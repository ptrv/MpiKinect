package mpikinect;

import java.awt.Point;

import processing.core.PApplet;
import processing.core.PImage;

public class TemplateChooserScreen extends Screen {

	static final int NUM_TEMPLATES = 2;
	
	PImage[] templates = new PImage[NUM_TEMPLATES];
	
	private int currentTemplateIndex;
	
	public TemplateChooserScreen(PApplet p) {
		super(p);
		this.background = parent.loadImage("testBackgroundTemplate.png");
		currentTemplateIndex = 0;
	}

	@Override
	void draw(Point p) {
		// TODO Auto-generated method stub

	}

	public void setCurrentTemplateIndex(int currentTemplateIndex) {
		this.currentTemplateIndex = currentTemplateIndex;
	}

	public int getCurrentTemplateIndex() {
		return currentTemplateIndex;
	}

}
