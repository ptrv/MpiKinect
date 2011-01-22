package mpikinect;

import java.awt.Point;

import processing.core.PApplet;
import processing.core.PImage;

public class TemplateChooser extends Screen {

	static final int NUM_TEMPLATES = 2;
	
	PImage templates[] = new PImage[NUM_TEMPLATES];
	
	public TemplateChooser(PApplet p) {
		super(p);
		this.background = parent.loadImage("");
	}

	@Override
	void draw(Point p) {
		// TODO Auto-generated method stub

	}

}
