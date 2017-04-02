import javax.swing.*;
import java.awt.*;

class WrongValueException extends Exception
{}

public class gui
{
	gui(int n)
	{
		Dimension d = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		TrojkatPascala tr;
		String tab[] = new String[n+1];
		StringBuilder sb = new StringBuilder();
		
		try { tr = new TrojkatPascala(n); }
		catch (WrongValueException ex)
		{
			System.out.println("To sie nigdy nie wykona");
			return;	
		}
		
		Font fnt = new Font("LM Math", Font.PLAIN, 16);
		for (int i = 0; i <= n; i++)
			tab[i] = tr.rowString(i);
		
		
		JFrame wnd = new JFrame("Trojkat Pascala");
		JLabel b = new JLabel(tab[n], JLabel.CENTER);
		for (int i = 30; i >= 8; i--)
		{
			fnt = fnt.deriveFont((float)i);
			b.setFont(fnt);
			if (b.getMinimumSize().getWidth() + 16 < d.getWidth())
				break;
		}
		
		GridLayout grid = new GridLayout(n+1, 1);
		wnd.setMinimumSize(new Dimension((int)b.getMinimumSize().getWidth() + 16, (n+1) * (int)b.getMinimumSize().getHeight() + 40));
		wnd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		wnd.setLayout(grid);
		
		for (int i = 0; i <= n; i++)
		{
			JLabel a = new JLabel(tab[i], JLabel.CENTER);
			a.setFont(fnt);
			wnd.add(a);
		}
		
		wnd.setVisible(true);
	}
	
	public static void main(String args[]) throws WrongValueException
	{
		int cnt = Integer.parseInt(args[0]);
		SwingUtilities.invokeLater(new Runnable() { public void run() { new gui(cnt); }});
		
	}
}

class TrojkatPascala
{
	private int triangle[][];
	
	TrojkatPascala(int n) throws WrongValueException
	{
		if (n < 0 || n > 33)
			throw new WrongValueException();
		
		triangle = new int[n + 1][];
		for (int i = 0; i <= n; i++)
			triangle[i] = new int[i + 1];
		triangle[0][0] = 1;
		
		for (int i = 1; i <= n; i++)
		{	
			triangle[i][0] = 1;
			for (int j = 1; j < triangle[i-1].length; j++)
			{
				triangle[i][j] = triangle[i-1][j-1] + triangle[i-1][j];
			}
			triangle[i][i] = 1;
		}
	}
	
	public String rowString(int i)
	{
		StringBuilder sb = new StringBuilder();
		
		for (int x : triangle[i])
		{
			sb.append(x);
			sb.append("  ");
		}
		
		return sb.toString();
	}
}