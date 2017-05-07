package myproj;

import java.awt.*;

/** 
* Represents circle shape in a coordinate space with its middle point and radius.
* <code>Circ</code> has border and filling color attributes.
* <code>Circ</code> can be drawn, translated and scaled.
* To create a <code>Circ</code> one should define two points. First (middle) point is set in a constructor, 
* second is passed by <code>addPoint()</code> function. Distance between these two points equals to circle's radius.
* @author Michał Słowik
* @version 1
*/
public class Circ extends ShapePlus
{	
	private float r;
	
	/**
	* Creates <code>Circ</code> using origin (middle) point and sets its border and filling color to <code>col</code>.
	* @param p middle point
	* @param col filling and border color
	*/
	public Circ(Point p, Color col)
	{
		super(col);
		centerPoint.x = p.x;
		centerPoint.y = p.y;
		r = 0;
	}
	
	/**
	* Constructs a new <code>Circ</code> with data from the <code>dataString</code>.
	* dataString should consist of:
	* <ol>
	* 	<li>shape type,</li>
	* 	<li>middle point X coordinate,</li>
	* 	<li>middle point Y coordinate,</li>
	* 	<li>border RGB color value,</li>
	* 	<li>filling RGB color value,</li>
	* 	<li>radius value,</li>
	* </ol>
	* separated by one space.
	* @param dataString String containing shape's data.
	*/
	public Circ(String dataString)
	{
		super(dataString);
		String tab[] = dataString.split(" ");
		
		r = Float.parseFloat(tab[5]);
		
		bounds.x = centerPoint.x - (int)r;
		bounds.y = centerPoint.y - (int)r;
		bounds.width = (int)(2*r + 1);
		bounds.height = (int)(2*r + 1);
	}
	
	/**
	* Draws the <code>Circ</code>.
	* @param g2d the <code>Graphics2D</code> context in which to draw
	*/
	@Override
	public void draw(Graphics2D g2d)
	{
		g2d.setColor(fillColor);
		g2d.fillOval(centerPoint.x - (int)r, centerPoint.y - (int)r, (int)(2*r + 1), (int)(2*r + 1));

		if (isSelected)
			drawSelected(g2d);
	}
	
	/**
	* Adds a specified <code>Point</code> to build a circle.
	* @param p specified <code>Point</code> to be added
	* @param save <code>true</code> if specified <code>Point</code> should be saved; <code>false</code> if it is temporary.
	*/
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
	
	/**
	* Tests if a specified Point is inside the boundary of the <code>Circ</code>.
	* @param p the specified <code>Point</code> to be tested.
	* @return <code>true</code> if the specified <code>Point</code> is inside the boundary of the <code>Circ</code>; 
	* <code>false</code> otherwise.
	*/
	@Override
	public boolean contains(Point p)
	{
		if (centerPoint.distance(p) <= r)
			return true;
		return false;
	}
	
	/**
	 * Translates the circle horizontally (by <code>dx</code>) and vertically (by <code>dy</code>).
	 * @param dx horizontal translation
	 * @param dy vertical translation
	 */
	@Override
	public void translate(int dx, int dy)
	{
		bounds.x += dx;
		centerPoint.x += dx;
		
		bounds.y += dy;
		centerPoint.y += dy;
	}
	
	/**
	 * Scales the circle equally horizontally and vertically
	 * @param s scaling factor; s &gt; 0
	 */
	@Override
	public void scale(float s)
	{
		//scalingFactor = s;
		
		r *= s;
		
		bounds.x = centerPoint.x - (int)r;
		bounds.y = centerPoint.y - (int)r;
		bounds.width = (int)(2*r + 1);
		bounds.height = (int)(2*r + 1);
	}
	
	
	/**
	 * Writes this <code>Circ</code> data to <code>String</code>.
	 * Returned <code>String</code> contains of:
	 * <ol>
	 * 	<li>shape type,</li>
	 * 	<li>middle point X coordinate,</li>
	 * 	<li>middle point Y coordinate,</li>
	 * 	<li>border RGB color value,</li>
	 * 	<li>filling RGB color value,</li>
	 * 	<li>radius value,</li>
	 * </ol>
	 * separated by one space.
	 * @return String with <code>Rect</code> data.
	 */
	@Override
	public String writeToString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("c ");
		sb.append(super.writeToString());
		sb.append(r);
		sb.append(' ');
		
		return sb.toString();
	}
}