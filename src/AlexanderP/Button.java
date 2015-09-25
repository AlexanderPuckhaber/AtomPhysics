package AlexanderP;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;

public class Button 
{
	double x, y, w, h;
	Rectangle2D.Double r;
	boolean pressed, visible;
	String s;
	Color c, oldC;
	
	public Button(double nX, double nY, double nW, double nH, String nS, Color nC)
	{
		x = nX;
		y = nY;
		w = nW;
		h = nH;
		
		r = new Rectangle2D.Double(x, y, w, h);
		s = nS;
		c = nC;
		oldC = c;
		
		pressed = false;
	}
	
	public void checkPressed(Point p)
	{
		pressed = false;
		if (r.contains(p))
		{
			pressed = true;
		}
	}
	
	public void checkHover(Point p)
	{
		if (r.contains(p))
		{
			
			int tmpR = c.getRed();
			int tmpG = c.getGreen();
			int tmpB = c.getBlue();
			
			
			tmpR += 20;
			if (tmpR > 255)
				tmpR = 255;
			
			tmpG += 20;
			if (tmpG > 255)
				tmpG = 255;
			
			c = new Color(tmpR, tmpG, tmpB);
		}
		else
		{
			c = oldC;
		}
	}
	
	public String getDescription()
	{
		return s;
	}
	
	public boolean getPressed()
	{
		return pressed;
	}
	
	public void draw(Graphics2D g)
	{
		g.setColor(c);
		g.fill3DRect((int)x, (int)y, (int)w, (int)h, true);
		g.setColor(Color.BLACK);
		g.drawString(s, (int)x, (int)y+10+(int)(h/2));
	}
	

}
