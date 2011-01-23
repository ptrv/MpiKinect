package mpikinect;

import java.awt.Point;

import processing.core.PImage;

import mpikinect.AppMain.Screens;

public class HelpScreen extends Screen {

//	private String[] helpScreens;
	
	Point deletePt = null;
	
	private Screens lastScreen = Screens.HOME;
	private Screens nextScreen = Screens.TEMPLATE_CHOOSER;
	PImage deleteIcon;
	PImage helpScreens[] = new PImage[3];
	
	public HelpScreen(AppMain p) {
		super(p);
		
		helpScreens[0] = pApplet.loadImage("help_screen_1_1024.png");
		helpScreens[1] = pApplet.loadImage("help_screen_2_1024.png");

		this.background = helpScreens[0];
		this.background.resize(AppMain.frameWidth, AppMain.frameHeight);
		
		deletePt = new Point(AppMain.frameWidth - 80, AppMain.frameHeight - 60);
		
		deleteIcon = pApplet.loadImage("buttons/Delete.png");

	}

	@Override
	void draw(Point p) {
		pApplet.image(background, 0, 0);
		
		pApplet.image(deleteIcon, deletePt.x, deletePt.y);
		if(pointInArea(p, deletePt, 40, 40)) {
			pApplet.setCurrentScreen(nextScreen);
		}

	}
	
	public void setHelpInfo(int helpIndex){
		this.background = helpScreens[helpIndex];
		this.background.resize(AppMain.frameWidth, AppMain.frameHeight);
		lastScreen = pApplet.getCurrentScreen();
		System.out.println(lastScreen);
		System.out.println(nextScreen);
		if(lastScreen == Screens.HOME)
			nextScreen = Screens.TEMPLATE_CHOOSER;
		else if(lastScreen == Screens.TEMPLATE_CHOOSER)
			nextScreen = Screens.DRAWING;
			
	}
	

	private boolean pointInArea(Point p, Point areaC, int areaSizeX, int areaSizeY) {

		return 
		p.x >= (areaC.x-areaSizeX/2) && 
		p.x <= (areaC.x+areaSizeX/2) && 
		p.y >= (areaC.y-areaSizeY/2) && 
		p.y <= (areaC.y+areaSizeY/2);
	}

}
