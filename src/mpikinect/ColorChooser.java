package mpikinect;

import java.awt.Point;

import processing.core.PApplet;
import processing.core.PImage;

public class ColorChooser extends Screen {

	PImage iconRed;
	PImage iconGreen;
	PImage iconBlue;
	
	public ColorChooser(PApplet p) {
		super(p);
		this.background = parent.loadImage("");
		
		iconRed = parent.loadImage("./res/graffiti_green.png");
		iconGreen = parent.loadImage("./res/graffiti_blue.png");
		iconBlue = parent.loadImage("./res/graffiti_red.png");

	}

	@Override
	void draw(Point p) {
		// TODO Auto-generated method stub

	}

}
