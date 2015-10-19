package AlexanderP;

import java.util.*;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.lang.Math.*;

public class Collider {
	
	
	public static void makeBonds(ArrayList<Atom> atomList, ArrayList<Bond> bondList, int aLoc, int bLoc)
	{
		Atom a = atomList.get(aLoc);
		Atom b = atomList.get(bLoc);
		
		if (aLoc != bLoc)
		{
			//rough distance
			if (getRoughDist(a, b) < 100)
			{
				Double dist = getDist(a, b);
				if (dist < 100)
				{
					boolean skip = false;
					//System.out.println(atomList.get(target).connectedTo.size());
					for (int i = 0; i < b.connectedTo.size(); i++)
					{
						if (b.connectedTo.get(i) == aLoc)
						{
							skip = true;
						}
					}
					if (!skip)
					{
						Bond newBond = new Bond();
						newBond.setTargets(aLoc, bLoc);
						
						newBond.getBondAvg(a, b);
						
						if (run.stick && dist <= newBond.getMaxDist())
						{
							newBond.setStick(true);
						}
						else
						{
							newBond.setStick(false);
						}
						
						bondList.add(newBond);
						b.connectedTo.add(aLoc);
						a.connectedTo.add(bLoc);
					}
				}
	
			}
		}
		
	}
	
	//not used
	public static void deleteExtraBonds(ArrayList<Bond> bondList, ArrayList<Integer> done)
	{
		
		for (int i = 0; i < bondList.size(); i++)
		{
			
			done.clear();
			
			for (int l = 0; l < bondList.size(); l++)
			{
				//if the first target is the same
				if (bondList.get(l).getTargets()[0] == i)
				{
					boolean skip = false;
					for (int y = 0; y < done.size(); y++)
					{
						//if the second target was already done, kill it
						if (bondList.get(l).getTargets()[1] == done.get(y))
						{
							bondList.remove(l);
							skip = true;
						}
					}
					//if the second target was not already done, add it to the done list
					if (!skip)
					{
						done.add(bondList.get(l).getTargets()[1]);
					}
				}
			}
		}	
	}
	
	public static void deleteFarBonds(ArrayList<Bond> bondList)
	{
		for (int k = 0; k < bondList.size(); k++)
		{
			if (bondList.get(k).getLength() >= 100)
			{
				bondList.remove(k);
			}	
		}
	}
	

	
	public static void doBorder(Rectangle r, ArrayList<Atom> atomList)
	{
		
		int top = r.y;
		int bottom = top+r.height;
		int left = r.x;
		int right = left + r.width;
		double amt = 0.4;
		
		for (int i = 0; i < atomList.size(); i++)
		{
			if (atomList.get(i).getPoint().y > bottom-atomList.get(i).m.getMinDist())
			{
				atomList.get(i).setPosition(atomList.get(i).getPoint().x, bottom-atomList.get(i).m.getMinDist());
				atomList.get(i).setVelocity(atomList.get(i).getVelocity()[0]*amt, atomList.get(i).getVelocity()[1]*-amt);
			}
			else if (atomList.get(i).getPoint().y < top+atomList.get(i).m.getMinDist())
			{
				atomList.get(i).setPosition(atomList.get(i).getPoint().x, top+atomList.get(i).m.getMinDist());
				atomList.get(i).setVelocity(atomList.get(i).getVelocity()[0]*amt, atomList.get(i).getVelocity()[1]*-amt);
			}
			
			if (atomList.get(i).getPoint().x < left+atomList.get(i).m.getMinDist())
			{
				
				atomList.get(i).setPosition(left+atomList.get(i).m.getMinDist(), atomList.get(i).getPoint().y);
				atomList.get(i).setVelocity(atomList.get(i).getVelocity()[0]*-amt, atomList.get(i).getVelocity()[1]*amt);
			}
			else if (atomList.get(i).getPoint().x > right-atomList.get(i).m.getMinDist())
			{
				atomList.get(i).setPosition(right-atomList.get(i).m.getMinDist(), atomList.get(i).getPoint().y);
				atomList.get(i).setVelocity(atomList.get(i).getVelocity()[0]*-amt, atomList.get(i).getVelocity()[1]*amt);
			}
		}
	}
	
	public static void stickToHardPoints(Atom a, Level currentLevel)
	{
		Point2D.Double p = new Point2D.Double(a.getPoint().x, a.getPoint().y);
		
		for (int i = 0; i < currentLevel.getHardPoints().size(); i++)
		{
			if (currentLevel.getHardPoints().get(i).contains(p) && !a.isActive())
			{
				a.setInHardPoint();
				a.setVelocity(0, 0);
			}
		}
	}
	
	public static void removeOverlappers(ArrayList<Atom> atomList, double minSpace)
	{
		int size = atomList.size();
		
		for (int i = 0; i < size; i++)
		{
			for (int p = 0; p < size; p++)
			{

				double dist = 1000;
				if (i!= p && i < atomList.size() && p < atomList.size())
				{
					dist = Collider.getDist(atomList.get(i), atomList.get(p));
				}
				if (dist < minSpace)
				{
					atomList.remove(i);
					p = size+1;
				}
		   
			}
		}
	}
	
	public static void removeCars(ArrayList<Atom> atomList, Material m)
	{
		for (int i = 0; i < atomList.size(); i++)
		{
			if (atomList.get(i).getMaterial() == m)
			{
				atomList.remove(i);
			}
		}
	}
	
	
	public static double getDist(Atom a, Atom b)
	{
		double xDist = a.getPoint().x-b.getPoint().x;
		double yDist = a.getPoint().y-b.getPoint().y;
		
		double dist = Math.sqrt((xDist * xDist) + (yDist * yDist));
		return dist;
	}
	
	public static double getRoughDist(Atom a, Atom b)
	{
		double xDist = a.getPoint().x-b.getPoint().x;
		double yDist = a.getPoint().y-b.getPoint().y;
		
		return xDist+yDist;
	}
	
	
}
