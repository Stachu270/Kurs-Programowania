import javax.swing.*;
import java.awt.*;
import java.util.*;

public abstract class Animal extends Thread
{
	protected int x, y;
	private int pixSize;
	private Random generator;
	protected Point pos;
	private Color color;
	
	Animal(int m, int n, int size, Color col)
	{
		generator = new Random(System.currentTimeMillis());
		x = m;
		y = n;
		pixSize = size;
		col = new Color(col.getRGB());
		pos = new Point(generator.nextInt(x), generator.nextInt(y));
	}
	
	protected void move(int vx, int vy)
	{	
		if (vx > 0 && pos.x < x-1)
			pos.x++;
		if (vx < 0 && pos.x > 0)
			pos.x--;
		if (vy > 0 && pos.y < y-1)
			pos.y++;
		if (vy < 0 && pos.y > 0)
			pos.y--;
	}
	
	protected void draw(Graphics2D g2d)
	{
		g2d.setColor(col);
		g2d.fillRectangle(pos.x * pixSize, pos.y * pixSize, pixSize, pixSize);
	}
}

class Wolf extends Animal
{
	public void run()
	{
		move(1, 1);
		draw();
		this.sleep(500);
	}
}

/*class Hare extends Animal
{
	
}*/