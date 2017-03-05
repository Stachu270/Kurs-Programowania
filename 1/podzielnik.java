public class podzielnik
{
	public static void main(String args[])
	{
		int n;
		
		for (int i = 0; i < args.length; i++)
		{
			try { n = Integer.parseInt(args[i]); }
			catch (NumberFormatException ex)
			{
				System.out.println(args[i] + " nie jest liczbą calkowitą");
				continue;
			}
			System.out.println(args[i] + "\t" + div(n));
		}
		for (int i = 0; i < 100; i++)
			System.out.println(i + "\t" + div(i));
	}
	
	public static int div(int n)
	{
		if (n == 0 || n == 1)
			return 0;
		
		if ((n&0x1) != 0x1)
			return n >> 1;
		
		for (int i = 3; i*i <= n; i += 2)
		{
			if (n % i == 0)
				return n / i;
		}
		
		return 1;
	}
}