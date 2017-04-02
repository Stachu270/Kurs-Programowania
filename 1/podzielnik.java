public class Podzielnik
{
	public static void main(String args[])
	{
		int n, ret;
		
		for (int i = 0; i < args.length; i++)
		{
			try { n = Integer.parseInt(args[i]); }
			catch (NumberFormatException ex)
			{
				System.out.println(args[i] + " nie jest liczba calkowita");
				continue;
			}
			
			if (n < 0)
			{
				System.out.println(args[i] + "\tnie jest liczba naturalna");
				continue;
			}
			
			if ((ret = div(n)) == 0)
				System.out.println(args[i] + "\tnie posiada podzielnika");
			else
				System.out.println(args[i] + "\t" + div(n));
		}
	}
	
	public static int div(int n)
	{
		int sqrtn = (int)(Math.sqrt(n) + 1);
		
		if (n == 0 || n == 1)
			return 0;
		
		if ((n&0x1) != 0x1)
			return n / 2;
		
		for (int i = 3; i <= sqrtn; i += 2)
		{
			if (n % i == 0)
				return n / i;
		}
		
		return 1;
	}
}