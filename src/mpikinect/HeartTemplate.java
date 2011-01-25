package mpikinect;

import java.awt.Point;

import processing.core.PApplet;
import processing.core.PImage;

public class HeartTemplate extends DrawTemplate{
	
	public HeartTemplate(PApplet pApplet) {
		super(pApplet);
		initTemplate();
	}

	@Override
	public void initTemplate() {
		stage = 0;
		numStages = 3;
		
		stageImages = new PImage[numStages];
		stageImages[0] = pApplet.loadImage("templates/template_heart_1_1024.png");
		stageImages[1] = pApplet.loadImage("templates/template_heart_2_1024.png");
		stageImages[2] = pApplet.loadImage("templates/template_heart_3_1024.png");
		AppMain.adjustImageSize(stageImages);
		
		startPoints = new Point[numStages];
		startPoints[0] = new Point((int)(0.505f*AppMain.frameWidth),(int)(0.659f*AppMain.frameHeight));
		startPoints[1] = new Point((int)(0.309f*AppMain.frameWidth),(int)(0.243f*AppMain.frameHeight));
		startPoints[2] = new Point((int)(0.700f*AppMain.frameWidth),(int)(0.221f*AppMain.frameHeight));
		
		stopPoints = new Point[numStages];
		stopPoints[0] = new Point((int)(0.505f*AppMain.frameWidth),(int)(0.659f*AppMain.frameHeight));
		stopPoints[1] = new Point((int)(0.309f*AppMain.frameWidth),(int)(0.243f*AppMain.frameHeight));
		stopPoints[2] = new Point((int)(0.700f*AppMain.frameWidth),(int)(0.221f*AppMain.frameHeight));

		
	}
}
