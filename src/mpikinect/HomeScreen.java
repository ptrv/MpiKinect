package mpikinect;

import java.awt.Point;

import processing.core.PApplet;
import processing.core.PImage;

public class HomeScreen extends Screen {

	PImage icon1;
	PImage icon2;
	
	public HomeScreen(PApplet p) {
		super(p);
		this.background = parent.loadImage("testBackgroundHome.png");
//		icon1 = parent.loadImage("");
//		icon2 = parent.loadImage("");
	}

	@Override
	void draw(Point p) {
		// TODO Auto-generated method stub

	}

}
