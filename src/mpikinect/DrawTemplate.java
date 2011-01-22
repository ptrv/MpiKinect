package mpikinect;

import java.awt.Point;

import processing.core.PApplet;
import processing.core.PImage;

public abstract class DrawTemplate {
	protected PApplet pApplet;
	protected int stage;
	protected int numStages;
	protected Point[] startPoints;
	protected Point[] stopPoints;
	protected PImage[] stageImages;

	public DrawTemplate(PApplet p){
		this.pApplet = p;
	}
	public abstract void initTemplate();
	
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
