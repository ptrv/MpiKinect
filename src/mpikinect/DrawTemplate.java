package mpikinect;

import java.awt.Point;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.xml.XMLElement;

public class DrawTemplate {
	private PApplet pApplet;
	private int stage;
	private int numStages;
	private Point[] startPoints;
	private Point[] stopPoints;
	private PImage[] stageImages;
	private XMLElement xml;
	private String xmlPath;
	private String thumbnailFull;
	private String thumbnailEmpty;
	private float thumbnailTopX;
	private float thumbnailTopY;
	private boolean templatedIsFinished = false;
	
	private float IMAGE_RES_X = 1024.0f;
	private float IMAGE_RES_Y = 768.0f;
	

	public DrawTemplate(PApplet p){
		this.pApplet = p;
	}
	public DrawTemplate(PApplet p, XMLElement xmlElem, int index){
		this.pApplet = p;
//		this.xmlPath = templateXmlPath;
		this.xml = xmlElem;
		this.init();
	}
	public void init()
	{
		stage = 0;
		
		XMLElement[] stages = xml.getChildren("stage");
		numStages = stages.length;
		
		float thumbX = Float.parseFloat(xml.getChild("thumb_top_x").getContent());
		float thumbY = Float.parseFloat(xml.getChild("thumb_top_y").getContent());
		setThumbnailTopX(thumbX);
		setThumbnailTopY(thumbY);
		setThumbnailFull(xml.getChild("thumbnail_full").getContent());
		setThumbnailEmpty(xml.getChild("thumbnail_empty").getContent());
		
		stageImages = new PImage[numStages];
		startPoints = new Point[numStages];
		stopPoints = new Point[numStages];
		for (int i = 0; i < numStages; i++) {
			stageImages[i] = pApplet.loadImage(stages[i].getChild("bg").getContent());
			float startX = Float.parseFloat(stages[i].getChild("startx").getContent());
			float startY = Float.parseFloat(stages[i].getChild("starty").getContent());
			
			startPoints[i] = new Point((int)((startX/IMAGE_RES_X)*AppMain.frameWidth),(int)((startY/IMAGE_RES_Y)*AppMain.frameHeight));
			
			float endX = Float.parseFloat(stages[i].getChild("endx").getContent());
			float endY = Float.parseFloat(stages[i].getChild("endy").getContent());
			
			stopPoints[i] = new Point((int)((endX/IMAGE_RES_X)*AppMain.frameWidth),(int)((endY/IMAGE_RES_Y)*AppMain.frameHeight));
		}
		AppMain.adjustImageSize(stageImages);

	}
	
	
	
	public boolean nextStage() {
		stage++;
		if(stage >= numStages) {
			stage = numStages-1;
			templatedIsFinished = true;
			return false;
		}
		return true;
	}
	public boolean previousStage() {
		stage--;
		if(stage < 0) {
			stage = 0;
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
	public int getNumStages() {
		return numStages;
	}
	
	public void reset() {
		templatedIsFinished = false;
		stage = 0;
	}
	public boolean isFinished() {
		return templatedIsFinished;
	}
	public void setThumbnailFull(String thumbnailFull) {
		this.thumbnailFull = thumbnailFull;
	}
	public String getThumbnailFull() {
		return thumbnailFull;
	}
	public void setThumbnailEmpty(String thumbnailEmpty) {
		this.thumbnailEmpty = thumbnailEmpty;
	}
	public String getThumbnailEmpty() {
		return thumbnailEmpty;
	}
	public void setThumbnailTopX(float thumbnailTopX) {
		
		this.thumbnailTopX = thumbnailTopX;
	}
	public float getThumbnailTopX() {
		return thumbnailTopX;
	}
	public void setThumbnailTopY(float thumbnailTopY) {
		
		this.thumbnailTopY = thumbnailTopY;
	}
	public float getThumbnailTopY() {
		return thumbnailTopY;
	}
	

}
