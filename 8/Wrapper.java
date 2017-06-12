import java.lang.*;
import java.util.*;
import java.awt.*;
import java.io.*;
import java.net.*;

class Wrapper
{
	private BinaryTree<Integer> tree_int;
	private BinaryTree<Double> tree_double;
	private BinaryTree<String> tree_string;
		
	enum Type { INT, DOUBLE, STRING };
	private Type type;
		
	public Wrapper()
	{
		this.type = Type.STRING;
		this.tree_int = new BinaryTree<>();
		this.tree_double = new BinaryTree<>();
		this.tree_string = new BinaryTree<>();
	}
	
	public void create(ArrayList<String> tab) throws StringException
	{
		if (tab.size() != 2)
			throw new StringException("Nie utworzono drzewa. Niepoprawna liczba argumentow.");
		
		switch (tab.get(1))
		{
			case "i":
				type = Type.INT;
				tree_double.clear();
				tree_int.clear();
				tree_string.clear();
				break;
			case "d":
				type = Type.DOUBLE;
				tree_double.clear();
				tree_int.clear();
				tree_string.clear();
				break;
			case "s":
				type = Type.STRING;
				tree_double.clear();
				tree_int.clear();
				tree_string.clear();
				break;
			default:
				throw new StringException("Poprawne wartosci to:\ni   - integer\nd   - double\ns   - string");
		}
	}
	
	public void insert(ArrayList<String> tab) throws StringException
	{
		int i = 1;
		switch (type)
		{
			case INT:
				try
				{
					for (i = 1; i < tab.size(); i++)
						tree_int.insert(Integer.parseInt(tab.get(i)));
				}
				catch (NumberFormatException nfe)
				{
					throw new StringException(tab.get(i) + " - nieprawidlowa wartosc. wpisz liczbe typu int.");
				}
				break;
			case DOUBLE:
				try
				{
					for (i = 1; i < tab.size(); i++)
						tree_double.insert(Double.parseDouble(tab.get(i)));
				}
				catch (NumberFormatException nfe)
				{
					throw new StringException(tab.get(i) + " - nieprawidlowa wartosc. wpisz liczbe typu double.");
				}
				break;
			case STRING:
				for (i = 1; i < tab.size(); i++)
					tree_string.insert(tab.get(i));
			break;
		}
	}
	
	public void del(ArrayList<String> tab) throws StringException
	{
		if (tab.size() == 1)
		{
			tree_int.clear();
			tree_double.clear();
			tree_string.clear();
			return;
		}
		
		int i = 1;
		switch (type)
		{
			case INT:
				for (i = 1; i < tab.size(); i++)
				{
					try
					{
						tree_int.delete(Integer.parseInt(tab.get(i)));
					}
					catch (NumberFormatException nfe)
					{
						throw new StringException(tab.get(i) + " - nieprawidlowa wartosc. wpisz liczbe typu int.");
					}
				}
				break;
			case DOUBLE:
				for (i = 1; i < tab.size(); i++)
				{	
					try
					{
						tree_double.delete(Double.parseDouble(tab.get(i)));
					}
					catch (NumberFormatException nfe)
					{
						throw new StringException(tab.get(i) + " - nieprawidlowa wartosc. wpisz liczbe typu double.");
					}
				}
				break;
			case STRING:
				for (i = 1; i < tab.size(); i++)
				{	
					tree_string.delete(tab.get(i));
				}
				break;
		}
	}
	
	public boolean search(ArrayList<String> tab) throws StringException
	{
		if (tab.size() != 2)
			throw new StringException("tej opcji nalezy przekazac jeden argument");
		
		switch (type)
		{
			case INT:
				try
				{
					return tree_int.search(Integer.parseInt(tab.get(1)));
				}
				catch (NumberFormatException nfe)
				{
					throw new StringException(tab.get(1) + " - nieprawidlowa wartosc. wpisz liczbe typu int.");
				}
			case DOUBLE:
				try
				{
					return tree_double.search(Double.parseDouble(tab.get(1)));
				}
				catch (NumberFormatException nfe)
				{
					throw new StringException(tab.get(1) + " - nieprawidlowa wartosc. wpisz liczbe typu double.");
				}
			case STRING:
					return tree_string.search(tab.get(1));
		}
		return false;
	}
	
	public ArrayList<StringBuilder> draw()
	{
		switch (type)
		{
			case INT:
				return tree_int.draw();
			case DOUBLE:
				return tree_double.draw();
			case STRING:
				return tree_string.draw();
		}
		
		return new ArrayList<StringBuilder>();
	}
}