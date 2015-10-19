package AlexanderP;

import java.awt.Color;
import java.awt.Font;
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
	double highlightTime;
	
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
		
		highlightTime = 0;
		
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
			highlightTime += run.timeStep*3;
			int mult = 50;
			
			int tmpR = oldC.getRed();
			int tmpG = oldC.getGreen();
			int tmpB = oldC.getBlue();
			
			
			tmpR += mult*Math.sin(highlightTime);
			if (tmpR > 255)
				tmpR = 255;
			else if (tmpR < 0)
				tmpR = 0;
			
			tmpG += mult*Math.cos(highlightTime*1.123);
			if (tmpG > 255)
				tmpG = 255;
			else if (tmpG < 0)
				tmpG = 0;
			
			tmpB += mult*Math.cos(highlightTime*.87+3);
			if (tmpB > 255)
				tmpB = 255;
			else if (tmpB < 0)
				tmpB = 0;
			
			c = new Color(tmpR, tmpG, tmpB);
		}
		else
		{
			c = oldC;
			highlightTime = 0;
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
		g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
		g.drawString(s, (int)x, (int)y+10+(int)(h/2));
	}
	

}
