package mpikinect;

import java.awt.Point;

import processing.core.PApplet;
import processing.core.PImage;

public class ColorChooserScreen extends Screen {

	PImage graffitiRed, graffitiGreen, graffitiBlue;
	
	public ColorChooserScreen(PApplet p) {
		super(p);
		this.background = parent.loadImage("testBackgroundColor.png");
		
		graffitiRed = parent.loadImage("graffiti_green.png");
		graffitiGreen = parent.loadImage("graffiti_blue.png");
		graffitiBlue = parent.loadImage("graffiti_red.png");

	}

	@Override
	void draw(Point p) {
		// TODO Auto-generated method stub

	}

}
