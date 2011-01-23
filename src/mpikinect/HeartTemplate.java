package mpikinect;

import java.awt.Point;

import processing.core.PApplet;
import processing.core.PImage;

public class HeartTemplate extends DrawTemplate{
	
	public HeartTemplate(PApplet p) {
		super(p);
		initTemplate();
	}

	@Override
	public void initTemplate() {
		stage = 0;
		numStages = 3;
		
		stageImages = new PImage[numStages];
		stageImages[0] = pApplet.loadImage("template_heart_1.png");
		stageImages[1] = pApplet.loadImage("template_heart_2.png");
		stageImages[2] = pApplet.loadImage("template_heart_3.png");
		
		startPoints = new Point[numStages];
		startPoints[0] = new Point(318,315);
		startPoints[1] = new Point(221,151);
		startPoints[2] = new Point(413,145);
		
		stopPoints = new Point[numStages];
		stopPoints[0] = new Point(318,315);
		stopPoints[1] = new Point(221,151);
		stopPoints[2] = new Point(413,145);
	}
}
