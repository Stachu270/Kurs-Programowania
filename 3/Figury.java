// Michal Slowik

import java.lang.Math.*;

class WrongArgumentsValue extends Exception
{}
class WrongAngleValue extends Exception
{}

// abstrakcyjna klasa Figura + abstrakcyjne metody do obliczania obwodu i pola
abstract class Figura
{
	public abstract double obwod();
	public abstract double pole();
}

// abstrakcyjna klasa Czworokat extends Figura
abstract class Czworokat extends Figura
{
	protected double a, b, c, d, fi;
	
	public double obwod() { return a + b + c + d; }
}

// klasa Okrag extends Figura
class Okrag extends Figura
{
	private double r;
	
	Okrag(double R) throws WrongArgumentsValue
	{
		if (R <= 0)
			throw new WrongArgumentsValue();
		r = R;
	}
	
	public double obwod() { return 2 * Math.PI * r; }
	
	public double pole() { return Math.PI * r * r; }
}

// klasa Pieciokat extends Figura
class Pieciokat extends Figura
{
	private double a;
	
	Pieciokat(double A) throws WrongArgumentsValue
	{
		if (A <= 0)
			throw new WrongArgumentsValue();
		a = A;
	}
	
	public double obwod() { return 5 * a; }
	
	public double pole() { return 0.3125 * a * a / Math.tan(Math.toRadians(36)); }
}

// klasa Szesciokat extends Figura
class Szesciokat extends Figura
{
	private double a;
	
	Szesciokat(double A) throws WrongArgumentsValue
	{
		if (A <= 0)
			throw new WrongArgumentsValue();
		a = A;
	}
	
	public double obwod() { return 6 * a; }
	
	public double pole() { return 1.5 * a * a * Math.sqrt(3); }
}

// klasa Kwadrat extends Czworokat
class Kwadrat extends Czworokat
{
	Kwadrat(double A) throws WrongArgumentsValue
	{
		if (A <= 0)
			throw new WrongArgumentsValue();
		
		a = b = c = d = A;
		fi = 90;
	}
	
	public double pole() { return a * a; }
}

// klasa Prostokat extends Czworokat
class Prostokat extends Czworokat
{
	Prostokat(double A, double B) throws WrongArgumentsValue
	{
		if (A <= 0 || B <= 0)
			throw new WrongArgumentsValue();
		
		a = c = A;
		b = d = B;
		fi = 90;
	}
	
	public double pole() { return a * b; }
}

// klasa Romb extends Czworokat
class Romb extends Czworokat
{
	Romb(double A, double Fi) throws WrongArgumentsValue
	{
		if (A <= 0 || Fi <= 0 || Fi >= 180)
			throw new WrongArgumentsValue();
		
		a = b = c = d = A;
		fi = Fi;
	}
	
	public double pole() { return a * a * Math.sin(Math.toRadians(fi)); }
}

// linia polecen: o - okrag, c - czworokat, p - pieciokat, s - szesciokat
// o(R), c(a, b, c, d, kat), p(a), s(a)

public class Figury
{
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
			throw new WrongArgumentsValue();
		}
		if (a == b && b == c && c == d && a > 0)
			return new Romb(a, fi);
		throw new WrongArgumentsValue();
	}
	
	public static void main(String args[])
	{
		int cnt = 1;
		double num;
		Figura tab[] = new Figura[10];
		int j = 0;
		
		for (int i = 0; i < args[0].length(); i++)
		{
			try 
			{
				switch (args[0].charAt(i))
				{
					case 'o':
						tab[j] = new Okrag(Double.parseDouble(args[cnt++]));
						j++;
						break;
					case 'c':
						tab[j] = decide(	Double.parseDouble(args[cnt++]),
											Double.parseDouble(args[cnt++]),
											Double.parseDouble(args[cnt++]),
											Double.parseDouble(args[cnt++]),
											Double.parseDouble(args[cnt++]));
						j++;
						break;
					case 'p':
						tab[j] = new Pieciokat(Double.parseDouble(args[cnt++]));
						j++;
						break;
					case 's':
						tab[j] = new Szesciokat(Double.parseDouble(args[cnt++]));
						j++;
						break;
					default:
						System.out.println(args[0].charAt(i) + " jest nieakceptowalnym znakiem. Poprawne znaki to o, c, p, s.");
				}
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
		}
		
		for (int i = 0; i < j; i++)
			System.out.println((i+1) + ". obwod = " + tab[i].obwod() + ", pole = " + tab[i].pole());
	}
}