import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

public class process
{
	private JFrame wnd;
	private JLabel row, elem, result;
	private JTextField rowText, elemText;
	private JButton submit;
	private JPanel jp1, jp2, mainPanel;
	private String cmd = "main.exe";
	
	process()
	{
		row = new JLabel("Wiersz");
		elem = new JLabel("Element");
		result = new JLabel();
		
		rowText = new JTextField(3);
		elemText = new JTextField(3);
		
		submit = new JButton("Oblicz");
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				try
				{
					int r = Integer.parseInt(rowText.getText());
					int e = Integer.parseInt(elemText.getText());
				}
				catch (NumberFormatException ex)
				{
					JOptionPane.showMessageDialog(null, "Bledne argumenty", "Blad", JOptionPane.INFORMATION_MESSAGE);
				}
				
				Process proc;
				String ans = "";
				try 
				{
					proc = Runtime.getRuntime().exec(cmd + " " + rowText.getText() + " " + elemText.getText());
				
					BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
					ans = reader.readLine();
					int a = Integer.parseInt(ans);
					result.setText(ans);
				}
				catch (IOException ex)
				{
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Blad", JOptionPane.INFORMATION_MESSAGE);
				}
				catch (NumberFormatException ex)
				{
					JOptionPane.showMessageDialog(null, ans, "Blad", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		TitledBorder title = BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(), "Wynik");
		title.setTitleJustification(TitledBorder.ABOVE_TOP);
		result.setBorder(title);
		result.setAlignmentX(Component.LEFT_ALIGNMENT);
		result.setPreferredSize(new Dimension(90, 38));
		
		jp1 = new JPanel(new FlowLayout());
		jp1.add(row);
		jp1.add(rowText);
		jp1.add(elem);
		jp1.add(elemText);
		jp1.add(submit);
		
		jp2 = new JPanel(new FlowLayout());
		jp2.add(result);
		
		wnd = new JFrame("Process");
		wnd.getContentPane().setLayout(new BoxLayout(wnd.getContentPane(), BoxLayout.PAGE_AXIS));
		wnd.add(jp1);
		wnd.add(jp2);
		
		wnd.pack();
		wnd.setResizable(false);
		wnd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		wnd.setVisible(true);
	}
	
	
	public static void main(String args[])
	{
		SwingUtilities.invokeLater(new Runnable() { public void run() { new process(); }});
		
	}
}