package myproj;

import java.awt.*;

/** 
* Represents polygon shape in a coordinate space with its vertexes' coordinates.
* <code>Poly</code> has border and filling color attributes.
* <code>Poly</code> can be drawn, translated and scaled.
* To create a <code>Poly</code> one should define minimum 3 points. First point is set in a constructor, 
* second and subsequent ones are passed by <code>addPoint()</code> function.
* When all vertex points are added one should call <code>closePoly</code> function to finish creation process.
* @author Michał Słowik
* @version 1
*/
public class Poly extends ShapePlus
{	
	private Polygon polygon;
	private float[] xPoints, yPoints;
	//private int nPoints;
	
	/**
	* Creates <code>Poly</code> using origin point (first vertex) and sets its border and filling color to <code>col</code>.
	* @param p first vertex point
	* @param col filling and border color
	*/
	public Poly(Point p, Color col)
	{
		super(col);
		polygon = new Polygon();
		polygon.addPoint(p.x, p.y);
		polygon.addPoint(p.x, p.y);
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
	* 	<li>1st vertex: X coord and Y coord separated by one space,</li>
	* 	<li>2nd vertex: X coord and Y coord separated by one space,</li>
	* 	<li>3rd vertex: X coord and Y coord separated by one space,</li>
	* 	<li>optional next vertexes</li>
	* </ol>
	* separated by one space.
	* @param dataString String containing shape's data.
	*/
	public Poly(String dataString)
	{
		super(dataString);
		String tab[] = dataString.split(" ");
		
		int size = tab.length - 5;
		
		xPoints = new float[size];
		yPoints = new float[size];
		polygon = new Polygon();
		
		int j = 0;
		for (int i = 5; i < tab.length; i+= 2)
		{
			xPoints[j] = Float.parseFloat(tab[i]);
			yPoints[j] = Float.parseFloat(tab[i + 1]);
			polygon.addPoint((int)xPoints[j], (int)yPoints[j]);
			j++;
		}
		
		bounds = polygon.getBounds();
	}
	
	/**
	* Draws the <code>Poly</code>.
	* @param g2d the <code>Graphics2D</code> context in which to draw
	*/
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
	
	/**
	* Adds a specified (vertex) <code>Point</code> to build a polygon.
	* @param p specified <code>Point</code> to be added
	* @param save <code>true</code> if specified <code>Point</code> should be saved; <code>false</code> if it is temporary.
	*/
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
			
			//nPoints = polygon.npoints;
			xPoints = new float[polygon.npoints];
			yPoints = new float[polygon.npoints];
			for (int i = 0; i < polygon.npoints; i++)
			{
				xPoints[i] = polygon.xpoints[i];
				yPoints[i] = polygon.ypoints[i];
			}
		}
	}
	
	/**
	* Should be called after adding last point to finish creation process.
	*/
	public void closePoly()
	{
		polygon.npoints--;
	}
	
	/**
	* Tests if a specified Point is inside the boundary of the <code>Poly</code>.
	* @param p the specified <code>Point</code> to be tested.
	* @return <code>true</code> if the specified <code>Point</code> is inside the boundary of the <code>Poly</code>; 
	* <code>false</code> otherwise.
	*/
	@Override
	public boolean contains(Point p)
	{
		return polygon.contains(p);
	}
	
	/**
	 * Translates the polygon horizontally (by <code>dx</code>) and vertically (by <code>dy</code>).
	 * @param dx horizontal translation
	 * @param dy vertical translation
	 */
	@Override
	public void translate(int dx, int dy)
	{
		polygon.translate(dx, dy);
		for (int i = 0; i < polygon.npoints; i++)
		{
			xPoints[i] += dx;
			yPoints[i] += dy;
		}
		
		centerPoint.x += dx;
		centerPoint.y += dy;
		
		bounds.x += dx;
		bounds.y += dy;
	}
	
	/**
	 * Scales the polygon equally horizontally and vertically
	 * @param s scaling factor; s &gt; 0
	 */
	@Override
	public void scale(float s)
	{
		//scalingFactor = s;
		//bounds.x = bounds.width = centerPoint.x;
		//bounds.y = bounds.height = centerPoint.y;
		int n = polygon.npoints;
		polygon.reset();
		
		for (int i = 0; i < n; i++)
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
	
	/**
	 * Writes this <code>Poly</code> data to <code>String</code>.
	 * Returned <code>String</code> contains of:
	 * <ol>
	 * 	<li>shape type,</li>
	 * 	<li>middle point X coordinate,</li>
	 * 	<li>middle point Y coordinate,</li>
	 * 	<li>border RGB color value,</li>
	 * 	<li>filling RGB color value,</li>
	 * 	<li>1st vertex: X coord and Y coord separated by one space,</li>
	 * 	<li>2nd vertex: X coord and Y coord separated by one space,</li>
	 * 	<li>3rd vertex: X coord and Y coord separated by one space,</li>
	 * 	<li>optional next vertexes</li>
	 * </ol>
	 * separated by one space.
	 * @return String with <code>Rect</code> data.
	 */
	public String writeToString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("p ");
		sb.append(super.writeToString());
		
		for (int i = 0; i < polygon.npoints; i++)
		{
			sb.append(xPoints[i]);
			sb.append(' ');
			sb.append(yPoints[i]);
			sb.append(' ');
		}
		
		return sb.toString();
	}
}