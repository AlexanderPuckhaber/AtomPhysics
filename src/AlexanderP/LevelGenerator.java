package AlexanderP;

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
	}
	
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
		
		//#5
		nL = new Level();
		nL.addHardPoint(new Rectangle(0, 150, 50, 200));
		nL.addHardPoint(new Rectangle(550, 150, 50, 200));
		
		nL.setRoad(new Rectangle(0, 100, 600, 50));
		nL.setBudget(3000);
		
		levelList.add(nL);
		
		//#6
		nL = new Level();
		nL.addHardPoint(new Rectangle(0, 150, 50, 400));
		nL.addHardPoint(new Rectangle(200, 100, 50, 50));
		nL.addHardPoint(new Rectangle(400, 100, 50, 50));
		nL.addHardPoint(new Rectangle(550, 150, 50, 400));
		
		nL.setRoad(new Rectangle(0, 350, 600, 50));
		nL.setBudget(1800);
		
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

	            out.write(Integer.toString(highScore));
	            out.close();
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        }
		}
	}
	
}
