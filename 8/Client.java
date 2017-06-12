import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

class Client
{
	Socket socket;
	PrintWriter out;
	BufferedReader in;
	
	Client()
	{
		this.socket = null;
		this.out = null;
		this.in = null;
	}
	
	public void listenSocket()
	{
		try
		{
			socket = new Socket("localhost", 4444);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		}
		catch (UnknownHostException ue)
		{
			System.out.println("Unknown host: localhost");
			System.exit(1);
		}
		catch  (IOException ioe)
		{
			System.out.println("No I/O");
			System.exit(1);
		}
	}
	
	private void foo()
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String buffer;
		
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
			
			String ans = new String("");
			out.println(buffer);
			do
			{
				try
				{
					ans = in.readLine();
				}
				catch (IOException ioe)
				{
					System.out.println("Nie wiem!");
				}
				
				System.out.println(ans);
				if (ans.equals("end"))
					end = true;
			} while (!ans.equals("-"));
			
			/*switch (ans)
			{
				case "ins":
					try
					{
						ans = in.readLine();
					}
					catch (IOException ioe)
					{
						System.out.println("Nie wiem!");
					}
					break;
				case "draw":
					
					break;
				case "err":
					
					break;
				default:
					
			}*/
		
		}
	}
	
	public static void main(String[] args)
	{
		Client frame = new Client();
		frame.listenSocket();
		
		frame.foo();
	}
}

