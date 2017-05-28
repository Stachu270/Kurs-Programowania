import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.util.*;


public class Simulator implements ActionListener, ChangeListener
{
	private Board field;
	
	private JFrame wnd;
	private JPanel northMenu, columnsPane, rowsPane, timerPane, haresNumbPane;
	private JLabel rowsLabel, columnsLabel, timerLabel, haresNumbLabel;
	private JLabel rowsNum, columnsNum, timerNum, haresNum;
	private JButton startButton, stopButton;
	private JSlider rowsSlider, columnsSlider, timerSlider, haresNumbSlider;
	
	Simulator()
	{
		// JLabels
		rowsLabel = new JLabel("Rows:");
		columnsLabel = new JLabel("Columns:");
		timerLabel = new JLabel("Step [ms]:");
		haresNumbLabel = new JLabel("Hares:");
		
		// JButtons
		startButton = new JButton("Start");
		stopButton = new JButton("Stop");
		startButton.addActionListener(this);
		stopButton.addActionListener(this);
		
		// JSliders
		rowsSlider = new JSlider(2, 50, 5);
		//rowsSlider.setMajorTickSpacing(10);
		//rowsSlider.setMinorTickSpacing(1);
		//rowsSlider.setPaintTicks(true);
		columnsSlider = new JSlider(2, 50, 5);
		//columnsSlider.setMajorTickSpacing(10);
		//columnsSlider.setMinorTickSpacing(1);
		//columnsSlider.setPaintTicks(true);
		timerSlider = new JSlider(20, 200, 100);
		//timerSlider.setMajorTickSpacing(10);
		//timerSlider.setPaintTicks(true);
		haresNumbSlider = new JSlider(0, 24, 0);
		//haresNumbSlider.setMajorTickSpacing(10);
		//haresNumbSlider.setPaintTicks(true);
		rowsSlider.addChangeListener(this);
		columnsSlider.addChangeListener(this);
		timerSlider.addChangeListener(this);
		haresNumbSlider.addChangeListener(this);
		
		// JLabels Num
		rowsNum = new JLabel(Integer.toString(rowsSlider.getValue()));
		columnsNum = new JLabel(Integer.toString(columnsSlider.getValue()));
		timerNum = new JLabel(Integer.toString(timerSlider.getValue()));
		haresNum = new JLabel(Integer.toString(haresNumbSlider.getValue()));
		
		// Layout Managers
		columnsPane = new JPanel(new FlowLayout());
		rowsPane = new JPanel(new FlowLayout());
		timerPane = new JPanel(new FlowLayout());
		haresNumbPane = new JPanel(new FlowLayout());
		northMenu = new JPanel(new GridLayout(2, 3));
		
		// setting Panes
		columnsPane.add(columnsLabel);
		columnsPane.add(columnsSlider);
		columnsPane.add(columnsNum);
		rowsPane.add(rowsLabel);
		rowsPane.add(rowsSlider);
		rowsPane.add(rowsNum);
		timerPane.add(timerLabel);
		timerPane.add(timerSlider);
		timerPane.add(timerNum);
		haresNumbPane.add(haresNumbLabel);
		haresNumbPane.add(haresNumbSlider);
		haresNumbPane.add(haresNum);
		northMenu.add(startButton);
		northMenu.add(rowsPane);
		northMenu.add(timerPane);
		northMenu.add(stopButton);
		northMenu.add(columnsPane);
		northMenu.add(haresNumbPane);
		
		wnd = new JFrame("Simulator");
		field = new Board(this);
		field.setColumnsCount(columnsSlider.getValue());
		field.setRowsCount(rowsSlider.getValue());
		
		haresSliderInit();
		
		//JPanel outerField = new JPanel();
		//outerField.add(field, BorderLayout.CENTER);
		
		wnd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		wnd.setLayout(new BorderLayout());
		
		wnd.add(northMenu, BorderLayout.NORTH);
		wnd.add(field, BorderLayout.CENTER);
		
		
		wnd.validate();
		wnd.setSize(1100, 500);
		wnd.setExtendedState(wnd.getExtendedState());
		wnd.setVisible(true);
	}
	
	public void setHaresNumber(int n)
	{
		haresNumbSlider.setValue(n);
		haresNum.setText(Integer.toString(n));
	}
	
	private void haresSliderInit()
	{
		int i = rowsSlider.getValue() * columnsSlider.getValue() - 1;
		i = (i > 1000) ? 1000 : i;
		haresNumbSlider.setMaximum(i);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		switch (ae.getActionCommand())
		{
			case "Start":
				field.start();
				rowsSlider.setEnabled(false);
				columnsSlider.setEnabled(false);
				haresNumbSlider.setEnabled(false);
				break;
			case "Stop":
				field.stop();
				rowsSlider.setEnabled(true);
				columnsSlider.setEnabled(true);
				haresNumbSlider.setEnabled(true);
				break;
			default:
				System.out.println("Cos nie dziala");
		}
	}
	
	public void stateChanged(ChangeEvent ce)
	{
		if (ce.getSource() == rowsSlider)
		{
			rowsNum.setText(Integer.toString(rowsSlider.getValue()));
			field.setRowsCount(rowsSlider.getValue());
			haresSliderInit();
			field.breedWolf();
			field.repaint();
		}
		else if (ce.getSource() == columnsSlider)
		{
			columnsNum.setText(Integer.toString(columnsSlider.getValue()));
			field.setColumnsCount(columnsSlider.getValue());
			haresSliderInit();
			field.breedWolf();
			field.repaint();
		}
		else if (ce.getSource() == timerSlider)
		{
			timerNum.setText(Integer.toString(timerSlider.getValue()));
			field.setTimer(timerSlider.getValue());
		}
		else if (ce.getSource() == haresNumbSlider)
		{
			haresNum.setText(Integer.toString(haresNumbSlider.getValue()));
			field.setHaresCount(haresNumbSlider.getValue());
		}
		else
			System.out.println("Critical error!");
	}
	
	public static void main(String args[])
	{
		SwingUtilities.invokeLater(new Runnable() { public void run() { new Simulator(); }});
		//new Simulator();
	}
}

class Board extends JPanel implements ActionListener, MouseListener, MouseMotionListener, MouseWheelListener
{
	public Wolf agressor;
	public ArrayList<Hare> opressors;
	public int width, height, time, pixSize;
	private javax.swing.Timer timer;
	private Point tmpPoint, origin;
	private Simulator sim;
	
	Board(Simulator s)
	{
		super();
		sim = s;
		tmpPoint = new Point();
		origin = new Point(0, 0);
		width = 1;
		height = 1;
		pixSize = 0;
		time = 100;
		
		addComponentListener(new ComponentAdapter() {
				public void componentResized(ComponentEvent ce) {
					resize();
					repaint();
				}
			});
		
		addMouseListener(this);
		addMouseMotionListener(this);
		
		resize();
		
		opressors = new ArrayList<>();
		agressor = new Wolf(this);
		
		for (Hare h : opressors)
			h.start();
		agressor.start();
	}
	
	Board(Simulator s, int x, int y)
	{
		super();
		sim = s;
		tmpPoint = new Point();
		origin = new Point(0, 0);
		width = x;
		height = y;
		pixSize = 30;
		time = 100;
		
		addComponentListener(new ComponentAdapter() {
				public void componentResized(ComponentEvent ce) {
					resize();
					repaint();
					//System.out.println(getBounds());
				}
			});
		
		//timer = new javax.swing.Timer(40, this);
		
		//setSize(x*6, y*6);
		
		//System.out.println(getBounds());
		//System.out.println(getPreferredSize());
		//System.out.println(getSize());
		addMouseListener(this);
		addMouseMotionListener(this);
		
		resize();
		
		opressors = new ArrayList<>();
		agressor = new Wolf(this);
		//for (int i = 0; i < 7; i++)
		//{
			//opressors.add(new Hare(this));
		//}
		
		for (Hare h : opressors)
			h.start();
		agressor.start();
		
		//timer.start();
	}
	
	private void resize()
	{
		Rectangle r = getBounds();
		//int m//in = (r.width > r.height) ? r.height : r.width;
		int size, size1, size2;
		
		size1 = (r.width - width - 1) / width;
		size2 = (r.height - height - 1) / height;
		size = (size1 < size2) ? size1 : size2;
		
		pixSize = (size > 0) ? size : 1;
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.translate(origin.x, origin.y);
		draw(g);
	}
	
	public void start()
	{
		for (Hare h : opressors)
			h.play();
		agressor.play();
		repaint();
	}
	
	public void stop()
	{
		agressor.pause();
		for (Hare h : opressors)
			h.pause();
		repaint();
	}
	
	public void setRowsCount(int n)
	{
		height = n;
		resize();
	}
	
	public void setColumnsCount(int n)
	{
		width = n;
		resize();
	}
	
	public void setTimer(int n)
	{
		time = n;
	}
	
	public void breedWolf()
	{
		agressor.kill();
		agressor = new Wolf(this);
		agressor.start();
	}
	
	public void setHaresCount(int n)
	{
		int size = opressors.size();
		if (n < size)
		{
			for (int i = n; i < size; i++)
			{
				opressors.get(0).kill();
				opressors.remove(0);
			}
		}
		else if (n > size)
		{
			Hare tmp;
			for (int i = size; i < n; i++)
			{
				tmp = new Hare(this);
				tmp.start();
				opressors.add(tmp);
			}
		}
		
		repaint();
		//System.out.println(opressors.size());
	}
	
	public Simulator getSimulator()
	{
		return sim;
	}
	
	private void draw(Graphics g)
	{
		//Graphics2D g2d = (Graphics2D) g.create();
		setBackground(Color.gray);
		
		g.setColor(Color.green);
		g.fillRect(0, 0, width*(pixSize+1), height*(pixSize+1));
		g.setColor(Color.black);
		g.drawRect(0, 0, width*(pixSize+1), height*(pixSize+1));
		
		for (int i = 1; i < width; i++)
			g.drawLine((pixSize+1)*i, 0, (pixSize+1)*i, height*(pixSize+1));
		for (int i = 1; i < height; i++)
			g.drawLine(0, (pixSize+1)*i, width*(pixSize+1), (pixSize+1)*i);
		
		//for (Hare h : opressors)
			//h.draw(g);
		for (int i = 0; i < opressors.size(); i++)
			opressors.get(i).draw(g);
		agressor.draw(g);
		
		//System.out.println("wilk: " + agressor.getPos().x + " " + agressor.getPos().y);
		//for (Hare h : opressors.values())
		//	System.out.println("zajac: " + h.getPos().x + " " + h.getPos().y);
		
		//g2d.dispose();
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		repaint();
	}
	
	public void mouseDragged(MouseEvent me)
	{
		if (!SwingUtilities.isLeftMouseButton(me))
			return;
		
		origin.translate(me.getPoint().x - tmpPoint.x, me.getPoint().y - tmpPoint.y);
		tmpPoint = me.getPoint();
		repaint();
	}
	
	public void mousePressed(MouseEvent me)
	{
		tmpPoint.setLocation(me.getPoint());
	}
	
	public void mouseWheelMoved(MouseWheelEvent me)
	{
		int e = me.getWheelRotation();
		if (e > 0) 
			Math.pow(0.8, Math.abs(e));
		else
			Math.pow(1.2, Math.abs(e));
	}
	
	public void mouseMoved(MouseEvent me) {}
	public void mouseClicked(MouseEvent me) {}
	public void mouseEntered(MouseEvent me) {}
	public void mouseExited(MouseEvent me) {}
	public void mouseReleased(MouseEvent me) {}
	
}

abstract class Animal extends Thread
{
	//protected int offsetX, offsetY;
	protected static Random generator;
	protected Point pos;
	protected Position borderPos;
	protected boolean stop, dead;
	protected final Object LOCK = new Object();
	private Color color;
	Board brd;
	enum Position {UP, DOWN, RIGHT, LEFT, UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT, CENTER};
	enum Moves {N, S, E, W, NE, NW, SE, SW};
	final static protected Moves[][] moveTab = {	{Moves.SW, Moves.W, Moves.NW},
													{Moves.S, null, Moves.N},
													{Moves.SE, Moves.E, Moves.NE}};
	
	static
	{
		generator = new Random(System.currentTimeMillis());
	}
	
	Animal(Board b, Color col)
	{
		stop = true;
		dead = false;
		//offsetX = offsetY = 0;
		color = new Color(col.getRGB());
		brd = b;
		pos = new Point(generator.nextInt(brd.width), generator.nextInt(brd.height));
		
		while (!canMove(pos))
			pos.setLocation(generator.nextInt(brd.width), generator.nextInt(brd.height));
		
		borderPos = borderPosition(pos);	
	}
	
	protected void draw(Graphics g)
	{
		g.setColor(color);
		g.fillRect(	pos.x * (brd.pixSize+1) + 1/* + offsetX*/,
					(brd.height - pos.y - 1) * (brd.pixSize+1) + 1/* - offsetY*/,
					brd.pixSize,
					brd.pixSize);
	}
	
	public void play()
	{
		synchronized (LOCK)
		{
			stop = false;
			LOCK.notify();
		}
	}
	
	public void pause()
	{
		synchronized (LOCK)
		{	
			stop = true;
			LOCK.notify();
		}
	}
	
	public void kill()
	{
		synchronized (LOCK)
		{
			stop = false;
			LOCK.notify();
			dead = true;
		}
	}
	
	public Point getPos()
	{
		return pos;
	}
	
	protected boolean canMove(Point p)
	{
		
		for (int i = 0; i < brd.opressors.size(); i++)
		{
			if (brd.opressors.get(i) == null)
				System.out.println("AHA!");
			if (brd.opressors.get(i).getPos().equals(p))
				return false;
		}
		if (brd.agressor != null && brd.agressor.getPos().equals(p))
			return false;
		return true;
	}
	
	protected void move(Moves dir)
	{
		if (dir == null)
			return;
		switch (dir)
		{
			case N:
				pos.y++;
				//offsetX = 0; offsetY = 1;
				break;
			case NE:
				pos.x++; pos.y++;
				//offsetX = 1; offsetY = 1;
				break;
			case E:
				pos.x++;
				//offsetX = 1; offsetY = 0;
				break;
			case SE:
				pos.x++; pos.y--;
				//offsetX = 1; offsetY = -1;
				break;
			case S:
				pos.y--;
				//offsetX = 0; offsetY = -1;
				break;
			case SW:
				pos.x--; pos.y--;
				//offsetX = -1; offsetY = -1;
				break;
			case W:
				pos.x--;
				//offsetX = -1; offsetY = 0;
				break;
			case NW:
				pos.x--; pos.y++;
				//offsetX = -1; offsetY = 1;
				break;
			default:
				//offsetX = 0; offsetY = 0;
		}
		borderPos = borderPosition(pos);
	}
	
	protected void move(int x, int y)
	{
		move(moveTab[x+1][y+1]);
	}
	
	protected Point posAfterMove(int x, int y)
	{
		return posAfterMove(moveTab[x+1][y+1]);
	}
	
	protected Point posAfterMove(Moves dir)
	{
		Point p = new Point(pos);
		if (dir == null)
			return p;
		
		switch (dir)
		{
			case N:
				p.y++;
				break;
			case NE:
				p.x++; p.y++;
				break;
			case E:
				p.x++;
				break;
			case SE:
				p.x++; p.y--;
				break;
			case S:
				p.y--;
				break;
			case SW:
				p.x--; p.y--;
				break;
			case W:
				p.x--;
				break;
			case NW:
				p.x--; p.y++;
				break;
			default:
				
		}
		return p;
	}
	
	protected Position borderPosition(Point p)
	{
		if (p.x == 0)
		{
			if (p.y == 0)
				return Position.DOWN_LEFT;
			else if (p.y == brd.height - 1)
				return Position.UP_LEFT;
			return Position.LEFT;
		}
		else if (p.x == brd.width - 1)
		{
			if (p.y == 0)
				return Position.DOWN_RIGHT;
			else if (p.y == brd.height - 1)
				return Position.UP_RIGHT;
			return Position.RIGHT;
		}
		else
		{
			if (p.y == 0)
				return Position.DOWN;
			else if (p.y == brd.height - 1)
				return Position.UP;
			return Position.CENTER;
		}
	}
	
	protected Moves[] possibleMoves()
	{
		switch (borderPos)
		{
			case UP:
				return new Moves[] {Moves.W, Moves.SW, Moves.S, Moves.SE, Moves.E};
			case DOWN:
				return new Moves[] {Moves.W, Moves.NW, Moves.N, Moves.NE, Moves.E};
			case LEFT:
				return new Moves[] {Moves.N, Moves.NE, Moves.E, Moves.SE, Moves.S};
			case RIGHT:
				return new Moves[] {Moves.N, Moves.NW, Moves.W, Moves.SW, Moves.S};
			case CENTER:
				return new Moves[] {Moves.N, Moves.NW, Moves.W, Moves.SW, Moves.S, Moves.SE, Moves.E, Moves.NE};
			case UP_LEFT:
				return new Moves[] {Moves.E, Moves.SE, Moves.S};
			case UP_RIGHT:
				return new Moves[] {Moves.W, Moves.SW, Moves.S};
			case DOWN_LEFT:
				return new Moves[] {Moves.N, Moves.NE, Moves.E};
			case DOWN_RIGHT:
				return new Moves[] {Moves.N, Moves.NW, Moves.W};
			default:
				return null;
		}
	}
}

class Wolf extends Animal
{
	//ArrayList<Hare> hares;
	
	Wolf(Board b)
	{
		super(b, Color.red);
	}
	
	public void run()
	{
		while (true)
		{
			synchronized (LOCK)
			{
				while (stop)
				{
					try {
						LOCK.wait();
					}
					catch (InterruptedException ie)
					{
						System.out.println(this);
					}
				}
			}
			if (dead)
				return;
			
			int m = move();
			
			brd.repaint();
			
			try
			{
				int t = brd.time/2 + generator.nextInt(brd.time + 1);
				//int t = (brd.time/2 + generator.nextInt(brd.time + 1)) / pixSize;
				
				Thread.sleep(m * t);
				/*if (m != 1)
				{
					Thread.sleep(m * t * pixSize);
					continue;
				}
				
				int ox = offsetX;
				int oy = offsetY;
				
				for (int i = -pixSize; i <= 0; i++)
				{
					offsetX = i * ox;
					offsetY = i * oy;
					brd.repaint();
					Thread.sleep(t);
				}*/
			}
			catch (InterruptedException e)
			{
				System.out.println("Cos poszlo nie tak.");
			}
		}
	}
	
	private synchronized int move()
	{
		//if (stop)
			//return 1;
		
		if (brd.opressors.isEmpty())
			return 1;
		
		Hare closest = null;
		for (Hare h : brd.opressors)
		{
			if (closest == null)
				closest = h;
			
			if (pos.distance(closest.getPos()) > pos.distance(h.getPos()))
				closest = h;
		}
		
		int vx, vy;
		
		if (pos.x < closest.getPos().x)
			vx = 1;
		else if (pos.x > closest.getPos().x)
			vx = -1;
		else
			vx = 0;
		
		if (pos.y < closest.getPos().y)
			vy = 1;
		else if (pos.y > closest.getPos().y)
			vy = -1;
		else
			vy = 0;
		
		//int vx = (pos.x < closest.getPos().x) ? 1 : -1;
		//int vy = (pos.y < closest.getPos().y) ? 1 : -1;
		
		super.move(vx, vy);
		
		if (pos.equals(closest.getPos()))
		{	
			closest.kill();
			brd.opressors.remove(closest);
			brd.getSimulator().setHaresNumber(brd.opressors.size());
			return 5;
		}
		return 1;
	}
}

class Hare extends Animal
{	
	Hare(Board b)
	{
		super(b, Color.white);
		dead = false;
	}
	
	public void run()
	{
		while (true)
		{
			synchronized (LOCK)
			{
				while (stop)
				{
					try {
						LOCK.wait();
					}
					catch (InterruptedException ie)
					{
						System.out.println(this);
					}
				}
			}
			if (dead)
				return;
			
			move();
			brd.repaint();
			
			try
			{
				int t = brd.time/2 + generator.nextInt(brd.time + 1);
				/*int t = (brd.time/2 + generator.nextInt(brd.time + 1)) / pixSize;
				int ox = offsetX;
				int oy = offsetY;
				
				for (int i = -pixSize; i <= 0; i++)
				{
					offsetX = i * ox;
					offsetY = i * oy;
					brd.repaint();
					Thread.sleep(t);
				}*/
				Thread.sleep(t);
			}
			catch (InterruptedException e)
			{
				System.out.println("Cos poszlo nie tak.");
			}
		}
	}
	
	private synchronized void move()
	{
		//if (stop)
			//return;
		
		if (brd.agressor == null)
			return;
		
		if (borderPos == Position.CENTER)
		{
			Point tmp = new Point(pos);
			int vx, vy;
			
			if (brd.agressor.getPos().x < pos.x)
				vx = 1;
			else if (brd.agressor.getPos().x > pos.x)
				vx = -1;
			else
				vx = 0;
			
			if (brd.agressor.getPos().y < pos.y)
				vy = 1;
			else if (brd.agressor.getPos().y > pos.y)
				vy = -1;
			else
				vy = 0;
			
			//int vx = (brd.agressor.getPos().x < pos.x) ? 1 : -1;
			//int vy = (brd.agressor.getPos().y < pos.y) ? 1 : -1;
			
			if (canMove(posAfterMove(vx, vy)))
				super.move(vx, vy);
		}
		else
		{
			Moves t[] = possibleMoves();
			Moves m = t[generator.nextInt(t.length)];
			
			if (canMove(posAfterMove(m)))
				super.move(m);
		}
	}
}