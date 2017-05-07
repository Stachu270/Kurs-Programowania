package myproj;

import java.security.*;
import java.applet.*;
import java.awt.*;
import java.util.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.basic.*;

/**
* Board represents a coordinate space that shapes can be drawn on. <br>
* It implements all shape creation processes and takes care of mouse actions. <br>
* It is kind of badly designed. For instance it is a listener for save/open/new button when it can have methods
* for doing so "from the outside".
* @author Michał Słowik
* @version 1
*/
public class Board extends JPanel implements MouseListener, MouseMotionListener, ActionListener, MouseWheelListener
{
	private ShapePlus shp;
	private ArrayList<ShapePlus> shapeArray;
	private Mode md;
	private Point tmp;
	private JPopupMenu clickMenu;
	private enum Mode {None, DrawRect, DrawCirc, DrawPoly, Modify};
	
	private static Color basicColors[] = {	Color.black, Color.blue, Color.cyan, Color.darkGray, Color.gray,
											Color.green, Color.lightGray, Color.magenta, Color.orange,
											Color.pink, Color.red, Color.white, Color.yellow};
	private static String nameColors[] = {	"black", "blue", "cyan", "dark gray", "gray", "green",
											"light gray", "magenta", "orange", "pink", "red", "white", "yellow"};
	
	/**
	* Creates <code>Board</code> instance.
	*/
	public Board()
	{
		super();
		md = Mode.None;
		tmp = new Point();
		shapeArray = new ArrayList<>();
		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
		
		clickMenu = new JPopupMenu("Color");
		JMenuItem mi;
		for (int i = 0; i < basicColors.length; i++)
		{
			mi = new JMenuItem(nameColors[i]);
			mi.setBackground(basicColors[i]);
			mi.addActionListener(this);
			clickMenu.add(mi);
		}
		
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "delete");
		getActionMap().put("delete", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				if (md == Mode.Modify)
				{
					shapeArray.remove(shp);
					shp = null;
					md = Mode.None;
					repaint();
				}
			}
		});
		
		//getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "space");
		/*getActionMap().put("space", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				if (md == Mode.DrawPoly)
				{
					((customPoly)shp).endTrace();
					shapeArray.add(shp);
					shp = null;
					repaint();
				}
			}
		});*/
	}
	
	/**
	* Draws the <code>oard</code> with all the shapes.
	* @param g the <code>Graphics</code> context in which to draw
	*/
	private void draw(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g.create();
		setBackground(Color.white);
		
		for (ShapePlus sp : shapeArray)
			sp.draw(g2d);
		
		if (shp != null)
			shp.draw(g2d);
		g2d.dispose();
	}
	
	/**
	* Saves the shapes in a file.
	*/
	public void save()
	{
		for (ShapePlus sp : shapeArray)
			System.out.println(sp.writeToString());
		
		AccessController.doPrivileged(new PrivilegedAction<Void>() {
			
			public Void run() {
				try {
				FileOutputStream file = new FileOutputStream("abcdef");
				file.write((byte)16);				
				file.close();
				} catch (IOException ioe)
				{
					System.out.println(ioe.toString());
				}
				return null;
			}
		});
		//System.out.println(System.getProperty("user.dir"));
		
		//int ret = fc.showSaveDialog(this.getParent());
		/*try {
		if (true)//ret == JFileChooser.APPROVE_OPTION)
		{
			//File f = fc.getSelectedFile();
			//System.out.println(f.canWrite());
			
			FileOutputStream file = new FileOutputStream("abcdef");
			
			file.write((byte)16);
			
			file.close();
			
		}
		} catch (IOException ioe)
		{
			System.out.println(ioe.toString());
		}*/
	}
	
	/**
	* Loads <code>Board</code> from file.
	*/
	public void open()
	{
		JFileChooser fc = new JFileChooser();
		
		int ret = fc.showSaveDialog(this.getParent());
		if (ret == JFileChooser.APPROVE_OPTION)
			;
	}
	
	/**
	* Paints component. Calls <code>draw</code> function.
	*/
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		draw(g);
	}
	
	/**
	* Does nothing here.
	*/
	@Override
	public void mouseEntered(MouseEvent e) {}
	
	/**
	* Does nothing here.
	*/
	@Override
	public void mouseExited(MouseEvent e) {}
	
	/**
	* Does nothing here.
	*/
	@Override
	public void mouseClicked(MouseEvent e)
	{
		switch(md)
		{
			
		}
	}
	
	/**
	* Responds to mouse being pressed on <code>Board</code> area.
	*/
	@Override
	public void mousePressed(MouseEvent e)
	{
		tmp.setLocation(e.getPoint());
		switch (md)
		{
			case DrawRect:
				if (!SwingUtilities.isLeftMouseButton(e))
					break;
				if (shp == null)
				{
					shp = new Rect(getMousePosition(), Color.red);
					repaint();
				}
				else
				{	
					shp.addPoint(getMousePosition(), true);
					shapeArray.add(shp);
					shp = null;
				}
				break;
			case DrawCirc:
				if (!SwingUtilities.isLeftMouseButton(e))
					break;
				if (shp == null)
				{
					shp = new Circ(getMousePosition(), Color.green);
					repaint();
				}
				else
				{	
					shp.addPoint(getMousePosition(), true);
					shapeArray.add(shp);
					shp = null;
				}
				break;
			case DrawPoly:
				if (shp == null)
				{
					if (!SwingUtilities.isLeftMouseButton(e))
						break;
					shp = new Poly(getMousePosition(), Color.blue);
				}
				else
				{	
					if (SwingUtilities.isRightMouseButton(e))
					{
						shapeArray.add(shp);
						((Poly)shp).closePoly();
						shp = null;
						repaint();
						break;
					}
					shp.addPoint(getMousePosition(), true);
				}
				repaint();
				break;
			case Modify:
				if (!SwingUtilities.isLeftMouseButton(e))
					break;
				if (shp.getBounds().contains(e.getPoint()))
					break;
				
				md = Mode.None;
				shp.deactive();
				//shapeArray.add(shp);
				shp = null;
				repaint();
				//System.out.println(e.getPoint().toString());
			case None:
				if (!SwingUtilities.isLeftMouseButton(e))
					break;
				ListIterator<ShapePlus> iter = shapeArray.listIterator(shapeArray.size());
				ShapePlus cs;
				while (iter.hasPrevious())
				{
					cs = iter.previous();
					if (cs.contains(getMousePosition()))
					{
						shp = cs;
						shp.active();
						shapeArray.remove(shp);
						shapeArray.add(shp);
						md = Mode.Modify;
						this.grabFocus();
						repaint();
						break;
					}
				}
				break;
		}
	}
	
	/**
	* Responds to mouse being released on <code>Board</code> area.
	*/
	@Override
	public void mouseReleased(MouseEvent e)
	{
		if (md == Mode.Modify && shp.getBounds().contains(e.getPoint()) && e.isPopupTrigger())
		{
			clickMenu.show(this, e.getX(), e.getY());
			repaint();
		}
	}
	
	/**
	* Responds to mouse being dragged on <code>Board</code> area.
	*/
	@Override
	public void mouseDragged(MouseEvent e)
	{
		if (!SwingUtilities.isLeftMouseButton(e))
			return;
		switch (md)
		{
			case Modify:
				if (!shp.getBounds().contains(tmp))
					break;
				
				shp.translate(e.getPoint().x - tmp.x, e.getPoint().y - tmp.y);
				tmp = e.getPoint();
				repaint();
				//System.out.println(e.getPoint().toString());
				break;
			case DrawCirc:
			case DrawRect:
			case DrawPoly:
				if (shp != null)
				{
					shp.addPoint(e.getPoint());
					repaint();
				}
				break;
		}
	}
	
	/**
	* Responds to mouse being moved on <code>Board</code> area.
	*/
	@Override
	public void mouseMoved(MouseEvent e)
	{
		//System.out.println(e.getPoint().toString());
		if (shp != null && md != Mode.Modify)
		{	
			shp.addPoint(e.getPoint());
			repaint();
		}
		if (md == Mode.None)
		{
			/*ListIterator<customShape> iter = shapeArray.listIterator(shapeArray.size());
			ShapePlus cs;
			boolean flag = true;
			while (iter.hasPrevious())
			{	
				cs = iter.previous();
				if (flag && cs.contains(e.getPoint()))
				{
					cs.rollover();
					//repaint();
					flag = false;
					//break;
				}
				else
					cs.notRollover();
			}
			repaint();*/
		}
	}
	
	/**
	* Responds to buttons being clicked.
	* It's a badly designed part of the code, because one should add this class as a buttons action listener, 
	* which is not a very good idea.
	*/
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		switch (ae.getActionCommand())
		{
			case "None":
				if (md == Mode.Modify)
					shp.deactive();
				md = Mode.None;
				shp = null;
				break;
			case "Rectangle":
				if (md == Mode.Modify)
					shp.deactive();
				if (md != Mode.DrawRect)
					shp = null;
				md = Mode.DrawRect;
				break;
			case "Circle":
				if (md == Mode.Modify)
					shp.deactive();
				if (md != Mode.DrawCirc)
					shp = null;
				md = Mode.DrawCirc;
				break;
			case "Polygon":
				if (md == Mode.Modify)
					shp.deactive();
				if (md != Mode.DrawPoly)
					shp = null;
				md = Mode.DrawPoly;
				break;
			case "New":
				System.out.println("New");
				shp = null;
				md = Mode.None;
				shapeArray.clear();
				break;
			case "Open":
				System.out.println("Open");
				open();
				break;
			case "Save":
				System.out.println("Save");
				save();
				break;
			case "About program":
				JOptionPane.showMessageDialog(this, "A simple simple-shape painting program.\n"
				+ "Nothing to see here. Please disperse.");
				break;
			default:	// Takes care of colors buttons
				int i;
				for (i = 0; i < basicColors.length; i++)
					if (ae.getActionCommand().equals(nameColors[i]))
						break;
					
				shp.setColor(basicColors[i]);
				break;
		}
		//grabFocus();
		repaint();
	}
	
	/**
	* Responds to mouse wheel being moved while cursor is in <code>Board</code> area.
	*/
	@Override
	public void mouseWheelMoved(MouseWheelEvent me)
	{
		switch (md)
		{
			case Modify:
				int e = me.getWheelRotation();
				shp.scale(( e > 0) ? (float)Math.pow(0.8, Math.abs(e)) : (float)Math.pow(1.2, Math.abs(e)));
				repaint();
				break;
			case None:
				
				break;
		}
	}
}