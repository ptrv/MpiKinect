package mpikinect;

import java.awt.Point;

import processing.core.PApplet;
import processing.core.PImage;

public abstract class Screen {
	protected PApplet parent;
	protected PImage background;
	
	public Screen(PApplet p){
		parent = p;
	}
	
	abstract void draw(Point p);

}

