package AlexanderP;

import java.util.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
public class run extends JFrame implements Runnable, MouseListener, KeyListener, MouseMotionListener
{
	ArrayList<Atom> atomList = new ArrayList<Atom>();
	ArrayList<Bond> bondList = new ArrayList<Bond>();
	ArrayList<Button> buttonList = new ArrayList<Button>();
	LevelGenerator lGen = new LevelGenerator();
	//ArrayList<Integer> alreadyDone = new ArrayList<Integer>();
	public static boolean stick = true;
	
	boolean doRectangle = false;
	
	Container con = getContentPane();
	Renderer drawer = new Renderer();
	Thread t = new Thread(this);
	ArrayList<Point2D.Double> pList = new ArrayList<Point2D.Double>();
	ArrayList<Integer> aList = new ArrayList<Integer>();
	QuadTree root = new QuadTree(-100, -100, 1800, 1800);
	Rectangle worldBorder = new Rectangle();
	//QuadTree root = new QuadTree(0, 0, 800, 800);
	int minQuadSize = 100;
	
	double targetTimeStep = 0.01;
	public double elapsedTime = 0;
	public static double timeStep = 0.01;
	//time
	public double time = System.nanoTime();
	public double lastTime = time;
	double delay = 0;
	int tick = 0;
	double tm = 0;
	double paintTime = 0;
	double repaintTime = 17;
	static double scale = 0.8;
	
	double maxLengthChange = 0;
	double maxSpeed = 0;
	
	//this stuff is for setting new rectangles
	Boolean place = false;
	Point startPoint = new Point();
	Point endPoint = new Point();
	Point rPoint = new Point();
	int length = 0;
	int height = 0;
	
	double dX = 0;
	double dY = 0;
	int mouseX = 0;
	int mouseY = 0;
	boolean pressed = false;
	
	Material goop = new Material();
	Material liquid = new Material();
	Material concrete = new Material();
	Material steel = new Material();
	Material car = new Material();
	
	Material selectedMaterial = goop;
	
	public static boolean pause = true;
	public boolean clear = false;
	static int overBudgetMessageTime = 1000;
	int maxOverBudgetMessageTime = 1000;
	int clearMessageTime = 1000;
	int maxClearMessageTime = 1000;
	
	ButtonManager bManager;
	
	
	//levels
	ArrayList<Level> levelList = new ArrayList<Level>();
	static double currentCost = 0;
	int currentLevelNumber = 0;
	Level currentLevel;
	
	//"car"
	static int carLocation = -1;
	
	//images
	Image background;
	
	public run ()
	{
		con.setLayout(new FlowLayout());
		t.start();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addMouseListener(this);
		addKeyListener(this);
		worldBorder.setBounds(0, 0, 1200, 1000);
		
		background = Toolkit.getDefaultToolkit().getImage(getClass().getResource("background.png"));
		background = background.getScaledInstance((int)(1200*scale), (int)(1000*scale), 1);
		
		//makes levels
		lGen.generateLevels(levelList);
		
		//initializes buttons
		bManager = new ButtonManager();
		buttonList = bManager.createButtons();
		
		
		currentLevel = levelList.get(currentLevelNumber);
		System.out.println(currentLevel.getBudget());
		
		//Initializes materials
		goop.setMass(1);
		goop.setMinDist(26);
		goop.setMaxDist(80);
		goop.setDampener(10);
		goop.setTensileStrength(0.2);
		goop.setCompressiveStrength(1);
		goop.setEquilibrium(60);
		goop.setSpacing(60);
		goop.setCost(10);
		goop.setColor(new Color(30, 255, 50));
		
		concrete.setMass(1.3);
		concrete.setMinDist(26);
		concrete.setMaxDist(80);
		concrete.setDampener(13);
		concrete.setTensileStrength(0.1);
		concrete.setCompressiveStrength(1);
		concrete.setEquilibrium(60);
		concrete.setSpacing(50);
		concrete.setCost(20);
		concrete.setColor(new Color(150, 150, 150));
		
		steel.setMass(1);
		steel.setMinDist(26);
		steel.setMaxDist(80);
		steel.setDampener(13);
		steel.setTensileStrength(0.5);
		steel.setCompressiveStrength(0.5);
		steel.setEquilibrium(60);
		steel.setSpacing(68);
		steel.setCost(50);
		steel.setColor(new Color(50, 50, 50));
		
		liquid.setMass(1);
		liquid.setMinDist(26);
		liquid.setMaxDist(80);
		liquid.setDampener(13);
		liquid.setTensileStrength(0);
		liquid.setCompressiveStrength(0);
		liquid.setEquilibrium(60);
		liquid.setSpacing(50);
		liquid.setCost(2);
		liquid.setColor(new Color(50, 50, 255));
		
		car.setMass(7);
		car.setMinDist(36);
		car.setMaxDist(100);
		car.setDampener(13);
		car.setTensileStrength(1);
		car.setCompressiveStrength(1);
		car.setEquilibrium(60);
		car.setCost(0);
		car.setColor(new Color(255, 0, 0));
	}
	
	public void run()
	{
		try{
			
			 
			root.setIndex(-1);
			
			
			time = System.nanoTime()/1000000;
			time /= 1000;
			
			
			while(true)
			{
				//updates level
				if (currentLevelNumber < levelList.size()-1)
				{
					currentLevel = levelList.get(currentLevelNumber);
				}
				
				//updates time
				lastTime = time;
				time = System.nanoTime()/1000/1000/1000;
				elapsedTime = (time-lastTime);
				//timeStep = elapsedTime;
				
				
				if (elapsedTime > targetTimeStep)
				{
					delay = 0;
				}
				else 
				{
					delay = targetTimeStep;
				}
				
				//System.out.println(delay);
				
				t.sleep((int)(1000*Math.abs(delay)));
				paintTime += 1000*timeStep;
				

				bManager.checkButtons(buttonList, new Point(mouseX, mouseY));
				
				
				
				//button actions
				if (pressed)
				{
				if (bManager.findButtonPressed(buttonList, "Goop ($10)"))
					selectedMaterial = goop;
				if (bManager.findButtonPressed(buttonList, "Concrete ($20)"))
					selectedMaterial = concrete;
				if (bManager.findButtonPressed(buttonList, "Steel ($50)"))
					selectedMaterial = steel;
				if (bManager.findButtonPressed(buttonList, "Liquid ($2)"))
					selectedMaterial = liquid;
				if (bManager.findButtonPressed(buttonList, "Pause/Unpause"))
					pause = !pause;
				if (bManager.findButtonPressed(buttonList, "Clear Atoms"))
					clear = true;
				}
				
				
				
				
				//places atoms with mouse
				if (place && pause)
				{
					dX = startPoint.x-endPoint.x;
					dY = startPoint.y-endPoint.y;
					
					System.out.println("Start: "+startPoint.x+" "+startPoint.y);
					System.out.println("End: "+endPoint.x+" "+endPoint.y);
					
					if (dX < 0)
					{
						rPoint.setLocation(startPoint.x, rPoint.y);
					}
					else
					{
						rPoint.setLocation(endPoint.x, rPoint.y);
					}
					
					if (dY < 0)
					{
						rPoint.setLocation(rPoint.x, startPoint.y);
					}
					else
					{
						rPoint.setLocation(rPoint.x, endPoint.y);
					}
					
					if (dX == 0 && dY == 0 && pressed && mouseY < 900)
					{
						//place one atom
						setter.init(atomList, 1, selectedMaterial);
						atomList.get(atomList.size()-1).setPosition(rPoint.x, rPoint.y);
					}
					
					if (doRectangle && dX != 0 && dY != 0)
					{
					
						System.out.println(rPoint.getX()+" "+ rPoint.getY());
						//System.out.println(endPoint.getX());
						
						length = (int)(Math.abs(dX)/30);
						length *= 30;
						
						height = (int)((Math.abs(dY)/(15*Math.sqrt(3))));
						height *= (15*Math.sqrt(3));
						
						int num = 0;
						//int num = (int)((length/30)*(height/(15*Math.sqrt(3))));
						
						int row = 0;
						int col = 0;
						
						//tests how many can fit in the rectangle
						while (row < height/(15*Math.sqrt(3)))
						{
							if (col > length/30)
							{
								col = 0;
								row ++;
							}
							col += 1;
							num++;
						}
						//removes the extra one
						num -=1;
						
						int start = atomList.size();
						setter.init(atomList, num, selectedMaterial);
						
						double size = selectedMaterial.getSpacing();
						
						for (int i = 0; i < start+num; i++)
						{
							setter.setRect(atomList, start, start+num, rPoint.getX(), rPoint.getY(), length/size, size);
						}
					
					}
					else if (dX != 0 && dY != 0)
					{
						
						double dist = Math.sqrt(Math.pow(startPoint.x-endPoint.x, 2)+Math.pow(startPoint.y-endPoint.y, 2));
						
						
						
						double size = selectedMaterial.getSpacing();
						int num = (int)Math.floor(dist/size);
						int start = atomList.size();
						System.out.println(start + " total: " + (start+num) + " startx: " + startPoint.x + " starty: " + startPoint.y + 
								" endx: " +endPoint.x+" endy: "+endPoint.y+" dist: "+dist+" size: "+size);
						setter.init(atomList, num, selectedMaterial);
						
						setter.setLine(atomList, start, start+num, startPoint.x, startPoint.y, endPoint.x, endPoint.y, dist, size);
					}
					
				    //removes ones that are too close together
					Collider.removeOverlappers(atomList, 30);
					
					//sets in road
					currentLevel.setRoad(atomList);
					
					//adds "car"
					Collider.removeCars(atomList, car);
					setter.init(atomList, 1, car);
					carLocation = atomList.size()-1;
					//first road in road list
					atomList.get(carLocation).setPosition(currentLevel.roadList.get(0).x+100, currentLevel.roadList.get(0).y-100);
					atomList.get(carLocation).setVelocity(100*timeStep, 100*timeStep);
					atomList.get(carLocation).setActive();
					
					place = false;
					
					//updates current cost
					currentCost = 0;
					for (int i = 0; i < atomList.size(); i++)
					{
						currentCost += atomList.get(i).m.getCost();
					}
					
					/*********
					 * UPDATES QUADTREES AND MAKES NEW BONDS
					 */
					stick = true;
					
					root.addAtoms(atomList, aList, 10, minQuadSize);
					root.collideAtoms(root, atomList, bondList);
					root.addAtoms(atomList, aList, 10, minQuadSize);
					root.collideAtoms(root, atomList, bondList);
					
					stick = false;
					//System.out.println(atomList.size());
				}
				
				//sets pressed to false
				pressed = false;
				
				/********
				 * UPDATES LENGTHS
				 */
				for (Bond b: bondList)
					Force.updateLength(b);
				
				/*********
				 * UPDATES QUADTREES
				 */
				root.addAtoms(atomList, aList, 10, minQuadSize);
				

				
				/*********
				 * MAKES NEW BONDS AND COLLIDES WITH GROUND
				 */
				if (tick %10 == 0 && !pause)
				{
					root.collideAtoms(root, atomList, bondList);
				}
				
				
				/******
				 * UPDATES MAX SPEED
				 */
				if (!pause)
				{
					root.updateMaxSpeed(atomList);
				}
				

				
				/*********
				 * MOVES ATOMS AND UPDATES CONNECTIONS
				 */
				if (!pause)
				{
					for (int l = 0; l < atomList.size(); l++)
					{	
						
						atomList.get(l).move(timeStep);
						
						//almost forgot this...
						atomList.get(l).updateConnections(bondList, l);
					}
					//Collider.ground(500, atomList);
					Collider.doBorder(worldBorder, atomList);
				}
				
				if (pause)
				{
					for (int i = 0; i < atomList.size(); i++)
					{
						Collider.stickToHardPoints(atomList.get(i), currentLevel);
					}
				
				}

				
				/*********
				 * DELETES EXTRA BONDS, FINDS WHAT BONDS HAVE BOTH ATOMS FULLY INSIDE A QUADTREE
				 */
				if (tick %1 == 0)
				{
					Collider.deleteFarBonds(bondList);
					if (!pause)
					{
						root.addBonds(atomList, bondList, root.bList);
						root.DoForce(atomList, bondList);
					}
				}
				
				
				/*********
				 * CLEARS ATOMS AND BONDS AND STUFF
				 */
				if (clear)
				{
					currentLevel.reset(atomList, bondList);
					clear = false;
				}
				

				/*********
				 * CHECKS FOR WIN CONDITION
				 */
				//this can't check for wins right after the program starts, for some reason
				if (tick > 2)
				{
					
					//you went over budget!
					if (currentCost > currentLevel.getBudget())
					{
						currentLevel.reset(atomList, bondList);
						carLocation = -1;
					}
					//uses another atom to check for distance
					else if (carLocation != -1)
					{
						
						double dist = Math.sqrt(Math.pow(currentLevel.getTarget().x-atomList.get(carLocation).getPoint().x, 2)+Math.pow(currentLevel.getTarget().y-atomList.get(carLocation).getPoint().y, 2));
						//System.out.println(dist);
						if (dist < car.getMinDist()+50)
						{
							currentLevel.pass();
						}
					}
					
					//if current level passed
					if (currentLevel.passed)
					{
						//update highScore
						lGen.writeHighScore(currentLevelNumber);
					}
					
					//after tutorial, skip to highscore level
					if (currentLevel.passed && currentLevelNumber == 0)
					{
						if (lGen.highScore > 1 && lGen.highScore+1 < levelList.size())
						{
							currentLevelNumber = lGen.highScore;
						}
					}
					
					if (currentLevel.passed)
					{
						
						
						if (currentLevelNumber < levelList.size()-1)
						{
							currentLevelNumber++;
							System.out.println(currentLevelNumber);
							currentLevel = levelList.get(currentLevelNumber);
							currentLevel.reset(atomList, bondList);
							carLocation = -1;
							overBudgetMessageTime = maxOverBudgetMessageTime;
						}
					}
				}
				
				//updates mouse positions
				updateMouseLoc();

				//repaints
				if (paintTime >= repaintTime)
				{
					repaint();
					paintTime = 0;
				}
				tick ++;
			}
				
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public void paint(Graphics gr)
	{
		Image i=createImage(getSize().width, getSize().height);
		Graphics2D g2 = (Graphics2D)i.getGraphics();
		
		//scales
		g2.scale(scale, scale);
		
		//sets antialiasing
	    g2.setRenderingHints(new RenderingHints(
	             RenderingHints.KEY_TEXT_ANTIALIASING,
	             RenderingHints.VALUE_TEXT_ANTIALIAS_ON));
	    g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
		
		g2.setColor(new Color(0, 0, 0));
		
		//draws background
		g2.drawImage(background, 0, 0, 1200, 1000, this);
		
		//draws level number and cost, also material options
		g2.setFont(new Font("TimesRoman", Font.PLAIN, 100));
		g2.drawString("Level: "+Integer.toString(currentLevelNumber), 800, 140);
		
		g2.setFont(new Font("TimesRoman", Font.PLAIN, 30));
		g2.drawString("Budget: "+currentLevel.getBudget(), 900, 200);
		
		g2.setFont(new Font("TimesRoman", Font.PLAIN, 30));
		g2.drawString("Cost: "+currentCost, 900, 240);
		
		
		//draws messages
		
		//adds to message time
		overBudgetMessageTime += repaintTime;
		clearMessageTime += repaintTime;
				
		if (overBudgetMessageTime < maxOverBudgetMessageTime && clearMessageTime >= maxClearMessageTime)
		{
			g2.setFont(new Font("TimesRoman", Font.PLAIN, 60));

			g2.drawString("You went over budget,", 400, 400);
			g2.drawString("so we wrecked your bridge.", 400, 600);
		}
		if (overBudgetMessageTime >= maxOverBudgetMessageTime && clearMessageTime < maxClearMessageTime)
		{
			g2.setFont(new Font("TimesRoman", Font.PLAIN, 30));

			g2.drawString("Reset", 250, 100);
			
		}
		
		g2.setColor(new Color(255, 0, 0));
		//for tutorial
		if (currentLevelNumber == 0)
		{
			
			g2.setFont(new Font("TimesRoman", Font.PLAIN, 60));
			g2.drawString("BRIDGE ENGINEER 2", 80, 100);
			g2.setFont(new Font("TimesRoman", Font.PLAIN, 40));
			g2.drawString("How to play: ", 80, 200);
			g2.setFont(new Font("TimesRoman", Font.PLAIN, 40));
			g2.drawString("Click and drag to fill a rectangle with atoms.", 80, 260);
			g2.drawString("Connect the bridge so the red ball can move across.", 80, 320);
			g2.drawString("This is a hardpoint,", 280, 500);
			g2.drawString("atoms stick to it.", 280, 540);
			g2.drawString("Atoms inside this  ---->", 80, 740);
			g2.drawString("rectangle are set to road.", 80, 780);
			g2.drawString("Press \"p\" or SPACE to toggle pause.", 80, 840);
			g2.drawString("Press BACKSPACE or \"c\" to reset.", 80, 880);
			
			g2.setColor(Color.cyan);
			g2.drawString("Get the car to the cyan circle", 400, 650);
		}
		
		//draws cost
		/*
		g2.setFont(new Font("TimesRoman", Font.PLAIN, 30));
		g2.drawString("Chose materials by pressing numbers", 80, 940);
		g2.drawString("1: Goop, $10   2: Concrete, $20   3: Steel, $30   4: Liquid, $2", 80, 990);
		*/
		
		//draws level
		currentLevel.draw(g2);
		
		
		drawer.drawQuadTrees(g2, root);
		
		
		//draw buttons
		bManager.draw(g2, buttonList);
		
		drawer.drawAndHighlightAtoms(g2, atomList, root, mouseX, mouseY, pressed);
		
		
		drawer.drawBonds(g2, bondList, atomList);
		
		
		//draws cursor
		Atom tmpAtom = new Atom();
		tmpAtom.setMaterial(selectedMaterial);
		tmpAtom.setColor(selectedMaterial.getColor(), false);
		tmpAtom.setPosition(mouseX, mouseY-selectedMaterial.getMinDist()*0);
		tmpAtom.draw(g2);
		
		
		
		/*
		g2.drawString(Double.toString(maxLengthChange), 10, 40);
		g2.drawString(Double.toString(maxSpeed), 10, 60);
		g2.drawString(Double.toString(timeStep), 10, 80);
		*/
		
		
		g2.dispose();
		gr.drawImage(i, 0, 0, this);
	}
	
	public double randDouble(double lowest, double highest)
	{
		Random r = new Random();
		double rnd = r.nextDouble();
		double ans = lowest + (highest-lowest)*rnd;
		return ans;
	}
	
	public static void main(String[] args)
	{
		run frame = new run ();
		frame.setSize((int)(1200*scale), (int)(1000*scale));
		frame.setVisible(true);
	}
	public void update(Graphics g)
	{
		paint(g);
	}

	public void mouseClicked(MouseEvent e) {
		pressed = true;
		System.out.println("nopenopenoepnopen");
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {
		startPoint.setLocation(e.getX()/scale, e.getY()/scale);
	}

	public void mouseReleased(MouseEvent e) {
		endPoint.setLocation(e.getX()/scale, e.getY()/scale);
		place = true;
	}
	
	public void updateMouseLoc()
	{
		Point tmpPoint = MouseInfo.getPointerInfo().getLocation();
		mouseX = (int)(tmpPoint.x/scale);
		mouseY = (int)(tmpPoint.y/scale);
		bManager.checkButtonHover(buttonList, new Point(mouseX, mouseY));
	}

	public void keyPressed(KeyEvent k) {
		
	}

	public void keyReleased(KeyEvent k) {
		//clears stuff
		if (k.getKeyCode() == 8 || k.getKeyCode() == 67)
		{
			clear = true;
			overBudgetMessageTime = maxOverBudgetMessageTime;
			clearMessageTime = 0;
			carLocation = -1;
		}
		
		//p
		if (k.getKeyCode() == 80 || k.getKeyCode() == 32)
		{
			pause = !pause;
			//updates current cost
			currentCost = 0;
			for (int i = 0; i < atomList.size(); i++)
			{
				currentCost += atomList.get(i).m.getCost();
			}
			
		}
		
		//1
		if (k.getKeyCode() == 49)
		{
			selectedMaterial = goop;
		}
		
		//2
		if (k.getKeyCode() == 50)
		{
			selectedMaterial = concrete;
		}
		
		//3
		if (k.getKeyCode() == 51)
		{
			selectedMaterial = steel;
		}
		
		//4
		if (k.getKeyCode() == 52)
		{
			selectedMaterial = liquid;
		}
	}

	public void keyTyped(KeyEvent k) {
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
