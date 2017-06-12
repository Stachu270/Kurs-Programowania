import java.lang.*;
import java.util.*;
import java.nio.charset.*;
import java.awt.*;
import java.io.*;
import java.net.*;

public class Tree
{
	ServerSocket server;
	Socket client;
	BufferedReader in;
	PrintWriter out;
	String data;
	Wrapper demo;
	
	Tree()
	{
		server = null;
		client = null;
		in = null;
		out = null;
		data = "";
		this.demo = new Wrapper();
		
		try
		{
			server = new ServerSocket(4444); 
		} 
		catch (IOException ioe)
		{
			System.out.println("Cos sie... Cos sie popsulo, nie  bylo mnie slychac");
		}
	}
	
	public void listenSocket()
	{
		try
		{
			client = server.accept();
		} 
		catch (IOException ioe)
		{
			System.out.println("Accept failed: 4444\nclient = server.accept();");
		}
		try
		{
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			//out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream(), Charset.forName("UTF-8").newEncoder())), true);
			out = new PrintWriter(client.getOutputStream(), true);
		}
		catch (IOException ioe)
		{
			System.out.println("Accept failed: 4444\n"
				+ "in = new BufferedReader(new InputStreamReader(client.getInputStream()));\n"
				+ "out = new PrintWriter(client.getOutputStream(), true);");
		}
		
		boolean end = false;
		while (!end)
		{
			try
			{
				data = in.readLine();
				System.out.println(data);
				end = foo(data);
			} 
			catch (IOException ioe)
			{
				System.out.println("Read failed");
			} 
		}
	}
	
	protected void finalize()
	{
		try
		{
			in.close();
			out.close();
			client.close();
			server.close();
		} 
		catch (IOException ioe)
		{
			System.out.println("Could not close.");
			System.exit(-1);
		}
	}
	
	public static void main(String args[])
	{
		Tree server = new Tree();
		server.listenSocket();
	}
	
	private boolean foo(String buffer)
	{
		ArrayList<String> tokens;
		
		tokens = new ArrayList<>(Arrays.asList(buffer.split("\\s+")));
			
		switch (tokens.get(0))
		{
			case "-new":
				try
				{
					demo.create(tokens);
					out.println("new");
					out.println("Tree created");
					out.println("-");
				}
				catch (StringException se)
				{
					out.println("err");
					out.println(se.getMessage());
					out.println("-");
					System.out.println(se.getMessage());
				}
				break;
			case "-ins":
				try
				{
					demo.insert(tokens);
					out.println("draw");
					ArrayList<StringBuilder> t = demo.draw();
					for (StringBuilder sb : t)
						out.println(sb.toString());
					out.println("-");
				}
				catch (StringException se)
				{
					out.println("err");
					out.println(se.getMessage());
					System.out.println(se.getMessage());
					out.println("-");
				}
				break;
			case "-del":
				try
				{
					demo.del(tokens);
					out.println("draw");
					ArrayList<StringBuilder> t = demo.draw();
					for (StringBuilder sb : t)
						out.println(sb.toString());
					out.println("-");
				}
				catch (StringException se)
				{
					out.println("err");
					out.println(se.getMessage());
					System.out.println(se.getMessage());
					out.println("-");
				}
				break;
			case "-srch":
				try
				{
					boolean ret = demo.search(tokens);
					System.out.println(ret);
					out.println("search");
					out.println(tokens.get(1));
					out.println(ret);
					out.println("-");
				}
				catch (StringException se)
				{
					out.println("err");
					out.println(se.getMessage());
					System.out.println(se.getMessage());
					out.println("-");
				}
				break;
			case "-draw":
				out.println("draw");
				ArrayList<StringBuilder> t = demo.draw();
				for (StringBuilder sb : t)
					out.println(sb.toString());
				out.println("-");
				break;
			case "-end":
				out.println("end");
				System.out.println("Milo bylo poznac ;)");
				out.println("-");
				return true;
			default:
				out.println("err");
				out.println("Nieprawidlowe polecenie. Przeczytaj instrukcje");
				System.out.println("Nieprawidlowe polecenie. Przeczytaj instrukcje");
				out.println("-");
		}
		return false;
	}
	
	public void fun(String args[])
	{
		Wrapper demo = new Wrapper();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String buffer;
		ArrayList<String> tokens;
		
		System.out.println("Instrukcje:");
		System.out.println("-new (i/d/s)                - nowe drzewo int/double/string");
		System.out.println("-ins val1 [val2 val3 ...]   - dodanie elementu(ow) do drzewa");
		System.out.println("-del [val1 val2 ...]        - usuniecie calego drzewa lub podanych wartosci");
		System.out.println("-srch val                   - szukanie danej wartosci w drzewie");
		System.out.println("-draw                       - rysuje drzewo");
		System.out.println("-end                        - konczy program");
		
		boolean end = false;
		while (!end)
		{
			try
			{
				buffer = br.readLine();
			}
			catch (IOException ioe)
			{
				System.out.println("IOException thrown. nie wiem dlaczego :(");
				buffer = new String("");
			}
			tokens = new ArrayList<>(Arrays.asList(buffer.split("\\s+")));
			
			switch (tokens.get(0))
			{
				case "-new":
					try
					{
						demo.create(tokens);
					}
					catch (StringException se)
					{
						System.out.println(se.getMessage());
					}
					break;
				case "-ins":
					try
					{
						demo.insert(tokens);
						demo.draw();
					}
					catch (StringException se)
					{
						System.out.println(se.getMessage());
					}
					break;
				case "-del":
					try
					{
						demo.del(tokens);
						demo.draw();
					}
					catch (StringException se)
					{
						System.out.println(se.getMessage());
					}
					break;
				case "-srch":
					try
					{
						System.out.println(demo.search(tokens));
					}
					catch (StringException se)
					{
						System.out.println(se.getMessage());
					}
					break;
				case "-draw":
					demo.draw();
					break;
				case "-end":
					System.out.println("Milo bylo poznac ;)");
					end = true;
					break;
				default:
					System.out.println("Nieprawidlowe polecenie. Przeczytaj instrukcje");
			}
		}	
	}
}

class StringException extends Exception
{
	StringException(String str)
	{
		super(str);
	}
}

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

class Node<T extends Comparable<T>>
{
	final T element;
	Node<T> left;
	Node<T> right;
	
	public Node(T e)
	{
		this.element = e;
		this.left = null;
		this.right = null;
	}
}

class BinaryTree<T extends Comparable<T>>
{
	private Node<T> Root;
	private HashMap<Point, String> triangle;
	private int maxLen, maxHeight;
	
	private ArrayList<Integer> maxLengths;
	private final char ver = (char)9474;
	private final char hor = (char)9472;
	private final char up = (char)9484;
	private final char dwn = (char)9492;
	
	
	public BinaryTree()
	{
		this.Root = null;
		this.triangle = new HashMap<>();
		this.maxHeight = this.maxLen = 0;
		
		this.maxLengths = new ArrayList<>();
	}
	
	public void insert(T e)
	{
		Node<T> tmp = Root;
		
		if (Root == null)
		{
			Root = new Node<T>(e);
			draw();
			return;
		}
		while (true)
		{
			if (tmp.element.compareTo(e) < 0)
			{
				if (tmp.right == null)
				{
					tmp.right = new Node<T>(e);
					break;
				}
				else
					tmp = tmp.right;
			}
			else
			{
				if (tmp.left == null)
				{
					tmp.left = new Node<T>(e);
					break;
				}
				else
					tmp = tmp.left;
			}
		}
	}
	
	public void delete(T el)
	{
		if (!search(el))
			return;
		
		Node<T> parent = Root;
		Node<T> del = Root;
		
		while (!del.element.equals(el))
		{	
			parent = del;
			if (parent.element.compareTo(el) < 0)
				del = parent.right;
			else
				del = parent.left;
		}
		
		if (del == Root)
		{
			Root = del.right;
			
			if (Root == null)
			{
				Root = del.left;
			}
			else
			{
				parent = Root;
				while (parent.left != null)
					parent = parent.left;
				parent.left = del.left;
			}
		}
		else if (parent.element.compareTo(el) < 0)
		{
			parent.right = del.right;
			
			if (parent.right == null)
			{
				parent.right = del.left;
			}
			else
			{
				parent = parent.right;
				while (parent.left != null)
					parent = parent.left;
				parent.left = del.left;
			}
		}
		else
		{
			parent.left = del.left;
			
			if (parent.left == null)
			{
				parent.left = del.right;
			}
			else
			{
				parent = parent.left;
				while (parent.right != null)
					parent = parent.right;
				parent.right = del.right;
			}
		}
	}
	
	private boolean srch(Node<T> node, T e)
	{
		if (node == null)
			return false;
		
		if (node.element.equals(e))
			return true;
		
		if (node.element.compareTo(e) < 0)
			return srch(node.right, e);
		else
			return srch(node.left, e);
	}
	
	public boolean search(T e)
	{
		return srch(Root, e);
	}
	
	public void printAscending()
	{
		pA(Root);
	}
	
	private void pA(Node<T> n)
	{
		if (n == null)
			return;
		pA(n.left);
		System.out.println(n.element.toString());
		pA(n.right);
	}
	
	public void printDescending()
	{
		pD(Root);
	}
	
	private void pD(Node<T> n)
	{
		if (n == null)
			return;
		pD(n.right);
		System.out.println(n.element.toString());
		pD(n.left);
	}
	
	public void clear()
	{
		Root = null;
	}
	
	public void printAll()
	{
		//if (Root == null)
			//return;
		triangle.clear();
		this.maxHeight = 0;
		this.maxLen = 4;	// bo null ma 4 litery
		
		prnt(Root, 0, 0);
		
		//System.out.println(Integer.toString(maxHeight));
		//System.out.println(Integer.toString(maxLen));
		
		
		int p = (int)Math.pow(2, maxHeight - 1);
		int max = (maxLen + 1) * p;
		
		//System.out.println(Integer.toString(max));
		
		/*for (String s : triangle.values())
			System.out.println(s);
		
		for (Point pp : triangle.keySet())
			System.out.println(pp.toString());
		*/
		String str;
		for (int i = 0; i < maxHeight; i++)
		{
			int tmp = (int)Math.pow(2, i);
			int currPos = 0;
			for (int j = 0; j < tmp; j++)
			{
				//System.out.println(Integer.toString(j) + " " + Integer.toString(i));
				
				str = triangle.get(new Point(j, i));
				if (str != null)
				{
					//print
					int offset = max/(2*tmp);
					
					int posX = 2*j * offset + offset - (int)(str.length()/2);
					//System.out.println(posX);
					spaces(posX - currPos);
					System.out.print(" " + str + " ");
					currPos = posX + str.length() + 1;
				}
			}
			System.out.print('\n');
		}
	}
	
	private void lengthsOnLevels()
	{
		maxLengths.clear();
		
		lol(Root, 0, 0);
		
		for (int i = 1; i < maxLengths.size(); i++)
			maxLengths.set(i, maxLengths.get(i) + 1);
	}
	
	private void lol(Node<T> node, int x, int y)
	{
		if (node == null)
			return;
		
		if (maxLengths.size() == y)
			maxLengths.add(0);
		
		if (maxLengths.get(y) < node.element.toString().length())
			maxLengths.set(y, node.element.toString().length());
		
		lol(node.left, 2*x, y+1);
		lol(node.right, 2*x + 1, y+1);
	}
	
	public ArrayList<StringBuilder> draw()
	{
		lengthsOnLevels();
		
		ArrayList<StringBuilder> strArr = new ArrayList<>();
		
		prt(Root, 0, 0, strArr);
		
		for (StringBuilder sb : strArr)
			System.out.println(sb.toString());
		
		return strArr;
	}
	
	private int prt(Node<T> node, int x, int y, ArrayList<StringBuilder> list)
	{
		if (node == null)
			return -1;
		
		ArrayList<StringBuilder> tmp = new ArrayList<>();
		
		int posLeft = prt(node.left, 2*x, y+1, list);
		int posRight = prt(node.right, 2*x + 1, y+1,tmp);
		int pos = list.size();
		
		if (posLeft != -1)
		{
			list.get(posLeft).insert(0, up);
			
			for (int j = 1; j < maxLengths.get(y); j++)
				list.get(posLeft).insert(0, ' ');
			
			for (int i = 0; i < posLeft; i++)
			{
				for (int j = 0; j < maxLengths.get(y); j++)
					list.get(i).insert(0, ' ');
			}
			for (int i = posLeft + 1; i < list.size(); i++)
			{
				list.get(i).insert(0, ver);
				for (int j = 1; j < maxLengths.get(y); j++)
					list.get(i).insert(0, ' ');
			}
		}
		if (posRight != -1)
		{
			tmp.get(posRight).insert(0, dwn);
			
			for (int j = 1; j < maxLengths.get(y); j++)
				tmp.get(posRight).insert(0, ' ');
			
			for (int i = 0; i < posRight; i++)
			{
				tmp.get(i).insert(0, ver);
				for (int j = 1; j < maxLengths.get(y); j++)
					tmp.get(i).insert(0, ' ');
			}
			for (int i = posRight + 1; i < tmp.size(); i++)
			{
				for (int j = 0; j < maxLengths.get(y); j++)
					tmp.get(i).insert(0, ' ');
			}
		}
		
		
		list.add(new StringBuilder());
		for (int i = node.element.toString().length() + 1; i < maxLengths.get(y); i++)
			list.get(pos).append(hor);
		
		if (node.element.toString().length() < maxLengths.get(y))
			list.get(pos).append(' ');
		
		list.get(pos).append(node.element.toString());
		
		if (posRight != -1)
			list.addAll(tmp);
		
		return pos;
	}
	
	private void spaces(int n)
	{
		for (int i = 0; i < n; i++)
			System.out.print(' ');
	}
	
	private void prnt(Node<T> e, int x, int y)
	{	
		//for (int i = 0; i < s; i++)
			//System.out.print("    ");
		//System.out.println(x + " " + y);
		
		if (y + 1 > this.maxHeight)
			this.maxHeight = y + 1;
		
		if (e != null)
		{
			if (e.element.toString().length() > this.maxLen)
				this.maxLen = e.element.toString().length();
			triangle.put(new Point(x, y), new String(e.element.toString()));
			//System.out.println(x + " " + y + " " + e.element.toString());
		}
		else
		{
			triangle.put(new Point(x, y), new String("null"));
			return;
		}
		
		prnt(e.left, 2*x, y+1);
		prnt(e.right, 2*x + 1, y+1);
	}
}