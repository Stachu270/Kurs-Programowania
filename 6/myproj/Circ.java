package myproj;

import java.awt.*;

public class Circ extends ShapePlus
{	
	private float r;
	
	public Circ(Point p, Color col)
	{
		super(col);
		centerPoint.x = p.x;
		centerPoint.y = p.y;
		r = 0;
	}
	
	@Override
	public void draw(Graphics2D g2d)
	{
		g2d.setColor(fillColor);
		g2d.fillOval(centerPoint.x - (int)r, centerPoint.y - (int)r, (int)(2*r + 1), (int)(2*r + 1));

		if (isSelected)
			drawSelected(g2d);
	}
	
	@Override
	public void addPoint(Point p, boolean save)
	{
		r = (float)centerPoint.distance(p);
		
		if (save)
		{
			bounds.x = centerPoint.x - (int)r;
			bounds.y = centerPoint.y - (int)r;
			bounds.width = (int)(2*r + 1);
			bounds.height = (int)(2*r + 1);
		}
	}
	
	@Override
	public boolean contains(Point p)
	{
		if (centerPoint.distance(p) <= r)
			return true;
		return false;
	}
	
	@Override
	public void translate(int dx, int dy)
	{
		bounds.x += dx;
		centerPoint.x += dx;
		
		bounds.y += dy;
		centerPoint.y += dy;
	}
	
	@Override
	public void scale(float s)
	{
		scalingFactor = s;
		
		r *= s;
		
		bounds.x = centerPoint.x - (int)r;
		bounds.y = centerPoint.y - (int)r;
		bounds.width = (int)(2*r + 1);
		bounds.height = (int)(2*r + 1);
	}
}