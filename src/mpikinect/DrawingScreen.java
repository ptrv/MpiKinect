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

	public enum Modes
	{
		FIRST_COLOR_CHOOSE, DRAWING, END
	}
	private Modes currentMode = Modes.FIRST_COLOR_CHOOSE;
	
	
	PGraphics[] gPaths;

	Point[] curvePoints;

	PImage startIcon, stopIcon;
	boolean drawingMode = false;
	boolean startPossible = true;
	boolean stopPossible = false;
	
	boolean interactionEnabled = true;
	
	DrawTemplate template = null;
	
	float startStopAreaFactor = 0.07f; //relative to frameWidth
	float stopPossibleAreaFactor = 0.2f; //relative to frameWidth
	
	private Color strokeColor = null;
	private Button graffitiRed, graffitiGreen, graffitiOrange, graffitiBlue, undo, redraw, cancel;
	private Button currentGraffitiButton;
	private PImage endBg;
	
	private HelpOverlay helpOverlayColor, helpOverlayDrawing, helpOverlayUndo;
	
	public DrawingScreen(AppMain p) {
		super(p);
		this.background = pApplet.loadImage("screen3_bg_1024.png");
		this.background.resize(AppMain.frameWidth, AppMain.frameHeight);
		
		this.endBg = pApplet.loadImage("screen4_bg_1024.png");
		
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
		PImage imgButton6e = pApplet.loadImage("buttons/button_4_1_empty.png");
		PImage imgButton6f = pApplet.loadImage("buttons/button_4_1_full.png");
		PImage imgButton7e = pApplet.loadImage("buttons/button_4_2_empty.png");
		PImage imgButton7f = pApplet.loadImage("buttons/button_4_2_full.png");
		AppMain.adjustImageSize(imgButton1e, imgButton1f, imgButton2e, imgButton2f, imgButton3e, imgButton3f, imgButton4e, imgButton4f, imgButton5e, imgButton5f, imgButton6e, imgButton6f, imgButton7e, imgButton7f, endBg);
		
		this.graffitiRed = 		new Button(imgButton1e, imgButton1f, (int)(0.05*AppMain.frameWidth), (int)(0.77*AppMain.frameHeight), Button.LOADING_BOTTOM_TO_TOP, p);
		this.graffitiGreen = 	new Button(imgButton2e, imgButton2f, (int)(0.20*AppMain.frameWidth), (int)(0.77*AppMain.frameHeight), Button.LOADING_BOTTOM_TO_TOP, p);
		this.graffitiOrange = 	new Button(imgButton3e, imgButton3f, (int)(0.35*AppMain.frameWidth), (int)(0.77*AppMain.frameHeight), Button.LOADING_BOTTOM_TO_TOP, p);
		this.graffitiBlue = 	new Button(imgButton4e, imgButton4f, (int)(0.50*AppMain.frameWidth), (int)(0.77*AppMain.frameHeight), Button.LOADING_BOTTOM_TO_TOP, p);
		this.undo = 			new Button(imgButton5e, imgButton5f, (int)(0.80*AppMain.frameWidth), (int)(0.77*AppMain.frameHeight), Button.LOADING_RIGHT_TO_LEFT, p);
		
		this.cancel = 			new Button(imgButton6e, imgButton6f, (int)(0.071*AppMain.frameWidth), (int)(0.829*AppMain.frameHeight), Button.LOADING_LEFT_TO_RIGHT, p);
		this.redraw = 			new Button(imgButton7e, imgButton7f, (int)(0.335*AppMain.frameWidth), (int)(0.829*AppMain.frameHeight), Button.LOADING_LEFT_TO_RIGHT, p);

		

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
		
		
		
		switch(currentMode) {
			case FIRST_COLOR_CHOOSE:
				processGraffitiButtons(p);
				break;
				
			case DRAWING:
				processGraffitiButtons(p);
				processUndoButton(p);
				processDrawing(p);
				drawPath();
				drawTemplate();
				drawTemplateStartEnd();
				break;
				
			case END:
				drawPath();
				processEndMenu(p);
				break;

		}
		
		
		
		processHelpOverlays(p);


	}
	
	
	
	private void processGraffitiButtons(Point p) {
		if(!drawingMode && interactionEnabled) {
	        doGraffitiButtonInteraction(p, graffitiRed, Color.RED);
	        doGraffitiButtonInteraction(p, graffitiGreen, Color.GREEN);
	        doGraffitiButtonInteraction(p, graffitiOrange, Color.ORANGE);
	        doGraffitiButtonInteraction(p, graffitiBlue, Color.BLUE);
		}
		graffitiRed.draw();
		graffitiGreen.draw();
        graffitiOrange.draw();
		graffitiBlue.draw();
		
		if(currentMode == Modes.FIRST_COLOR_CHOOSE && strokeColor!=null)
			currentMode = Modes.DRAWING;
	}
	
	private void processUndoButton(Point p) {
        if(undo.isPointOnButton(p) && interactionEnabled) {
            if(undo.hover(pApplet.millis())) { 
                undoCurrentStep();
                undo.release();
            }
        }
        else
        	undo.release();

        undo.draw();
	}
	
	private void processDrawing(Point p) {
		
		if(!template.isFinished() && interactionEnabled) {

			Point start = template.getCurrentStartPoint();
			Point stop = template.getCurrentStopPoint();


			if(drawingMode && !stopPossible && !pointInArea(p, start, (int)(AppMain.frameWidth*stopPossibleAreaFactor), (int)(AppMain.frameWidth*stopPossibleAreaFactor))) {
				stopPossible = true;
				System.out.println("stop possible from now on!");
			}
			
			
			if(pointInArea(p, start, (int)(AppMain.frameWidth*startStopAreaFactor), (int)(AppMain.frameWidth*startStopAreaFactor))) {
				if(!drawingMode) {
					System.out.println("start drawing");
					drawingMode = true;  
					stopPossible = false;
				}
			}

			if(pointInArea(p, stop,  (int)(AppMain.frameWidth*startStopAreaFactor), (int)(AppMain.frameWidth*startStopAreaFactor))) {
				if(drawingMode && stopPossible) {
					//end drawing
					System.out.println("end drawing");
					
					curvePoints[0] = curvePoints[2];
					curvePoints[1] = curvePoints[3];
					curvePoints[2] = stop;
					curvePoints[3] = stop;
					drawCurvePoints(gPaths[template.getCurrentStage()], curvePoints);

					curvePoints = null;
					drawingMode = false;  

					if(template.getCurrentStage()==0)
						helpOverlayUndo.setOverlayEnabled(true);
					
					if(!template.nextStage()) {
						return;
					}
				}
			}


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
				drawCurvePoints(gPaths[template.getCurrentStage()], curvePoints);
			}
			
		}
		
		else if(template.isFinished()) {
			System.out.println("Drawing finished. Proceed to end mode!");
			currentMode = Modes.END;
		}

	}
	
	private void drawTemplate() {
		pApplet.image(template.getCurrentStageImage(), 0, 0); 
	}
	private void drawTemplateStartEnd() {
		Point start = template.getCurrentStartPoint();
		Point stop = template.getCurrentStopPoint();
		if(!drawingMode) {
			pApplet.image(startIcon, start.x-startIcon.width/2, start.y-startIcon.height/2);
		} 
		else {
			pApplet.image(stopIcon, stop.x-stopIcon.width/2, stop.y-stopIcon.height/2);
		}
	}
	
	private void drawPath() {
		for (int i = 0; i <= template.getCurrentStage(); i++) {
			pApplet.image(gPaths[i], 0, 0);
		}
	}
	
	private void processHelpOverlays(Point p) {
		
        if(pApplet.isHelpMode()) {
        	if(helpOverlayColor.isEnabled()) {
               	if(!helpOverlayColor.overlay(pApplet.millis())) {
            		helpOverlayColor.setOverlayEnabled(false);
            		interactionEnabled = true;
            	}
            	else
            		interactionEnabled = false;
            			
            	helpOverlayColor.draw();
        	}
        	else if(helpOverlayUndo.isEnabled()) {
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
	
	private void processEndMenu(Point p) {
		
		//pApplet.image(endBg, (int)(0.025*AppMain.frameWidth), (int)(0.716*AppMain.frameHeight));
		pApplet.image(endBg, 0,0);
		
        if(redraw.isPointOnButton(p) && interactionEnabled) {
            if(redraw.hover(pApplet.millis())) { 
                currentMode = Modes.DRAWING;
            	template.reset();
            	setCurrentTemplate(template);
            }
        }
        else
        	redraw.release();

        
        if(cancel.isPointOnButton(p) && interactionEnabled) {
            if(cancel.hover(pApplet.millis())) { 
            	currentMode = Modes.FIRST_COLOR_CHOOSE;
            	strokeColor = null;
            	currentGraffitiButton.release();
            	template.reset();
                pApplet.setCurrentScreen(Screens.HOME);
            }
        }
        else
        	cancel.release();

        
        redraw.draw();
        cancel.draw();
		
	}
	
	
	
	
	
	
	
	
	
	
	private void doGraffitiButtonInteraction(Point p, Button graffitiButton, Color color) {
        if(graffitiButton.isPointOnButton(p)) {
            if(graffitiButton.hover(pApplet.millis())) { 
                System.out.println("graffitiButton " + color.toString() + " is clicked!!!");
                if(strokeColor==null)
                	helpOverlayDrawing.setOverlayEnabled(true);
                strokeColor = color;
                if(currentGraffitiButton!=null && currentGraffitiButton!=graffitiButton)
                	currentGraffitiButton.release();
                currentGraffitiButton = graffitiButton;
            }
        }
        else if(currentGraffitiButton!=graffitiButton)
        	graffitiButton.release();
	}
	
	
	private void drawCurvePoints(PGraphics g, Point[] curvePoints) {
		g.beginDraw();

		g.stroke(getStrokeColor().getRGB());
		g.strokeWeight(15);
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

	
	private void undoCurrentStep() {
		if(template.isFinished())
			return;
		
		if(drawingMode)
			drawingMode = false;
		else 
			template.previousStage();
		
		curvePoints = null;
		
		PGraphics gPath = gPaths[template.getCurrentStage()];
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
		
		this.gPaths = new PGraphics[template.getNumStages()];
		for (int i = 0; i < gPaths.length; i++) {
			PGraphics gPath = pApplet.createGraphics(AppMain.frameWidth, AppMain.frameHeight, PApplet.P2D);
			gPath.background(0,0);
			gPaths[i] = gPath;
		}
		
		System.out.println("Template changed: num steps " + template.getNumStages());
	}


}
