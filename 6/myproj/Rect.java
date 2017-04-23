package myproj;

import java.awt.*;

public class Rect extends ShapePlus
{	
	private float x, y, width, height;
	private Point origin = new Point();
	
	public Rect(Point p, Color col)
	{
		super(col);
		x = origin.x = p.x;
		y = origin.y = p.y;
		width = height = 1;
	}
	
	@Override
	public void draw(Graphics2D g2d)
	{
		g2d.setColor(fillColor);
		//g2d.scale(scalingFactor, scalingFactor);
		g2d.fillRect((int)x, (int)y, (int)width, (int)height);
		
		if (isSelected)
			drawSelected(g2d);
		
		//g2d.scale(1, 1);
	}
	
	@Override
	public void addPoint(Point p, boolean save)
	{
		width = Math.abs(p.x - origin.x) + 1;
		height = Math.abs(p.y - origin.y) + 1;
		
		x = (p.x < origin.x) ? p.x : origin.x; 
		y = (p.y < origin.y) ? p.y : origin.y;
		
		if (save)
		{
			bounds.x = (int)x; bounds.y = (int)y;
			bounds.width = (int)width; bounds.height = (int)height;
			centerPoint.x = (int)(x + width/2);
			centerPoint.y = (int)(y + height/2);
		}
	}
	
	@Override
	public boolean contains(Point p)
	{
		if (p.x >= this.x && p.x <= this.x + width &&
			p.y >= this.y && p.y <= this.y + height)
			return true;
		return false;
	}
	
	@Override
	public void translate(int dx, int dy)
	{
		x = bounds.x += dx;
		centerPoint.x += dx;
		
		y = bounds.y += dy;
		centerPoint.y += dy;
	}
	
	@Override
	public void scale(float s)
	{
		scalingFactor = s;
		
		width *= s;
		height *= s;
		
		x = centerPoint.x - width/2;
		y = centerPoint.y - height/2;
		
		bounds.x = (int)x; bounds.y = (int)y;
		bounds.width = (int)width; bounds.height = (int)height;
	}
}