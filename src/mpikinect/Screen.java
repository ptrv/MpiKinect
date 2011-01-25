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
	


}

