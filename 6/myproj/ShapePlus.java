package myproj;

import java.awt.*;

/** 
* Abstract class for custom shapes. Contains shape's color data, middle point coordinates, boundariess.
* @author Micha³ S³owik
* @version 1
*/
public abstract class ShapePlus
{
	/**
	 *	The middle point coordinates.
	 */
	protected Point centerPoint;
	
	/**
	 * The boundaries of this shape.
	 */
	protected Rectangle bounds;
	
	/**
	 * The color of this shape's filling.
	 */
	protected Color fillColor;
	
	/**
	 * The color of this shape's border.
	 * Not used.
	 */
	protected Color borderColor;
	
	/**
	 * The indicator if this shape is selected.
	 */
	protected boolean isSelected;
	
	//protected BasicStroke rolloverStroke; protected Color rolloverColor; protected 
	//protected float scalingFactor;
	
	private BasicStroke selectionStroke;
	
	
	/**
	 * Constructs a new <code>ShapePlus</code> with filling and border colors set to <code>color</code>.
	 * @param color filling and border color
	 */
	public ShapePlus(Color color)
	{	
		this.fillColor = new Color(color.getRGB());
		this.borderColor = new Color(color.getRGB());
		//this.rolloverColor = new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue());
		
		this.centerPoint = new Point(0, 0);
		this.bounds = new Rectangle(0, 0, 0, 0);
		
		//float dash[] = {10f};
		//this.rolloverStroke = new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 1f, dash, 0f);
		
		//this.isRollover = false;
		this.isSelected = false;
		
		//this.scalingFactor = 1f;
		//this.scalingFactor = 1.2f;
		
		float dash[] = {6f};
		this.selectionStroke = new BasicStroke(1f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 1f, dash, 0f);
	}
	
	/**
	 * Constructs a new <code>ShapePlus</code> with data from the <code>dataString</code>.
	 * dataString should consist of shape type, middle point X coordinate, middle point Y coordinate,
	 * border color value and filling RGB color values separated by one space.
	 * @param dataString String containing shape's data.
	 */
	public ShapePlus(String dataString)
	{
		String tab[] = dataString.split(" ");
		
		this.borderColor = new Color(Integer.parseInt(tab[3]));
		this.fillColor = new Color(Integer.parseInt(tab[4]));
		
		this.bounds = new Rectangle(0, 0, 0, 0);
		
		this.centerPoint = new Point(Integer.parseInt(tab[1]), Integer.parseInt(tab[2]));
		
		this.isSelected = false;
		float dash[] = {6f};
		this.selectionStroke = new BasicStroke(1f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 1f, dash, 0f);
	}
	
	//abstract public boolean contains(int x, int y);
	
	/**
	 * Tests if a specified Point is inside the boundary of the ShapePlus.
	 * @param p the specified <code>Point</code> to be tested.
	 * @return <code>true</code> if the specified <code>Point</code> is inside the boundary of the <code>ShapePlus</code>; <code>false</code> otherwise.
	 */
	abstract public boolean contains(Point p);
	
	/**
	 * Adds a specified <code>Point</code> to build a custom shape based on <code>ShapePlus</code>.
	 * @param p specified Point to be added
	 * @param save <code>true</code> if specified <code>Point</code> should be saved; <code>false</code> if it is temporary.
	 */
	abstract public void addPoint(Point p, boolean save);
	
	/**
	 * The same as <code>addPoint(p, false)</code>.
	 * @param p specified <code>Point</code> to be added.
	 */
	public void addPoint(Point p) { addPoint(p, false); }
	
	/**
	 * Draws the shape.
	 * @param g2d the <code>Graphics2D</code> context in which to draw
	 */
	abstract public void draw(Graphics2D g2d);
	//abstract public void translate(Point beg, Point end);
	//abstract public void translate(Distance vect);
	
	/**
	 * Translates the shape horizontally (by <code>dx</code>) and vertically (by <code>dy</code>).
	 * @param dx horizontal translation
	 * @param dy vertical translation
	 */
	abstract public void translate(int dx, int dy);
	
	/**
	 * Scales the shape equally horizontally and vertically
	 * @param s scaling factor; s &gt; 0
	 */
	abstract public void scale(float s);
	//abstract public void scale(int n);
	
	/**
	 * Sets isSelected field to false.
	 * @see #isSelected
	 */
	public void deactive() { isSelected = false; }
	
	/**
	 * Sets <code>isSelected</code> field to <code>true</code>.
	 * @see #isSelected
	 */
	public void active() { isSelected = true; }
	
	/**
	 * Returns the integer <code>Rectangle</code> that completely encloses this <code>ShapePlus</code>.
	 * @return integer <code>Rectangle</code>.
	 */
	public Rectangle getBounds() { return bounds; }
	
	/**
	 * Sets filling color for <code>col</code>.
	 * @param col filling color
	 */
	public void setColor(Color col) { this.fillColor = col; }
	
	
	/**
	 * Draws the selection rectangle.
	 * @param g2d the <code>Graphics2D</code> context in which to draw
	 */
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
	
	
	/**
	 * Writes this <code>ShapePlus</code> data to <code>String</code>.
	 * Returned <code>String</code> contains of: middle point X coordinate, middle point Y coordinate,
	 * border RGB color value, filling RGB color value separated by one space.
	 * @return String with <code>ShapePlus</code> data.
	 */
	public String writeToString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(centerPoint.x);
		sb.append(' ');
		sb.append(centerPoint.y);
		sb.append(' ');
		sb.append(borderColor.getRGB());
		sb.append(' ');
		sb.append(fillColor.getRGB());
		sb.append(' ');
		
		return sb.toString();
	}
}