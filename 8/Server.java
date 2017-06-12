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