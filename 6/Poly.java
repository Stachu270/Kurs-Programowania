package myproj;

import java.awt.*;

public class Poly extends ShapePlus
{	
	private Polygon polygon;
	private float[] xPoints, yPoints;
	private int nPoints;
	
	public Poly(Point p, Color col)
	{
		super(col);
		polygon = new Polygon();
		polygon.addPoint(p.x, p.y);
		polygon.addPoint(p.x, p.y);
	}
	
	@Override
	public void draw(Graphics2D g2d)
	{
		g2d.setColor(fillColor);
		if (polygon.npoints == 2)
			g2d.drawLine(polygon.xpoints[0], polygon.ypoints[0], polygon.xpoints[1], polygon.ypoints[1]);
		else
			g2d.fillPolygon(polygon);

		if (isSelected)
			drawSelected(g2d);
	}
	
	@Override
	public void addPoint(Point p, boolean save)
	{
		polygon.xpoints[polygon.npoints - 1] = p.x;
		polygon.ypoints[polygon.npoints - 1] = p.y;
		
		if (save)
		{
			polygon.addPoint(p.x, p.y);
			
			bounds = polygon.getBounds();
			
			centerPoint.x = bounds.x + bounds.width/2;
			centerPoint.y = bounds.y + bounds.height/2;
			
			nPoints = polygon.npoints;
			xPoints = new float[nPoints];
			yPoints = new float[nPoints];
			for (int i = 0; i < nPoints; i++)
			{
				xPoints[i] = polygon.xpoints[i];
				yPoints[i] = polygon.ypoints[i];
			}
		}
	}
	
	public void closePoly()
	{
		polygon.npoints = --nPoints;
	}
	
	@Override
	public boolean contains(Point p)
	{
		return polygon.contains(p);
	}
	
	@Override
	public void translate(int dx, int dy)
	{
		polygon.translate(dx, dy);
		for (int i = 0; i < nPoints; i++)
		{
			xPoints[i] += dx;
			yPoints[i] += dy;
		}
		
		centerPoint.x += dx;
		centerPoint.y += dy;
		
		bounds.x += dx;
		bounds.y += dy;
	}
	
	@Override
	public void scale(float s)
	{
		scalingFactor = s;
		//bounds.x = bounds.width = centerPoint.x;
		//bounds.y = bounds.height = centerPoint.y;
		polygon.reset();
		
		for (int i = 0; i < nPoints; i++)
		{
			xPoints[i] = (xPoints[i] - centerPoint.x) * s + centerPoint.x;
			//polygon.xpoints[i] = (int)xPoints[i];
			
			yPoints[i] = (yPoints[i] - centerPoint.y) * s + centerPoint.y;
			//polygon.ypoints[i] = (int)yPoints[i];
			
			//bounds.x = (polygon.xpoints[i] < bounds.x) ? polygon.xpoints[i] : bounds.x;
			//bounds.width = (polygon.xpoints[i] > bounds.width) ? polygon.xpoints[i] : bounds.width;
			
			//bounds.y = (polygon.ypoints[i] < bounds.y) ? polygon.ypoints[i] : bounds.y;
			//bounds.height = (polygon.ypoints[i] > bounds.height) ? polygon.ypoints[i] : bounds.height;
			
			polygon.addPoint((int)xPoints[i], (int)yPoints[i]);
		}
		//bounds.width = bounds.width - bounds.x;
		//bounds.height = bounds.height - bounds.y;
		bounds = polygon.getBounds();
	}
}