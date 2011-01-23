package mpikinect;

import java.awt.Point;

import processing.core.PApplet;
import processing.core.PImage;

public class TemplateChooserScreen extends Screen {

	static final int NUM_TEMPLATES = 2;
	
	PImage[] templates = new PImage[NUM_TEMPLATES];
	
	private int currentTemplateIndex;
	
	public TemplateChooserScreen(AppMain p) {
		super(p);
		this.background = pApplet.loadImage("testBackgroundTemplate.png");
		currentTemplateIndex = 0;
	}

	@Override
	void draw(Point p) {
		pApplet.image(background, 0,0);

	}

	public void setCurrentTemplateIndex(int currentTemplateIndex) {
		this.currentTemplateIndex = currentTemplateIndex;
	}

	public int getCurrentTemplateIndex() {
		return currentTemplateIndex;
	}

}
