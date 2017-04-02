// Michal Slowik

import java.lang.Math.*;

class WrongArgumentsValue extends Exception
{}
class WrongAngleValue extends Exception
{}

public class Figury
{
	enum One
	{
		KWADRAT
		{
			public double obwod(double a) { return 4 * a; }
			public double pole(double a) { return a * a; }
		},
		OKRAG
		{
			public double obwod(double r) { return 2 * Math.PI * r; }
			public double pole(double r) { return Math.PI * r * r; }
		},
		PIECIOKAT
		{
			public double obwod(double a) { return 5 * a; }
			public double pole(double a) { return 0.3125 * a * a / Math.tan(Math.toRadians(36)); }
		},
		SZESCIOKAT
		{
			public double obwod(double a) { return 6 * a; }
			public double pole(double a) { return 1.5 * a * a * Math.sqrt(3); }
		};
		
		// private double a;
		// One(double d) { a = d; }
		
		public abstract double obwod(double x);
		public abstract double pole(double x);
	}
	
	enum Two
	{
		PROSTOKAT
		{
			public double obwod(double a, double b) { return 2 * a + 2 * b; }
			public double pole(double a, double b) { return a * b; }
		},
		ROMB
		{
			public double obwod(double a, double fi) { return 4 * a; }
			public double pole(double a, double fi) { return a * a * Math.sin(Math.toRadians(fi)); }
		};
		
		// private double a, b;
		// Two(double x, double y) { a = x; b = y; }
		
		public abstract double obwod(double x, double y);
		public abstract double pole(double x, double y);
	}
	/*
	private static Figura decide(double a, double b, double c, double d, double fi) throws WrongAngleValue, WrongArgumentsValue
	{
		if (fi <= 0 || fi >= 180)
			throw new WrongAngleValue();
		
		if (fi == 90)
		{
			if (a == b && b == c && c == d && a > 0)
				return new Kwadrat(a);
			if (a > 0 && b > 0 && a == c && b == d)
				return new Prostokat(a, b);
			else if (a > 0 && c > 0 && a == b && c == d)
				return new Prostokat(a, c);
			else if (a > 0 && b > 0 && a == d && b == c)
				return new Prostokat(a, b);
			throw new WrongArgumentsValue();
		}
		if (a == b && b == c && c == d && a > 0)
			return new Romb(a, fi);
		throw new WrongArgumentsValue();
	}*/
	
	public static void main(String args[])
	{
		int cnt = 1;
		double num;
		double tb[] = new double[5];
		int j = 0;
		
		if (args.length == 0)
		{
			System.out.println("Zbyt malo parametrow.");
			return;
		}
		
		for (int i = 0; i < args[0].length(); i++)
		{
			try 
			{
				switch (args[0].charAt(i))
				{
					case 'o':
						num = Double.parseDouble(args[cnt++]);
						if (num <= 0)
							throw new WrongArgumentsValue();
						System.out.println((i+1) + ". obwod = " + One.OKRAG.obwod(num) + ", pole = " + One.OKRAG.pole(num));
						break;
					case 'c':
						cnt += 5;
						tb[0] = Double.parseDouble(args[cnt - 5]);
						tb[1] = Double.parseDouble(args[cnt - 4]);
						tb[2] = Double.parseDouble(args[cnt - 3]);
						tb[3] = Double.parseDouble(args[cnt - 2]);
						tb[4] = Double.parseDouble(args[cnt - 1]);
						
						if (tb[4] <= 0 || tb[4] >= 180)
							throw new WrongAngleValue();
		
						if (tb[4] == 90)
						{
							if (tb[0] == tb[1] && tb[1] == tb[2] && tb[2] == tb[3] && tb[0] > 0)
								System.out.println((i+1) + ". obwod = " + One.KWADRAT.obwod(tb[0]) + ", pole = " + One.KWADRAT.pole(tb[0]));
							if (tb[0] > 0 && tb[1] > 0 && tb[0] == tb[2] && tb[1] == tb[3])
								System.out.println((i+1) + ". obwod = " + Two.PROSTOKAT.obwod(tb[0], tb[1]) + ", pole = " + Two.PROSTOKAT.pole(tb[0], tb[1]));
							else if (tb[0] > 0 && tb[2] > 0 && tb[0] == tb[1] && tb[2] == tb[3])
								System.out.println((i+1) + ". obwod = " + Two.PROSTOKAT.obwod(tb[0], tb[2]) + ", pole = " + Two.PROSTOKAT.pole(tb[0], tb[2]));
							else if (tb[0] > 0 && tb[1] > 0 && tb[0] == tb[3] && tb[1] == tb[2])
								System.out.println((i+1) + ". obwod = " + Two.PROSTOKAT.obwod(tb[0], tb[1]) + ", pole = " + Two.PROSTOKAT.pole(tb[0], tb[1]));
							else
								throw new WrongArgumentsValue();
						}
						else if (tb[0] == tb[1] && tb[1] == tb[2] && tb[2] == tb[3] && tb[0] > 0)
							System.out.println((i+1) + ". obwod = " + Two.ROMB.obwod(tb[0], tb[4]) + ", pole = " + Two.ROMB.pole(tb[0], tb[4]));
						else
							throw new WrongArgumentsValue();
						break;
					case 'p':
						num = Double.parseDouble(args[cnt++]);
						if (num <= 0)
							throw new WrongArgumentsValue();
						System.out.println((i+1) + ". obwod = " + One.PIECIOKAT.obwod(num) + ", pole = " + One.PIECIOKAT.pole(num));
						break;
					case 's':
						num = Double.parseDouble(args[cnt++]);
						if (num <= 0)
							throw new WrongArgumentsValue();
						System.out.println((i+1) + ". obwod = " + One.SZESCIOKAT.obwod(num) + ", pole = " + One.SZESCIOKAT.pole(num));
						break;
					default:
						System.out.println(args[0].charAt(i) + " jest nieakceptowalnym znakiem. Poprawne znaki to o, c, p, s.");
				}	// koniec switch
			}
			catch (WrongAngleValue ex)
			{
				System.out.println("Bledny kat.");
			}
			catch (WrongArgumentsValue ex)
			{
				System.out.println("Zle wartosci argumentow.");
			}
			catch (ArrayIndexOutOfBoundsException ex)
			{
				System.out.println("Zbyt malo argumentow.");
				break;
			}
			catch (NumberFormatException ex)
			{
				System.out.println("Argument nie jest liczba. Koncze pobieranie danych.");
				break;
			}
		}	// koniec for
	}
}