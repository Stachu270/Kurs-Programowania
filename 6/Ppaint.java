import myproj.*;

import java.applet.*;
import java.awt.*;
import java.util.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.basic.*;
/*
<applet code="Ppaint" width=700 height=400>
</applet>
*/

/**
* Ppaint is an applet designed for creating simple shapes.
* @author Micha³ S³owik
* @version 1
*/
public class Ppaint extends JApplet
{
	private JPanel colorsPane, northPane, mainPane;
	private JMenuBar menuBar;
	private JMenu fileMenu, infoMenu;
	private JMenuItem newFileItem, openFileItem, saveFileItem, infoItem;
	private JTextField commandText;
	private static Color basicColors[] = {	Color.black, Color.blue, Color.cyan, Color.darkGray, Color.gray,
											Color.green, Color.lightGray, Color.magenta, Color.orange,
											Color.pink, Color.red, Color.white, Color.yellow};
	
	/**
	* What can I say more?
	*/
	@Override
	public void init()
	{
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					makeGUI();
				}
			});
		}
		catch (Exception ex)
		{
			System.out.println("Nie mo¿na uruchomiæ apletu: " + ex);
		}
	}
	
	/**
	* Called in <code>init()</code> it is just fo clarity purposes
	*/
	private void makeGUI()
	{
		colorsPane = new JPanel(new GridLayout(2, basicColors.length/2 + 1));
		JButton btn = new JButton();
		//btn.setUI(new colorButton(40, Color.red));
		
		menuBar = new JMenuBar();
		
		fileMenu = new JMenu("File");
		infoMenu = new JMenu("Info");
		
		infoItem = new JMenuItem("About program");
		newFileItem = new JMenuItem("New");
		openFileItem = new JMenuItem("Open");
		saveFileItem = new JMenuItem("Save");
		
		infoMenu.add(infoItem);
		
		fileMenu.add(newFileItem);
		fileMenu.add(openFileItem);
		fileMenu.add(saveFileItem);
		
		menuBar.add(fileMenu);
		menuBar.add(infoMenu);
		
		northPane = new JPanel(new FlowLayout());
		northPane.setBackground(new Color(200, 200, 200));
		
		mainPane = new JPanel(new BorderLayout());
		mainPane.setBackground(Color.gray);
		Board brd = new Board();
		mainPane.add(brd);
		mainPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		JButton rectButton = new JButton("Rectangle");
		JButton circButton = new JButton("Circle");
		JButton polyButton = new JButton("Polygon");
		JButton noneButton = new JButton("None");
		rectButton.addActionListener(brd);
		circButton.addActionListener(brd);
		polyButton.addActionListener(brd);
		noneButton.addActionListener(brd);
		northPane.add(noneButton);
		northPane.add(rectButton);
		northPane.add(circButton);
		northPane.add(polyButton);
		//colorButton im = new colorButton(Color.red);
		
		newFileItem.addActionListener(brd);
		openFileItem.addActionListener(brd);
		saveFileItem.addActionListener(brd);
		infoItem.addActionListener(brd);
		
		/*btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				JOptionPane.showMessageDialog(null, "Klik", "Blad", JOptionPane.INFORMATION_MESSAGE);
			}
		});*/
		
		commandText = new JTextField();
		
		//clickMenu.addMouseListener(brd);
		/*brd.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				checkPopup(e);
			}

			public void mouseClicked(MouseEvent e) {
				checkPopup(e);
			}

			public void mouseReleased(MouseEvent e) {
				checkPopup(e);
			}
			
			private void checkPopup(MouseEvent e) {
				if (e.isPopupTrigger())
					clickMenu.show(brd, e.getX(), e.getY());
			}
		});*/
		
		setLayout(new BorderLayout());
		setJMenuBar(menuBar);
		add(northPane, BorderLayout.NORTH);
		add(mainPane, BorderLayout.CENTER);
		add(commandText, BorderLayout.SOUTH);
	}
}