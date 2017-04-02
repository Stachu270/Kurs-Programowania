public class a
{
	enum En
	{
		A(1),
		B(2);
		
		public int num;
		
		En(int i) { num = i; }
		
		public void set(int i ) { num = i; }
		public int get() { return num; }
	}
	
	public static void main(String args[])
	{
		En x = En.A;
		En y = En.A;
		
		x.set(5);
		y.set(8);
		System.out.println(x.get() + "    " + y.get());
	}
}