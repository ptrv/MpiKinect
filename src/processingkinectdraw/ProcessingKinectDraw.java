package processingkinectdraw;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

import javax.xml.ws.Endpoint;

import org.openkinect.processing.Kinect;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;




public class ProcessingKinectDraw extends PApplet {
	Kinect kinect;
	HandDetector handDetector;
	
	boolean enableDepth = true;
	boolean enableRGB = true;
	int tiltDegrees = 10;

	// Size of kinect image
	int w = 640;
	int h = 480;
	

	PGraphics gDebug;
	PGraphics gPath;
	
	Point[] curvePoints;
	
	PImage deleteIcon, startIcon, stopIcon;
	PImage graffitiRed, graffitiGreen, graffitiBlue;
	Point deletePt = new Point(30, 30);
	boolean drawingMode = false;
	boolean startPossible = true;
	boolean stopPossible = false;
	boolean gamePaused = true;
	
	Color strokeColor = Color.red;
	
	HeartTemplate template;
	int areaSizeX = 40;
	int areaSizeY = 40;
	
	public void setup() {
		size(640,520);
 
	  kinect = new Kinect(this);
	  kinect.start();
	  kinect.enableDepth(enableDepth);
	  kinect.enableRGB(enableRGB);
	  kinect.enableIR(false);
	  kinect.tilt(tiltDegrees);
	  kinect.processDepthImage(false);
	  
	  handDetector = new HandDetector(w, h);
	  
	  
	  gDebug = createGraphics(w, h, P2D);
	  gDebug.stroke(255,0,0);
	  gPath = createGraphics(w, h, P2D);
	  gPath.background(0,0);
	  
	  deleteIcon = loadImage("./res/Delete.png");
	  startIcon = loadImage("./res/startButton.png");
	  stopIcon = loadImage("./res/stopButton.png");
	  
	  graffitiGreen = loadImage("./res/graffiti_green.png");
	  graffitiBlue = loadImage("./res/graffiti_blue.png");
	  graffitiRed = loadImage("./res/graffiti_red.png");
	  
	  template = new HeartTemplate(this);
	  
	  //stroke(255,0,0);
	  //fill(255,0,0);
	  
	}

	public void draw() {
		  background(0);
		  handDetector.update(kinect.getRawDepth());

		  
		  image(flipImage(kinect.getVideoImage()),0,0);
		  
		  
		  Hand hand = handDetector.detectHand();
		  if(hand==null)
			  return;
		  
		  
		  Point p = hand.getCentroid();
		  Rectangle bb = hand.getBoundingBox();
		  
		  //draw hand
		  gDebug.beginDraw();
		  gDebug.background(0,0);
		  gDebug.noFill();
		  gDebug.stroke(0, 0, 255);
		  gDebug.strokeWeight(3);
		  gDebug.rect(bb.x, bb.y, bb.width, bb.height);
		  gDebug.ellipse(p.x-2, p.y-2, 5, 5);
		  gDebug.stroke(0, 255,0);
		  //gDebug.ellipse(hand.getNearestPoint().x-2, hand.getNearestPoint().y-2, 5, 5);
		  gDebug.endDraw();
		  
		  


		  if(gamePaused) {

			  gPath.beginDraw();
			  gPath.background(255,50);
			  gPath.endDraw();

			  image(gPath,0,0);
			  image(gDebug,0,0);

			  Point pRed = new Point((int)(w/6), h-50);
			  Point pGreen = new Point((int)(w/2), h-50);
			  Point pBlue = new Point((int)(5*w/6), h-50);
			  image(graffitiRed, pRed.x-graffitiRed.width/2, pRed.y-graffitiRed.height/2);
			  image(graffitiGreen, pGreen.x-graffitiGreen.width/2, pGreen.y-graffitiGreen.height/2);
			  image(graffitiBlue, pBlue.x-graffitiBlue.width/2, pBlue.y-graffitiBlue.height/2);

			  
			  if(pointInArea(p, pRed, graffitiRed.width, graffitiRed.height)) {
				  strokeColor = Color.red;
				  gamePaused = false;
				  erasePath();
			  }
			  if(pointInArea(p, pGreen, graffitiGreen.width, graffitiGreen.height)) {
				  strokeColor = Color.green;
				  gamePaused = false;
				  erasePath();
			  }
			  if(pointInArea(p, pBlue, graffitiBlue.width, graffitiBlue.height)) {
				  strokeColor = Color.blue;
				  gamePaused = false;
				  erasePath();
			  }

			  return;

		  }
		  
		  

		  
		  if(!template.isFinished()) {
			  
			  Point start = template.getCurrentStartPoint();
			  Point stop = template.getCurrentStopPoint();
			  
			  if(pointInArea(p, start, areaSizeX, areaSizeY)) {
				  if(!drawingMode)
					  stopPossible = false;
				  
				  if(!drawingMode && startPossible) {
					  //start drawing
					  drawingMode = true;  
				  }
			  }
			  else 
				  stopPossible = true;
			  
			  if(pointInArea(p, stop, areaSizeX, areaSizeY)) {
				  if(drawingMode)
					  startPossible = false;
				  if(drawingMode && stopPossible) {
					  //end drawing
					  curvePoints[0] = curvePoints[2];
					  curvePoints[1] = curvePoints[3];
					  curvePoints[2] = stop;
					  curvePoints[3] = stop;
					  drawCurvePoints(gPath, curvePoints);
					  
					  curvePoints = null;
					  drawingMode = false;  
					  
					  if(!template.nextStage()) {
						  return;
					  }
						  

				  }
			  }
			  else 
				  startPossible = true;
			  
			  
			  
			  if(drawingMode) {
				  if(curvePoints != null) {
					  curvePoints[0] = curvePoints[1];
					  curvePoints[1] = curvePoints[2];
					  curvePoints[2] = curvePoints[3];
					  curvePoints[3] = p;
				  }
				  else {
					  curvePoints = new Point[4];
					  curvePoints[0] = curvePoints[1] = curvePoints[2] = curvePoints[3] = start;
				  }
				  drawCurvePoints(gPath, curvePoints);
			  }
			  
			  
			  if(!drawingMode) {
				  image(startIcon, start.x-areaSizeX/2, start.y-areaSizeY/2);
			  } 
			  else {
				  image(stopIcon, stop.x-areaSizeX/2, stop.y-areaSizeY/2);
			  }
			  		  
			  image(template.getCurrentStageImage(), 0, 0); 
		  }


		  image(deleteIcon, deletePt.x-areaSizeX/2, deletePt.y-areaSizeY/2);
		  
		  if(pointInArea(p, deletePt, areaSizeX, areaSizeY)) {
			  gamePaused = true;
			  erasePath();
			  template.reset();
		  }

		  image(gDebug, 0, 0); 
		  image(gPath, 0, 0);
		  
		  
		  fill(255);
		  //text("RGB FPS: " + kinect.getVideoFPS(),10,495);
		  //text("DEPTH FPS: " + kinect.getDepthFPS(),640,495);
		  text("nearest depth: " + hand.getDistance(),10,515);
	}
	
	
	private boolean pointInArea(Point p, Point areaC, int areaSizeX, int areaSizeY) {
		
		return 
			p.x >= (areaC.x-areaSizeX/2) && 
			p.x <= (areaC.x+areaSizeX/2) && 
			p.y >= (areaC.y-areaSizeY/2) && 
			p.y <= (areaC.y+areaSizeY/2);
	}
	
	private boolean pointInArea(Point p, Rectangle area) {
		return 
			p.x >= area.x && 
			p.x <= area.x+area.width && 
			p.y >= area.y && 
			p.y <= area.y+area.height;
	}
	
	private void drawCurvePoints(PGraphics g, Point[] curvePoints) {
		g.beginDraw();

		g.stroke(strokeColor.getRGB());
		g.strokeWeight(5);
		g.noFill();

		g.beginShape();
		g.curveVertex(curvePoints[0].x, curvePoints[0].y); 
		g.curveVertex(curvePoints[1].x, curvePoints[1].y);
		g.curveVertex(curvePoints[2].x, curvePoints[2].y);
		g.curveVertex(curvePoints[3].x, curvePoints[3].y);
		g.endShape();

		g.endDraw();

	}
	
	
	private PImage flipImage(PImage img) {
		int w = img.width;
		int h = img.height;
		PImage srcFlipped = new PImage(w, h);
		for(int y=0; y<h; y++) {
			for(int x=0; x<w; x++) {
				srcFlipped.set(x, y, img.pixels[y*w + (w-1-x)]);
			}
		}
		return srcFlipped;
	}
	
	public void keyPressed() {
		if (key == 'd') {
			enableDepth = !enableDepth;
			kinect.enableDepth(enableDepth);
		} 
		else if (key == 'r') {
			enableRGB = !enableRGB;
			kinect.enableRGB(enableRGB);
		}
		else if (key == 'c') {
			erasePath();
		}
	}
	
	private void erasePath() {
		gPath.beginDraw();
		gPath.background(0,0);
		gPath.endDraw();
	}


	public void stop() {
		kinect.quit();
		super.stop();
	}
		
		
		
	
	public static void main(String _args[]) {
		PApplet.main(new String[] { processingkinectdraw.ProcessingKinectDraw.class.getName() });
	}
}
