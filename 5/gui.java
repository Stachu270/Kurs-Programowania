import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

class WrongValueException extends Exception
{}

public class gui
{
	private JFrame wnd;
	private JLabel label1;
	private JButton okButton;
	private GridLayout grid;
	private JScrollPane scrollWnd;
	private JPanel leftColumn, mainPane;
	private JTextField txtBox;
	private Font fnt;
	private TrojkatPascala tr;
	private int n;
	
	private ActionListener action = new ActionListener()
	{
			public void actionPerformed(ActionEvent e)
			{
				int prev = n;
				try { n = Integer.parseInt(txtBox.getText()); }
				catch (NumberFormatException ne)
				{
					JOptionPane.showMessageDialog(null, "To nie jest liczba", "B³¹d", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				try { foo(); }
				catch (WrongValueException ve)
				{
					n = prev;
					txtBox.setText(Integer.toString(n));
					return;
				}
				updateLook();
			}
	};
	
	gui()
	{
		n = 5;
		wnd = new JFrame("Trojkat Pascala");
		scrollWnd = new JScrollPane();
		leftColumn = new JPanel();
		mainPane = new JPanel();
		fnt = new Font("Courier New", Font.PLAIN, 8);
		label1 = new JLabel("Liczba wierszy:");
		txtBox = new JTextField(Integer.toString(n));
		okButton = new JButton("Poka¿");
		
		try { foo(); }
		catch (WrongValueException ve)
		{
			// to sie nigdy nie zdarzy
		}
		
		label1.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		txtBox.setMaximumSize(new Dimension(200, txtBox.getPreferredSize().height));
		txtBox.setAlignmentX(Component.CENTER_ALIGNMENT);
		txtBox.addActionListener(action);
		txtBox.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "key_down");
		txtBox.getActionMap().put("key_down", new AbstractAction() {
			public void actionPerformed(ActionEvent e)
			{
				if (n <= 0)
					return;
				n--;
				txtBox.setText(Integer.toString(n));
				try { foo(); }
				catch (WrongValueException ve)
				{
					// to sie nigdy nie zdarzy
				}
				updateLook();
			}
		});
		txtBox.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "key_up");
		txtBox.getActionMap().put("key_up", new AbstractAction() {
			public void actionPerformed(ActionEvent e)
			{
				if (n >= 33)
					return;
				n++;
				txtBox.setText(Integer.toString(n));
				try { foo(); }
				catch (WrongValueException ve)
				{
					// to sie nigdy nie zdarzy
				}
				updateLook();
			}
		});
		
		
		okButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		okButton.addActionListener(action);
		
		leftColumn.setLayout(new BoxLayout(leftColumn, BoxLayout.PAGE_AXIS));
		leftColumn.setBorder(new CompoundBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK), new EmptyBorder(5, 5, 5, 5)));
		leftColumn.add(label1);
		leftColumn.add(Box.createRigidArea(new Dimension(0, 3)));
		leftColumn.add(txtBox);
		leftColumn.add(Box.createRigidArea(new Dimension(0, 3)));
		leftColumn.add(okButton);
		
		mainPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		
		scrollWnd.setViewportView(mainPane);
		
		
		wnd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		wnd.setLayout(new BorderLayout());
        wnd.add(leftColumn, BorderLayout.LINE_START);
		wnd.add(scrollWnd);
		wnd.validate();
		wnd.setSize(500, 400);
		wnd.setExtendedState(wnd.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		wnd.setVisible(true);
	}
	
	public void foo() throws WrongValueException
	{
		grid = new GridLayout(n+1, 1);
		mainPane.removeAll();
		mainPane.setLayout(grid);
		
		try { tr = new TrojkatPascala(n); }
		catch (WrongValueException ex)
		{
			JOptionPane.showMessageDialog(null, "Liczba spoza zakresu. Wpisz liczbê z zakresu [0, 33]", "B³¹d", JOptionPane.INFORMATION_MESSAGE);
			throw ex;
		}
		
		if (n > 24)
			fnt = fnt.deriveFont(10f);
		else if (n > 20)
			fnt = fnt.deriveFont(14f);
		else if (n > 18)
			fnt = fnt.deriveFont(18f);
		else if (n > 14)
			fnt = fnt.deriveFont(24f);
		else
			fnt = fnt.deriveFont(30f);
		for (int i = 0; i <= n; i++)
		{
			JLabel a = new JLabel(tr.rowString(i), JLabel.CENTER);
			//System.out.println(tr.rowString(i));
			a.setFont(fnt);
			mainPane.add(a);
		}
	}
	
	private void updateLook()
	{
		mainPane.setPreferredSize(null);
		wnd.validate();
		mainPane.setPreferredSize(new Dimension(mainPane.getWidth(), mainPane.getWidth()/2));
		
		JScrollBar sb = scrollWnd.getHorizontalScrollBar();
		sb.setValue((int)((sb.getMaximum() - sb.getVisibleAmount()) / 2));
		wnd.validate();
	}
	
	public static void main(String args[]) throws WrongValueException
	{
		SwingUtilities.invokeLater(new Runnable() { public void run() { new gui(); }});
		
	}
}

class TrojkatPascala
{
	private int triangle[][];
	private String rowString[];
	
	TrojkatPascala(int n) throws WrongValueException
	{
		if (n < 0 || n > 33)
			throw new WrongValueException();
		
		rowString = new String[n + 1];
		triangle = new int[n + 1][];
		for (int i = 0; i <= n; i++)
			triangle[i] = new int[i + 1];
		triangle[0][0] = 1;
		
		for (int i = 1; i <= n; i++)
		{	
			triangle[i][0] = 1;
			for (int j = 1; j < triangle[i-1].length; j++)
				triangle[i][j] = triangle[i-1][j-1] + triangle[i-1][j];
			triangle[i][i] = 1;
		}
		
		this.normalise();
	}
	
	private void normalise()
	{
		int n = triangle.length - 1;
		int max = Integer.toString(triangle[n][n/2]).length() + 2;
		StringBuilder sb;
		
		for (int i = 0; i <= n; i++)
		{
			sb = new StringBuilder();
			for (int k = 0; k < i; k++)
				sb.append(String.format("%-" + max + "d", triangle[i][k]));
			sb.append(1);
			rowString[i] = sb.toString();
		}
	}
	
	public int[] row(int n)
	{
		return triangle[n];
	}
	
	public int elem(int n, int k)
	{
		return triangle[n][k];
	}
	
	public String rowString(int i)
	{
		return rowString[i];
	}
}