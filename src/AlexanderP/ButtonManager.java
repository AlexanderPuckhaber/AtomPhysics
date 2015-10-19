package AlexanderP;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public class ButtonManager 
{

	
	public ButtonManager()
	{
		
		
	}
	
	public ArrayList<Button> createButtons()
	{
		
		ArrayList<Button> buttonList = new ArrayList<Button>();
		
		Button goopButton = new Button(20, 900, 200, 100, "Goop ($10)", new Color(100, 100, 100));
		Button concreteButton = new Button(220, 900, 200, 100, "Concrete ($20)", new Color(100, 100, 100));
		Button steelButton = new Button(420, 900, 200, 100, "Steel ($50)", new Color(100, 100, 100));
		Button liquidButton = new Button(620, 900, 200, 100, "Liquid ($2)", new Color(100, 100, 100));
		
		Button pauseButton = new Button(830, 900, 200, 100, "Pause/Unpause", new Color(100, 100, 100));
		Button clearButton = new Button(1040, 900, 160, 100, "Clear Atoms", new Color(100, 100, 100));
		
		buttonList.add(goopButton);
		buttonList.add(concreteButton);
		buttonList.add(steelButton);
		buttonList.add(liquidButton);
		buttonList.add(pauseButton);
		buttonList.add(clearButton);
		
		return buttonList;
	}
	
	public void draw(Graphics2D g, ArrayList<Button> buttonList)
	{
		for (int i = 0; i < buttonList.size(); i++)
		{
			buttonList.get(i).draw(g);
		}
	}
	
	public void checkButtons(ArrayList<Button> buttonList, Point p)
	{
		for (int i = 0; i < buttonList.size(); i++)
		{
			buttonList.get(i).checkPressed(p);
		}
	}
	
	public void checkButtonHover(ArrayList<Button> buttonList, Point p)
	{
		for (int i = 0; i < buttonList.size(); i++)
		{
			buttonList.get(i).checkHover(p);
		}
	}
	
	public boolean findButtonPressed(ArrayList<Button> buttonList, String desc)
	{
		int i = 0;
		boolean found = false;
		while (i < buttonList.size() && !found) //can only press 1 button
		{
			if (buttonList.get(i).getDescription().equalsIgnoreCase(desc))
			{
				found = true;
				
			}
				
			i++;
		}
		
		boolean pressed = buttonList.get(i-1).getPressed();
		if (pressed)
			System.out.println("Button "+i+" was pressed");
			
		return pressed;
	}
	
}
