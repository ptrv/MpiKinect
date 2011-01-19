package processingkinectdraw;

import java.awt.Point;

import processing.core.PApplet;
import processing.core.PImage;

public class HeartTemplate {
	private PApplet pApplet;
	private int stage;
	private int numStages;
	private Point[] startPoints;
	private Point[] stopPoints;
	private PImage[] stageImages;
	
	public HeartTemplate(PApplet pApplet) {
		this.pApplet = pApplet;
		stage = 0;
		numStages = 3;
		
		stageImages = new PImage[numStages];
		stageImages[0] = pApplet.loadImage("./res/template_heart_1.png");
		stageImages[1] = pApplet.loadImage("./res/template_heart_2.png");
		stageImages[2] = pApplet.loadImage("./res/template_heart_3.png");
		
		startPoints = new Point[numStages];
		startPoints[0] = new Point(318,315);
		startPoints[1] = new Point(221,151);
		startPoints[2] = new Point(413,145);
		
		stopPoints = new Point[numStages];
		stopPoints[0] = new Point(318,315);
		stopPoints[1] = new Point(221,151);
		stopPoints[2] = new Point(413,145);
		
	}
	
	public boolean nextStage() {
		stage++;
		if(stage >= numStages) {
			return false;
		}
		return true;
	}
	
	public Point getCurrentStartPoint() {
		return startPoints[stage];
	}
	public Point getCurrentStopPoint() {
		return stopPoints[stage];
	}
	public PImage getCurrentStageImage() {
		return stageImages[stage];
	}
	
	public void reset() {
		stage = 0;
	}
	public boolean isFinished() {
		return stage >= numStages;
	}
}
