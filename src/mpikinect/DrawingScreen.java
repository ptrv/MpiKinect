package mpikinect;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

import mpikinect.AppMain.Screens;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.xml.XMLElement;

public class DrawingScreen extends Screen {

	PGraphics gPath;

	Point[] curvePoints;

	PImage startIcon, stopIcon;
	boolean drawingMode = false;
	boolean startPossible = true;
	boolean stopPossible = false;
	
	boolean interactionEnabled = true;
	
	DrawTemplate template = null;
	
	float startStopAreaSizeX = 1.2f;
	float startStopAreaSizeY = 1.2f;
	
	private Color strokeColor = null;
	private Button graffitiRed, graffitiGreen, graffitiOrange, graffitiBlue, undo;
	private Button currentGraffitiButton;
	
	private HelpOverlay helpOverlayColor, helpOverlayDrawing, helpOverlayUndo;
	
	public DrawingScreen(AppMain p) {
		super(p);
		this.background = pApplet.loadImage("screen3_bg_1024.png");
		this.background.resize(AppMain.frameWidth, AppMain.frameHeight);
		
		PImage imgButton1e = pApplet.loadImage("buttons/button_3_1_empty.png");
		PImage imgButton1f = pApplet.loadImage("buttons/button_3_1_full.png");
		PImage imgButton2e = pApplet.loadImage("buttons/button_3_2_empty.png");
		PImage imgButton2f = pApplet.loadImage("buttons/button_3_2_full.png");
		PImage imgButton3e = pApplet.loadImage("buttons/button_3_3_empty.png");
		PImage imgButton3f = pApplet.loadImage("buttons/button_3_3_full.png");
		PImage imgButton4e = pApplet.loadImage("buttons/button_3_4_empty.png");
		PImage imgButton4f = pApplet.loadImage("buttons/button_3_4_full.png");
		PImage imgButton5e = pApplet.loadImage("buttons/button_3_5_empty.png");
		PImage imgButton5f = pApplet.loadImage("buttons/button_3_5_full.png");
		AppMain.adjustImageSize(imgButton1e, imgButton1f, imgButton2e, imgButton2f, imgButton3e, imgButton3f, imgButton4e, imgButton4f, imgButton5e, imgButton5f);

		this.graffitiRed = 		new Button(imgButton1e, imgButton1f, (int)(0.05*AppMain.frameWidth), (int)(0.77*AppMain.frameHeight), Button.LOADING_BOTTOM_TO_TOP, p);
		this.graffitiGreen = 	new Button(imgButton2e, imgButton2f, (int)(0.20*AppMain.frameWidth), (int)(0.77*AppMain.frameHeight), Button.LOADING_BOTTOM_TO_TOP, p);
		this.graffitiOrange = 	new Button(imgButton3e, imgButton3f, (int)(0.35*AppMain.frameWidth), (int)(0.77*AppMain.frameHeight), Button.LOADING_BOTTOM_TO_TOP, p);
		this.graffitiBlue = 	new Button(imgButton4e, imgButton4f, (int)(0.50*AppMain.frameWidth), (int)(0.77*AppMain.frameHeight), Button.LOADING_BOTTOM_TO_TOP, p);
		this.undo = 			new Button(imgButton5e, imgButton5f, (int)(0.80*AppMain.frameWidth), (int)(0.77*AppMain.frameHeight), Button.LOADING_RIGHT_TO_LEFT, p);
		

		gPath = pApplet.createGraphics(640, 480, pApplet.P2D);
		gPath.background(0,0);

		startIcon = pApplet.loadImage("buttons/startButton.png");
		stopIcon = pApplet.loadImage("buttons/stopButton.png");
		
		PImage helpImgColor = pApplet.loadImage("help_screen2_1024.png");
		helpOverlayColor = new HelpOverlay(helpImgColor, p);
		PImage helpImgDrawing = pApplet.loadImage("help_screen3_1024.png");
		helpOverlayDrawing = new HelpOverlay(helpImgDrawing, p);
		helpOverlayDrawing.setOverlayEnabled(false);
		PImage helpImgUndo = pApplet.loadImage("help_screen4_1024.png");
		helpOverlayUndo = new HelpOverlay(helpImgUndo, p);
		helpOverlayUndo.setOverlayEnabled(false);
	}

	@Override
	void draw(Point p) {
		pApplet.image(background, 0,0);
		if(template==null)
			return;
		
		/*
		 * process graffiti color buttons
		 */
		if(!drawingMode && interactionEnabled) {
	        if(graffitiRed.isPointOnButton(p)) {
	            if(graffitiRed.hover(pApplet.millis())) { 
	                System.out.println("graffitiRed is clicked!!!");
	                if(strokeColor==null)
	                	helpOverlayDrawing.setOverlayEnabled(true);
	                strokeColor = Color.RED;
	                if(currentGraffitiButton!=null && currentGraffitiButton!=graffitiRed)
	                	currentGraffitiButton.release();
	                currentGraffitiButton = graffitiRed;
	            }
	        }
	        else if(currentGraffitiButton!=graffitiRed)
	        	graffitiRed.release();

			if(graffitiGreen.isPointOnButton(p)) {
	            if(graffitiGreen.hover(pApplet.millis())) { 
	                System.out.println("graffitiGreen is clicked!!!");
	                if(strokeColor==null)
	                	helpOverlayDrawing.setOverlayEnabled(true);
	                strokeColor = Color.GREEN;
	                if(currentGraffitiButton!=null && currentGraffitiButton!=graffitiGreen)
	                	currentGraffitiButton.release();
	                currentGraffitiButton = graffitiGreen;
	            }
	        }
	        else if(currentGraffitiButton!=graffitiGreen)
	        	graffitiGreen.release();


	        if(graffitiOrange.isPointOnButton(p)) {
	            if(graffitiOrange.hover(pApplet.millis())) { 
	                System.out.println("graffitiOrange is clicked!!!");
	                if(strokeColor==null)
	                	helpOverlayDrawing.setOverlayEnabled(true);
	                strokeColor = Color.ORANGE;
	                if(currentGraffitiButton!=null && currentGraffitiButton!=graffitiOrange)
	                	currentGraffitiButton.release();
	                currentGraffitiButton = graffitiOrange;
	            }
	        }
	        else if(currentGraffitiButton!=graffitiOrange)
	        	graffitiOrange.release();

			
	        if(graffitiBlue.isPointOnButton(p)) {
	            if(graffitiBlue.hover(pApplet.millis())) { 
	                System.out.println("graffitiBlue is clicked!!!");
	                if(strokeColor==null)
	                	helpOverlayDrawing.setOverlayEnabled(true);
	                strokeColor = Color.BLUE;
	                if(currentGraffitiButton!=null && currentGraffitiButton!=graffitiBlue)
	                	currentGraffitiButton.release();
	                currentGraffitiButton = graffitiBlue;
	            }
	        }
	        else if(currentGraffitiButton!=graffitiBlue)
	        	graffitiBlue.release();

		}
		graffitiRed.draw();
		graffitiGreen.draw();
        graffitiOrange.draw();
		graffitiBlue.draw();
		
		
		/*
		 * process undo button
		 */
		
        if(undo.isPointOnButton(p) && interactionEnabled) {
            if(undo.hover(pApplet.millis())) { 
                erasePath();
                template.reset();
            }
        }
        else
        	undo.release();

        undo.draw();
        
        
        if(strokeColor==null) {
        	
            if(pApplet.isHelpMode()) {
            	if(!helpOverlayColor.overlay(pApplet.millis())) {
            		helpOverlayColor.setOverlayEnabled(false);
            		interactionEnabled = true;
            	}
            	else
            		interactionEnabled = false;
            			
            	helpOverlayColor.draw();
            }
        	
        	
        	return;
        }
        	
        
        
		
		
		/*
		 * process template drawing
		 */
		if(!template.isFinished() && interactionEnabled) {

			Point start = template.getCurrentStartPoint();
			Point stop = template.getCurrentStopPoint();

			if(pointInArea(p, start, (int)(startIcon.width*startStopAreaSizeX), (int)(startIcon.height*startStopAreaSizeY))) {
				if(!drawingMode)
					stopPossible = false;

				if(!drawingMode && startPossible) {
					//start drawing
					drawingMode = true;  
				}
			}
			else 
				stopPossible = true;

			if(pointInArea(p, stop,  (int)(stopIcon.width*startStopAreaSizeX), (int)(stopIcon.height*startStopAreaSizeY))) {
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

					if(template.getCurrentStage()==0)
						helpOverlayUndo.setOverlayEnabled(true);
					
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

			
			pApplet.image(template.getCurrentStageImage(), 0, 0); 
			
			if(!drawingMode) {
				pApplet.image(startIcon, start.x-startIcon.width/2, start.y-startIcon.height/2);
			} 
			else {
				pApplet.image(stopIcon, stop.x-stopIcon.width/2, stop.y-stopIcon.height/2);
			}


		}

		pApplet.image(gPath, 0, 0);
		
		
		/*
		 * process help overlays
		 */
        if(pApplet.isHelpMode()) {
        	if(helpOverlayUndo.isEnabled()) {
            	if(!helpOverlayUndo.overlay(pApplet.millis())) {
            		helpOverlayUndo.setOverlayEnabled(false);
            		interactionEnabled = true;
            	}
            	else
            		interactionEnabled = false;
            			
            	helpOverlayUndo.draw();
        	}
        	else if(helpOverlayDrawing.isEnabled()) {
            	if(!helpOverlayDrawing.overlay(pApplet.millis())) {
            		helpOverlayDrawing.setOverlayEnabled(false);
            		interactionEnabled = true;
            	}
            	else
            		interactionEnabled = false;
            			
            	helpOverlayDrawing.draw();
        	}

        }

	}
	
	
	private void drawCurvePoints(PGraphics g, Point[] curvePoints) {
		g.beginDraw();

		g.stroke(getStrokeColor().getRGB());
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

	public void setStrokeColor(Color strokeColor) {
		this.strokeColor = strokeColor;
	}

	public Color getStrokeColor() {
		return strokeColor;
	}

	public void setCurrentTemplate(DrawTemplate template) {
		this.template = template;
		
	}


}
