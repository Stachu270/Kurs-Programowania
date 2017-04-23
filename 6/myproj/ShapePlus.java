package myproj;

import java.awt.*;

/** 
@author Micha³ S³owik
@version v1
Abstract class for custom shapes
*/
public abstract class ShapePlus
{
	protected Point centerPoint;
	protected Rectangle bounds;
	protected Color fillColor, borderColor;
	protected boolean isBorder, isSelected;
	//protected BasicStroke rolloverStroke; protected Color rolloverColor; protected 
	protected float scalingFactor;
	private BasicStroke selectionStroke;
	
	public ShapePlus(Color color)
	{	
		this.fillColor = color;
		this.borderColor = color;
		//this.rolloverColor = new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue());
		
		this.centerPoint = new Point(0, 0);
		this.bounds = new Rectangle(0, 0, 0, 0);
		
		//float dash[] = {10f};
		//this.rolloverStroke = new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 1f, dash, 0f);
		
		//this.isRollover = false;
		this.isBorder = false;
		this.isSelected = false;
		
		this.scalingFactor = 1f;
		//this.scalingFactor = 1.2f;
		
		float dash[] = {6f};
		this.selectionStroke = new BasicStroke(1f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 1f, dash, 0f);
	}
	
	//abstract public boolean contains(int x, int y);
	abstract public boolean contains(Point p);
	abstract public void addPoint(Point p, boolean save);
	public void addPoint(Point p) { addPoint(p, false); }
	abstract public void draw(Graphics2D g2d);
	//abstract public void translate(Point beg, Point end);
	//abstract public void translate(Distance vect);
	abstract public void translate(int dx, int dy);
	abstract public void scale(float s);
	//abstract public void scale(int n);
	
	public void deactive() { isSelected = false; }
	public void active() { isSelected = true; }
	public Rectangle getBounds() { return bounds; }
	public void setColor(Color col) { this.fillColor = col; }
	
	protected void drawSelected(Graphics2D g2d)
	{
		Rectangle r = new Rectangle(bounds);
		int posTabX[] = { r.x, r.x, r.x + r.width - 5, r.x + r.width - 5};
		int posTabY[] = { r.y, r.y + r.height - 5, r.y + r.height - 5, r.y};
		r.x += 2; r.y += 2; r.width -= 5; r.height -= 5;
		Stroke prevStroke = g2d.getStroke();
		
		g2d.setColor(Color.white);
		g2d.draw(r);
		
		g2d.setColor(new Color(0, 120, 215));
		g2d.setStroke(selectionStroke);
		g2d.draw(r);
		
		g2d.setStroke(prevStroke);
		for (int i = 0; i < posTabX.length; i++)
		{
			g2d.setColor(Color.white);
			g2d.fillRect(posTabX[i], posTabY[i], 4, 4);
			g2d.setColor(new Color(85, 85, 85));
			g2d.drawRect(posTabX[i], posTabY[i], 4, 4);
		}
	}
}