package mpikinect;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

public class DrawingScreen extends Screen {

	PGraphics gPath;

	Point[] curvePoints;

	PImage deleteIcon, startIcon, stopIcon;
	Point deletePt = new Point(30, 30);
	boolean drawingMode = false;
	boolean startPossible = true;
	boolean stopPossible = false;
	
	HeartTemplate template;
	
	int areaSizeX = 40;
	int areaSizeY = 40;
	
	Color strokeColor = Color.red;


	
	public DrawingScreen(AppMain p) {
		super(p);
		this.background = pApplet.loadImage("./res/testBackgroundDrawing.png");
		
		gPath = pApplet.createGraphics(640, 480, pApplet.P2D);
		gPath.background(0,0);

		deleteIcon = pApplet.loadImage("./res/buttons/Delete.png");
		startIcon = pApplet.loadImage("./res/buttons/startButton.png");
		stopIcon = pApplet.loadImage("./res/buttons/stopButton.png");
		
		template = new HeartTemplate(p);
	}

	@Override
	void draw(Point p) {
		pApplet.image(background, 0,0);
		
		
		
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
				pApplet.image(startIcon, start.x-areaSizeX/2, start.y-areaSizeY/2);
			} 
			else {
				pApplet.image(stopIcon, stop.x-areaSizeX/2, stop.y-areaSizeY/2);
			}

			pApplet.image(template.getCurrentStageImage(), 0, 0); 
		}


		pApplet.image(deleteIcon, deletePt.x-areaSizeX/2, deletePt.y-areaSizeY/2);

		if(pointInArea(p, deletePt, areaSizeX, areaSizeY)) {
			erasePath();
			template.reset();
		}
		
		pApplet.image(gPath, 0, 0);

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

	
	private void erasePath() {
		gPath.beginDraw();
		gPath.background(0,0);
		gPath.endDraw();
	}


}
