package mpikinect;

import java.awt.Point;

import processing.core.PApplet;
import processing.core.PImage;

public abstract class Screen {
	AppMain pApplet;
	
	PImage background;

	
	Screen(AppMain p){
		pApplet = p;
	}
	
	abstract void draw(Point p);
	
	/**
	 * resizes PImages in such way that they are adjusted to current frame dimensions
	 */
	protected void adjustImageSize(PImage... images) {
		float ratioW = (float)AppMain.frameWidth/AppMain.originalWidth;
		float ratioH = (float)AppMain.frameHeight/AppMain.originalHeight;
		
		for (int i = 0; i < images.length; i++) {
			PImage img = images[i];
			img.resize((int)(img.width*ratioW), (int)(img.height*ratioH));
		}
		
		
	}

}

