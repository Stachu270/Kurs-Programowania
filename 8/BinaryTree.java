import java.lang.*;
import java.util.*;
import java.awt.*;
import java.io.*;
import java.net.*;

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