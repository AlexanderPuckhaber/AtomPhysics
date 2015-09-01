package AlexanderP;

import java.awt.Rectangle;
import java.util.ArrayList;

public class LevelGenerator {
	
	//this is here so it doesn't clutter the run method
	
	public static void generateLevels(ArrayList<Level> levelList)
	{
		//#0
		Level nL = new Level();
		nL.addHardPoint(new Rectangle(300, 200, 100, 100));
		
		nL.setRoad(new Rectangle(300, 350, 100, 100));
		nL.setBudget(9001);
		
		levelList.add(nL);
		
		//#1
		nL = new Level();
		nL.addHardPoint(new Rectangle(0, 150, 50, 200));
		nL.addHardPoint(new Rectangle(550, 150, 50, 200));
		
		nL.setRoad(new Rectangle(0, 150, 600, 50));
		nL.setBudget(1000);
		
		levelList.add(nL);
		
		//#2
		nL = new Level();
		nL.addHardPoint(new Rectangle(0, 150, 50, 200));
		nL.addHardPoint(new Rectangle(300, 300, 50, 50));
		nL.addHardPoint(new Rectangle(550, 150, 50, 200));
		
		nL.setRoad(new Rectangle(0, 150, 600, 50));
		nL.setBudget(700);
		
		levelList.add(nL);
		
		//#3
		nL = new Level();
		nL.addHardPoint(new Rectangle(0, 150, 50, 400));
		nL.addHardPoint(new Rectangle(200, 100, 50, 50));
		nL.addHardPoint(new Rectangle(400, 100, 50, 50));
		nL.addHardPoint(new Rectangle(550, 150, 50, 400));
		
		nL.setRoad(new Rectangle(0, 150, 600, 50));
		nL.setBudget(600);
		
		levelList.add(nL);
		
		//#4
		nL = new Level();
		nL.addHardPoint(new Rectangle(0, 150, 50, 400));
		nL.addHardPoint(new Rectangle(200, 50, 50, 50));
		nL.addHardPoint(new Rectangle(400, 50, 50, 50));
		nL.addHardPoint(new Rectangle(550, 350, 50, 400));
		
		nL.setRoad(new Rectangle(0, 150, 600, 50));
		nL.setBudget(800);
		
		levelList.add(nL);
		
		//#PLACEHOLDER
				nL = new Level();
				nL.addHardPoint(new Rectangle(0, 350, 50, 400));
				nL.addHardPoint(new Rectangle(200, 50, 50, 50));
				nL.addHardPoint(new Rectangle(400, 50, 50, 50));
				nL.addHardPoint(new Rectangle(550, 350, 50, 400));
				
				nL.setRoad(new Rectangle(0, 150, 600, 50));
				nL.setBudget(400);
				
				levelList.add(nL);
		
	}
	
}
