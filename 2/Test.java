// Michał Słowik

class WrongValueException extends Exception
{}

class WierszTrojkataPascala
{
	int row[];
	
	WierszTrojkataPascala(int n)
	{
		int half_i;
		
		row = new int[n + 1];
		row[0] = 1;
		
		for (int i = 0; i < n; i++)
		{	
			half_i = i / 2;
			
			if (i % 2 == 1)
				row[half_i + 1] = 2 * row[half_i];
			
			for (int j = half_i; j > 0; j--)
				row[j] += row[j-1];
		}
		
		for (int b = 0, e = n; b < e; b++, e--)
			row[e] = row[b];
	}
	
	int wspolczynnik(int m) throws WrongValueException
	{
		//for (int x : row)
			//System.out.print(x + " ");
		try { return row[m]; }
		catch (ArrayIndexOutOfBoundsException ex)
		{
			throw new WrongValueException();
		}
	}
}

public class Test
{
	public static void main(String args[])
	{
		int x;
		WierszTrojkataPascala pas;
		
		try { x = Integer.parseInt(args[0]); }
		catch (NumberFormatException ex)
		{
			System.out.println(args[0] + " - Nie jest to liczba");
			return;
		}
		
		if (x < 0 | x > 32)
		{
			System.out.println(x + " - Nieprawidlowy numer wiersza");
			return;
		}
		
		pas = new WierszTrojkataPascala(x);
		
		for (int i = 1; i < args.length; i++)
		{
			try { x = Integer.parseInt(args[i]); }
			catch (NumberFormatException ex)
			{
				System.out.println(args[i] + " - Nieprawidlowa dana");
				continue;
			}
			
			try { System.out.println(x + " - " + pas.wspolczynnik(x)); }
			catch (WrongValueException ex)
			{
				System.out.println(x + " - Liczba spoza zakresu");
			}
		}
	}
}