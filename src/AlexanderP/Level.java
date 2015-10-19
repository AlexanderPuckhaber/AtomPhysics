package AlexanderP;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Level {
	
	public ArrayList<Rectangle> hardPointList = new ArrayList<Rectangle>();
	public int number;
	public boolean passed;
	public double budget;
	public Rectangle road;
	
	public Level()
	{
		number = 0;
		passed = false;
		budget = 0;
	}
	
	public void pass()
	{
		passed = true;
	}
	
	public void setNumber(int newNumber)
	{
		number = newNumber;
	}
	
	public void setBudget(double newBudget)
	{
		budget = newBudget;
	}
	
	public void setRoad(Rectangle newR)
	{
		road = newR;
	}
	
	public void addHardPoint(Rectangle newRect)
	{
		hardPointList.add(newRect);
	}
	
	public void draw(Graphics2D g)
	{
		for (int i = 0; i < hardPointList.size(); i++)
		{
			g.setColor(new Color(150, 150, 150));
			g.fillRect(hardPointList.get(i).x, hardPointList.get(i).y, hardPointList.get(i).width, hardPointList.get(i).height);
			g.setColor(Color.black);
			g.drawRect(hardPointList.get(i).x, hardPointList.get(i).y, hardPointList.get(i).width, hardPointList.get(i).height);
			g.drawLine(hardPointList.get(i).x, hardPointList.get(i).y, hardPointList.get(i).x+hardPointList.get(i).width, hardPointList.get(i).y+hardPointList.get(i).height);
			g.drawLine(hardPointList.get(i).x+hardPointList.get(i).width, hardPointList.get(i).y, hardPointList.get(i).x, hardPointList.get(i).y+hardPointList.get(i).height);
		}
		g.drawRect(road.x, road.y, road.width, road.height);
		g.drawLine(road.x, road.y, road.x+road.width, road.y+road.height);
		g.drawLine(road.x+road.width, road.y, road.x, road.y+road.height);
	}
	
	public void setRoad(ArrayList<Atom> atomList)
	{
		for (int i = 0; i < atomList.size(); i++)
		{
			Point2D.Double p = new Point2D.Double(atomList.get(i).getPoint().x, atomList.get(i).getPoint().y);
			if (road.contains(p) && !atomList.get(i).isActive())
			{
				atomList.get(i).setRoad();
			}
		}
	}
	
	public void reset(ArrayList<Atom> atomList, ArrayList<Bond> bondList)
	{
		//pauses
		run.pause = true;
		run.overBudgetMessageTime = 0;
		//clears atoms
		atomList.clear();
		bondList.clear();
		//resets car
		run.carLocation = -1;
		//updates current cost
		run.currentCost = 0;
		for (int i = 0; i < atomList.size(); i++)
		{
			run.currentCost += atomList.get(i).m.getCost();
		}
	}
	
	public ArrayList<Rectangle> getHardPoints()
	{
		return hardPointList;
	}
	
	public Rectangle getRoad()
	{
		return road;
	}
	
	public double getBudget()
	{
		return budget;
	}
	
	
}
