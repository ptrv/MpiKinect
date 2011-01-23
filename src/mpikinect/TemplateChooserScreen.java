package mpikinect;

import java.awt.Point;

import processing.core.PApplet;
import processing.core.PImage;

public class TemplateChooserScreen extends Screen {

	static final int NUM_TEMPLATES = 2;
	
	PImage[] templates = new PImage[NUM_TEMPLATES];
	
	private int currentTemplateIndex;
	private Button buttonPrev, buttonPick, buttonNext;
	
	public TemplateChooserScreen(AppMain p) {
		super(p);
		this.background = pApplet.loadImage("screen2_bg_1024.png");
		this.background.resize(AppMain.frameWidth, AppMain.frameHeight);
		
		PImage imgButton1e = pApplet.loadImage("buttons/button_2_1_empty.png");
		PImage imgButton1f = pApplet.loadImage("buttons/button_2_1_full.png");
		PImage imgButton2e = pApplet.loadImage("buttons/button_2_2_empty.png");
		PImage imgButton2f = pApplet.loadImage("buttons/button_2_2_full.png");
		PImage imgButton3e = pApplet.loadImage("buttons/button_2_3_empty.png");
		PImage imgButton3f = pApplet.loadImage("buttons/button_2_3_full.png");
		adjustImageSize(imgButton1e, imgButton1f, imgButton2e, imgButton2f, imgButton3e, imgButton3f);
		
		this.buttonPrev = new Button(imgButton1e, imgButton1f, (int)(0.07*AppMain.frameWidth), (int)(0.67*AppMain.frameHeight), Button.LOADING_LEFT_TO_RIGHT, p);
		this.buttonPick = new Button(imgButton2e, imgButton2f, (int)(0.38*AppMain.frameWidth), (int)(0.67*AppMain.frameHeight), Button.LOADING_LEFT_TO_RIGHT, p);
		this.buttonNext = new Button(imgButton3e, imgButton3f, (int)(0.75*AppMain.frameWidth), (int)(0.67*AppMain.frameHeight), Button.LOADING_LEFT_TO_RIGHT, p);

		currentTemplateIndex = 0;
	}

	@Override
	void draw(Point p) {
		pApplet.image(background, 0,0);
		
		buttonPrev.draw();
		buttonPick.draw();
		buttonNext.draw();

	}

	public void setCurrentTemplateIndex(int currentTemplateIndex) {
		this.currentTemplateIndex = currentTemplateIndex;
	}

	public int getCurrentTemplateIndex() {
		return currentTemplateIndex;
	}

}
