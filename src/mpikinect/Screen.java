package mpikinect;

import java.awt.Point;

import processing.core.PApplet;
import processing.core.PImage;

public abstract class Screen {
	PApplet parent;
	
	PImage background;
	
	
	Screen(PApplet p){
		parent = p;
	}
	
	abstract void draw(Point p);

}

