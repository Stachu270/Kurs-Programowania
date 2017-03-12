// Micha³ S³owik

#include <iostream>
#include <string>
#include <cstdlib>
#include <sstream>
#include <stdexcept>

class WrongValueException : public std::range_error
{
	public:
		WrongValueException(std::string s) : range_error(s) {}
};

class Wiersz
{
	private:
		int size;
		int *row;
	public:
		Wiersz(int n);
		~Wiersz() { delete[] row; }
		int wspolczynnik(int m);
};

int main(int argc, char **argv)
{
	using namespace std;
	int x;
	stringstream ss;
	
	if (argc < 2)
	{
		cout << "Zbyt malo argumentow\n";
		exit(1);
	}
	
	ss << argv[1];
	ss >> x;
	if (ss.fail() || !ss.eof())
	{
		cout << argv[1] << " - Bledny argument\n";
		exit(1);
	}
	
	if (x < 0 || x > 32)
	{
		cout << x << " - Nieprawidlowy numer wiersza\n";
		exit(1);
	}
	
	Wiersz pas(x);
	
	for (int i = 2; i < argc; i++)
	{
		ss.clear();
		ss.str(argv[i]);
		
		ss >> x;
		if (ss.fail() || !ss.eof())
		{
			cout << argv[i] << " - Nieprawidlowa dana\n";
			continue;
		}
		
		try { cout << x << " - " << pas.wspolczynnik(x) << endl; }
		catch (const WrongValueException &ex)
		{
			cout << x << " - Liczba spoza zakresu\n";
		}
	}
	
	return 0;
}

Wiersz::Wiersz(int n) : size(n), row(new int[n + 1])
{
	int half_i;
	row[0] = 1;
	
	for (int i = 0; i < n; i++)
	{	
		half_i = i / 2;
		
		if (i % 2)
			row[half_i + 1] = 2 * row[half_i];
		
		for (int j = half_i; j > 0; j--)
			row[j] += row[j-1];
	}
		
	for (int b = 0, e = n; b < e; b++, e--)
		row[e] = row[b];
}

int Wiersz::wspolczynnik(int m)
{
	if (m < 0 || m > size)
		throw WrongValueException("Liczba spoza zakresu");
	return row[m];
}