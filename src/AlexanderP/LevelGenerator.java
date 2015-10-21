package AlexanderP;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class LevelGenerator {
	
	public int highScore;
	public LevelGenerator()
	{
		highScore = loadHighScore();
		System.out.println(highScore);
	}
	
	//this is here so it doesn't clutter the run method
	
	public static void generateLevels(ArrayList<Level> levelList)
	{
		//#0
		Level nL = new Level();
		nL.addHardPoint(new Rectangle(600, 400, 200, 200));
		
		nL.addRoad(new Rectangle(500, 700, 200, 200));
		nL.setTarget(new Point(600, 700));
		nL.setBudget(9001);
		
		levelList.add(nL);
		
		//#1
		nL = new Level();
		nL.addHardPoint(new Rectangle(0, 300, 100, 400));
		nL.addHardPoint(new Rectangle(1100, 300, 100, 400));
		
		nL.addRoad(new Rectangle(0, 300, 1200, 100));
		nL.setTarget(new Point(1200, 300));
		nL.setBudget(1200);
		
		levelList.add(nL);
		
		//#2
		nL = new Level();
		nL.addHardPoint(new Rectangle(0, 300, 100, 400));
		nL.addHardPoint(new Rectangle(400, 500, 100, 100));
		nL.addHardPoint(new Rectangle(800, 500, 100, 100));
		nL.addHardPoint(new Rectangle(1100, 300, 100, 400));
		
		nL.addRoad(new Rectangle(0, 300, 1200, 100));
		nL.setTarget(new Point(1200, 300));
		nL.setBudget(700);
		
		levelList.add(nL);
		
		//#3
		nL = new Level();
		nL.addHardPoint(new Rectangle(0, 300, 100, 800));
		nL.addHardPoint(new Rectangle(400, 200, 100, 100));
		nL.addHardPoint(new Rectangle(800, 200, 100, 100));
		nL.addHardPoint(new Rectangle(1100, 300, 100, 800));
		
		nL.addRoad(new Rectangle(0, 300, 1200, 100));
		nL.setTarget(new Point(1200, 300));
		nL.setBudget(600);
		
		levelList.add(nL);
		
		//#4
		nL = new Level();
		nL.addHardPoint(new Rectangle(0, 300, 100, 800));
		nL.addHardPoint(new Rectangle(400, 100, 100, 100));
		nL.addHardPoint(new Rectangle(800, 100, 100, 100));
		nL.addHardPoint(new Rectangle(1100, 700, 100, 800));
		
		nL.addRoad(new Rectangle(0, 300, 1200, 100));
		nL.setTarget(new Point(1200, 300));
		nL.setBudget(1000);
		
		levelList.add(nL);
		
		//#5
		nL = new Level();
		nL.addHardPoint(new Rectangle(0, 300, 100, 400));
		nL.addHardPoint(new Rectangle(1100, 300, 100, 400));
		
		nL.addRoad(new Rectangle(0, 200, 1200, 100));
		nL.setTarget(new Point(1200, 300));
		nL.setBudget(3000);
		
		levelList.add(nL);
		
		//#6
		nL = new Level();
		nL.addHardPoint(new Rectangle(0, 300, 100, 800));
		nL.addHardPoint(new Rectangle(400, 200, 100, 100));
		nL.addHardPoint(new Rectangle(800, 200, 100, 100));
		nL.addHardPoint(new Rectangle(1100, 300, 100, 800));
		
		nL.addRoad(new Rectangle(0, 300, 400, 100));
		nL.addRoad(new Rectangle(400, 350, 400, 100));
		nL.addRoad(new Rectangle(700, 300, 500, 100));
		nL.setTarget(new Point(1200, 300));
		nL.setBudget(1800);
		
		levelList.add(nL);
		
		//7
		nL = new Level();
		nL.addHardPoint(new Rectangle(600, 100, 200, 200));
		
		nL.addRoad(new Rectangle(100, 600, 300, 300));
		nL.addRoad(new Rectangle(0, 300, 300, 300));
		nL.setTarget(new Point(800, 900));
		nL.setBudget(2000);
		
		levelList.add(nL);
		
		//#PLACEHOLDER U WIN
				nL = new Level();
				nL.addHardPoint(new Rectangle(50, 200, 100, 400));
				nL.addHardPoint(new Rectangle(150, 550, 200, 50));
				nL.addHardPoint(new Rectangle(350, 200, 100, 400));
				
				nL.addHardPoint(new Rectangle(500, 200, 50, 400));
				nL.addHardPoint(new Rectangle(600, 400, 50, 200));
				nL.addHardPoint(new Rectangle(700, 200, 50, 400));
				nL.addHardPoint(new Rectangle(500, 500, 200, 100));
				
				nL.addHardPoint(new Rectangle(800, 200, 100, 100));
				nL.addHardPoint(new Rectangle(800, 400, 100, 200));
				
				nL.addHardPoint(new Rectangle(950, 200, 50, 400));
				nL.addHardPoint(new Rectangle(950, 200, 100, 50));
				nL.addHardPoint(new Rectangle(1050, 200, 50, 400));
				
				nL.addRoad(new Rectangle(0, 900, 1200, 50));
				nL.setTarget(new Point(1200, 300));
				nL.setBudget(400);
				
				levelList.add(nL);
		
	}
	
	public int loadHighScore()
	{
		try
        {
			File f = new File("highScore.txt");
            Scanner inFile = new Scanner(f);
            while (inFile.hasNext())
            {
                return Integer.parseInt(inFile.next());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
		return 0;
		
	}
	
	public void writeHighScore(int currentLevel)
	{
		if (currentLevel > highScore)
		{
			try
	        {
	            FileWriter fStream = new FileWriter("highScore.txt");
	            BufferedWriter out = new BufferedWriter(fStream);

	            out.write(Integer.toString(5));
	            out.close();
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        }
		}
	}
	
}
