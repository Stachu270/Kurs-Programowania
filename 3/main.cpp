// Michal Slowik

#include <iostream>
#include <cstdlib>
#include <cmath>
#include <exception>
#include <vector>
#include <string>
#include <sstream>
#define PI 3.14159265
#define RAD(d)  (d * PI / 180)
#define TXT1 "Bok nie moze byc ujemny lub = 0"

class WrongValueException : public std::range_error
{
	public:
		WrongValueException(std::string s) : range_error(s) {}
};

double parseDouble(std::string s)
{
	std::stringstream ss(s);
	double d;
	
	ss >> d;
	if (ss.fail() || !ss.eof())
		throw WrongValueException("Argument nie jest liczba");
	return d;
}

class Figura
{
	public:
		virtual double obwod() const = 0;
		virtual double pole() const = 0;
};

class Czworokat : public Figura
{
	protected:
		double a, b, c, d, fi;
	public:
		static Czworokat* decide(double A, double B, double C, double D, double Fi);
		double obwod() const { return a + b + c + d; }
};

class Okrag : public Figura
{
	private:
		double r;
	public:
		Okrag(double R);
		double obwod() const { return 2 * PI * r; }
		double pole() const { return PI * r * r; }
};

class Pieciokat : public Figura
{
	private:
		double a;
	public:
		Pieciokat(double A);
		double obwod() const { return 5 * a; }
		double pole() const { return 0.3125 * a * a / std::tan(RAD(36)); }
};

class Szesciokat : public Figura
{
	private:
		double a;
	public:
		Szesciokat(double A);
		double obwod() const { return 6 * a; }
		double pole() const { return 1.5 * a * a * std::sqrt(3); }
};

class Kwadrat : public Czworokat
{
	public:
		Kwadrat(double A);
		double pole() const { return a*a; }
};

class Prostokat : public Czworokat
{
	public:
		Prostokat(double A, double B);
		double pole() const { return a*b; }
};

class Romb : public Czworokat
{
	public:
		Romb(double A, double Fi);
		double pole() const { return a * a * std::sin(RAD(fi)); }
};

int main(int argc, char *argv[])
{
	using namespace std;
	// sprawdzic czy argc jest odpowiednio duza
	vector<Figura *> tab;
	vector<string> args;
	int cnt = 1;
	double num;
	
	if (argc < 2)
	{
		cout << "Zbyt malo parametrow." << endl;
		exit(1);
	}
	
	for (int i = 1; i < argc; i++)
		args.push_back(argv[i]);
	
	for (int i = 0; i < args[0].size(); i++)
	{
		try
		{
			switch (args[0][i])
			{
				case 'o':
					tab.push_back(new Okrag(parseDouble(args.at(cnt++))));
					break;
				case 'c':
					tab.push_back(Czworokat::decide(	parseDouble(args.at(cnt++)),
														parseDouble(args.at(cnt++)),
														parseDouble(args.at(cnt++)),
														parseDouble(args.at(cnt++)),
														parseDouble(args.at(cnt++))));
					break;
				case 'p':
					tab.push_back(new Pieciokat(parseDouble(args.at(cnt++))));
					break;
				case 's':
					tab.push_back(new Szesciokat(parseDouble(args.at(cnt++))));
					break;
				default:
					cout << args[0][i] + " jest nieakceptowalnym znakiem. Poprawne znaki to o, c, p, s.";
			}
		}
		catch (const string &ex)
		{
			cout << ex << endl;
		}
		catch (const out_of_range &ex)
		{
			cout << "Zbyt malo argumentow\n";
			break;
		}
		catch (const WrongValueException &ex)
		{
			cout << ex.what() << endl;
			break;
		}
		catch (...)
		{
			cout << "jakis inny wyjatek\n";
		}
	}
	
	for (int i = 0; i < tab.size(); i++)
	{	
		cout << i+1 << ". obwod = " << tab[i]->obwod() << ", pole = " << tab[i]->pole() << endl;
		delete tab[i];
	}
	
	return 0;
}

Okrag::Okrag(double R)
{
	if (R <= 0)
		throw std::string("Promien nie moze byc ujemny lub = 0");
	r = R;
}

Pieciokat::Pieciokat(double A)
{
	if (A <= 0)
		throw std::string(TXT1);
	a = A;
}

Szesciokat::Szesciokat(double A)
{
	if (A <= 0)
		throw std::string(TXT1);
	a = A;
}

Kwadrat::Kwadrat(double A)
{
	if (A <= 0)
		throw std::string(TXT1);
	
	a = b = c = d = A;
	fi = 90;
}

Prostokat::Prostokat(double A, double B)
{
	if (A <= 0 || B <= 0)
		throw std::string(TXT1);
	
	a = c = A;
	b = d = B;
	fi = 90;
}

Romb::Romb(double A, double Fi)
{
	if (A <= 0 || Fi <= 0 || Fi >= 180)
		throw std::string("Bok musi byc > 0, a kat > 0 i < 180");
	
	a = b = c = d = A;
	fi = Fi;
}

Czworokat* Czworokat::decide(double A, double B, double C, double D, double Fi)
{
	if (Fi <= 0 || Fi >= 180)
		throw std::string("Kat musi byc > 0 i < 180");
	
	if (Fi == 90)
	{
		if (A == B && B == C && C == D && A > 0)
			return new Kwadrat(A);
		if (A > 0 && B > 0 && A == C && B == D)
			return new Prostokat(A, B);
		else if (A > 0 && C > 0 && A == B && C == D)
			return new Prostokat(A, C);
		throw std::string("Zle wartosci bokow czworokata");
	}
	if (A == B && B == C && C == D && A > 0)
		return new Romb(A, Fi);
	throw std::string("Zle wartosci bokow czworokata");
}