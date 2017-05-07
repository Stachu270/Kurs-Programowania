package myproj;

import java.awt.*;

/** 
* Represents rectangle shape in a coordinate space with its upper left corner coordinates, 
* width and height.
* <code>Rect</code> has a color and origin point (first point set during creation).
* <code>Rect</code> can be drawn, translated and scaled.
* To create a <code>Rect</code> one should define two points. First (origin) point is set in a constructor, 
* second is passed by <code>addPoint()</code> function.
* @author Michał Słowik
* @version 1
*/
public class Rect extends ShapePlus
{	
	private float x, y, width, height;
	private Point origin = new Point();
	
	/**
	* Creates <code>Rect</code> using origin point and sets its border and filling color to <code>col</code>.
	* @param p origin point
	* @param col filling and border color
	*/
	public Rect(Point p, Color col)
	{
		super(col);
		x = origin.x = p.x;
		y = origin.y = p.y;
		width = height = 1;
	}
	
	/**
	* Constructs a new <code>Rect</code> with data from the <code>dataString</code>.
	* dataString should consist of:
	* <ol>
	* 	<li>shape type,</li>
	* 	<li>middle point X coordinate,</li>
	* 	<li>middle point Y coordinate,</li>
	* 	<li>border RGB color value,</li>
	* 	<li>filling RGB color value,</li>
	* 	<li>origin point X coordinate,</li>
	* 	<li>origin point Y coordinate,</li>
	* 	<li>upper left X coordinate,</li>
	* 	<li>upper left Y coordinate,</li>
	* 	<li>width,</li>
	* 	<li>height</li>
	* </ol>
	* separated by one space.
	* @param dataString String containing shape's data.
	*/
	public Rect(String dataString)
	{
		super(dataString);
		
		String tab[] = dataString.split(" ");
		
		origin.x = Integer.parseInt(tab[5]);
		origin.y = Integer.parseInt(tab[6]);
		
		x = Float.parseFloat(tab[7]);
		y = Float.parseFloat(tab[8]);
		width = Float.parseFloat(tab[9]);
		height = Float.parseFloat(tab[10]);
		
		bounds.x = (int)x; bounds.y = (int)y;
		bounds.width = (int)width; bounds.height = (int)height;
	}
	
	/**
	* Draws the <code>Rect</code>.
	* @param g2d the <code>Graphics2D</code> context in which to draw
	*/
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
	
	/**
	* Adds a specified <code>Point</code> to build a rectangle.
	* @param p specified <code>Point</code> to be added
	* @param save <code>true</code> if specified <code>Point</code> should be saved; <code>false</code> if it is temporary.
	*/
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
	
	/**
	* Tests if a specified Point is inside the boundary of the <code>Rect</code>.
	* @param p the specified <code>Point</code> to be tested.
	* @return <code>true</code> if the specified <code>Point</code> is inside the boundary of the <code>Rect</code>; 
	* <code>false</code> otherwise.
	*/
	@Override
	public boolean contains(Point p)
	{
		if (p.x >= this.x && p.x <= this.x + width &&
			p.y >= this.y && p.y <= this.y + height)
			return true;
		return false;
	}
	
	/**
	 * Translates the rectangle horizontally (by <code>dx</code>) and vertically (by <code>dy</code>).
	 * @param dx horizontal translation
	 * @param dy vertical translation
	 */
	@Override
	public void translate(int dx, int dy)
	{
		x = bounds.x += dx;
		centerPoint.x += dx;
		
		y = bounds.y += dy;
		centerPoint.y += dy;
	}
	
	/**
	 * Scales the rectangle equally horizontally and vertically
	 * @param s scaling factor; s &gt; 0
	 */
	@Override
	public void scale(float s)
	{
		//scalingFactor = s;
		
		width *= s;
		height *= s;
		
		x = centerPoint.x - width/2;
		y = centerPoint.y - height/2;
		
		bounds.x = (int)x; bounds.y = (int)y;
		bounds.width = (int)width; bounds.height = (int)height;
	}
	
	/**
	 * Writes this <code>Rect</code> data to <code>String</code>.
	 * Returned <code>String</code> contains of:
	 * <ol>
	 * 	<li>shape type,</li>
	 * 	<li>middle point X coordinate,</li>
	 * 	<li>middle point Y coordinate,</li>
	 * 	<li>border RGB color value,</li>
	 * 	<li>filling RGB color value,</li>
	 * 	<li>origin point X coordinate,</li>
	 * 	<li>origin point Y coordinate,</li>
	 * 	<li>upper left X coordinate,</li>
	 * 	<li>upper left Y coordinate,</li>
	 * 	<li>width,</li>
	 * 	<li>height</li>
	 * </ol>
	 * separated by one space.
	 * @return String with <code>Rect</code> data.
	 */
	@Override
	public String writeToString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("r ");
		sb.append(super.writeToString());
		
		sb.append(origin.x);
		sb.append(' ');
		sb.append(origin.y);
		sb.append(' ');
		
		sb.append(x);
		sb.append(' ');
		sb.append(y);
		sb.append(' ');
		sb.append(width);
		sb.append(' ');
		sb.append(height);
		sb.append(' ');
		
		return sb.toString();
	}
}