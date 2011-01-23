package mpikinect;

import java.awt.Point;

import processing.core.PApplet;
import processing.core.PImage;

public class ColorChooserScreen extends Screen {

	Button graffitiRed, graffitiGreen, graffitiBlue;
	
	public ColorChooserScreen(AppMain p) {
		super(p);
		this.background = pApplet.loadImage("testBackgroundColor.png");
		


	}

	@Override
	void draw(Point p) {
		pApplet.image(background, 0,0);

	}

}
