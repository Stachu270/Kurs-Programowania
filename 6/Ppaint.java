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

public class Ppaint extends JApplet
{
	private JPanel colorsPane, northPane, mainPane;
	private JTextField commandText;
	private static Color basicColors[] = {	Color.black, Color.blue, Color.cyan, Color.darkGray, Color.gray,
											Color.green, Color.lightGray, Color.magenta, Color.orange,
											Color.pink, Color.red, Color.white, Color.yellow};
	
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
	
	private void makeGUI()
	{
		colorsPane = new JPanel(new GridLayout(2, basicColors.length/2 + 1));
		JButton btn = new JButton();
		//btn.setUI(new colorButton(40, Color.red));
		
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
		add(northPane, BorderLayout.NORTH);
		add(mainPane, BorderLayout.CENTER);
		add(commandText, BorderLayout.SOUTH);
	}
}