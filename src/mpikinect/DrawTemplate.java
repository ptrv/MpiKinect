package mpikinect;

import java.awt.Point;

import processing.core.PApplet;
import processing.core.PImage;
import processing.xml.XMLElement;

public class DrawTemplate {
	protected PApplet pApplet;
	protected int stage;
	protected int numStages;
	protected Point[] startPoints;
	protected Point[] stopPoints;
	protected PImage[] stageImages;
	protected XMLElement xml;
	protected String xmlPath;
	protected PImage thumbnailFull;
	protected PImage thumbnailEmpty;

	public DrawTemplate(PApplet p){
		this.pApplet = p;
	}
	public DrawTemplate(PApplet p, XMLElement xmlElem){
		this.pApplet = p;
//		this.xmlPath = templateXmlPath;
		this.xml = xmlElem;
		this.init();
	}
	public void init()
	{
		stage = 0;
//		xml = new XMLElement();
//		thumbnailFull = pApplet.loadImage(xml.getChild("thumbnail_full").getContent());
//		thumbnailEmpty = pApplet.loadImage(xml.getChild("thumbnail_empty").getContent());
		
		XMLElement[] stages = xml.getChildren("stage");
		numStages = stages.length;
		
		stageImages = new PImage[numStages];
		startPoints = new Point[numStages];
		stopPoints = new Point[numStages];
		for (int i = 0; i < numStages; i++) {
			stageImages[i] = pApplet.loadImage(stages[i].getChild("bg").getContent());
			float startX = Float.parseFloat(stages[i].getChild("startx").getContent());
			float startY = Float.parseFloat(stages[i].getChild("starty").getContent());
			startPoints[i] = new Point((int)(startX*AppMain.frameWidth),(int)(startY*AppMain.frameHeight));
			float endX = Float.parseFloat(stages[i].getChild("endx").getContent());
			float endY = Float.parseFloat(stages[i].getChild("endy").getContent());
			stopPoints[i] = new Point((int)(endX*AppMain.frameWidth),(int)(endY*AppMain.frameHeight));
			
		}
		AppMain.adjustImageSize(stageImages);
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
	public int getCurrentStage() {
		return stage;
	}
	
	public void reset() {
		stage = 0;
	}
	public boolean isFinished() {
		return stage >= numStages;
	}
	

}
