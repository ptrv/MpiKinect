package mpikinect;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

import org.openkinect.processing.Kinect;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;




public class AppMain extends PApplet {
	
	public static boolean DEBUG_MODE = true;
	
	
	//dimension of application
	public static int frameWidth = 640;
	public static int frameHeight = 480;
	
	//dimension for which images/icons etc. are designed
	public static int originalWidth = 1024;
	public static int originalHeight = 768;
	
	
	public enum Screens
	{
		HOME, TEMPLATE_CHOOSER, COLOR_CHOOSER, DRAWING, HELP 
	}


	private Kinect kinect;
	private boolean enableDepth = true;
	private boolean enableRGB = true;
	private int tiltDegrees = 10;


	private HandDetector handDetector;
	private PGraphics gPointer;
	private PImage cursorImg;

	private HomeScreen homeScreen;
	private TemplateChooserScreen templateChooser;
	private ColorChooserScreen colorChooser;
	private DrawingScreen drawingScreen;
	private HelpScreen helpScreen;
	private Screens currentScreen = Screens.HOME;
	
	
	private boolean expertMode; 
	
	
	public void setup() {
		size(frameWidth, frameHeight+40);

		kinect = new Kinect(this);
		kinect.start();
		kinect.enableDepth(enableDepth);
		kinect.enableRGB(enableRGB);
		//kinect.enableIR(false);
		kinect.tilt(tiltDegrees);
		kinect.processDepthImage(false);

		handDetector = new HandDetector(640, 480);

		gPointer = createGraphics(frameWidth, frameHeight, JAVA2D);
		gPointer.stroke(255,0,0);
		cursorImg = loadImage("cursor.png");
		cursorImg.resize((int)(cursorImg.width*((float)frameWidth/originalWidth)), (int)(cursorImg.height*((float)frameHeight/originalHeight)));

		homeScreen = new HomeScreen(this);
		templateChooser = new TemplateChooserScreen(this);
		colorChooser = new ColorChooserScreen(this);
		drawingScreen = new DrawingScreen(this);
		helpScreen = new HelpScreen(this);
	}

	public void draw() {
		
		
		/*
		 * STEP1: Do hand detection
		 */
		handDetector.update(kinect.getRawDepth());
		Hand hand = handDetector.detectHand();
		
		if(hand==null)
			return;

		Point p = hand.getCentroid();
		Rectangle bb = hand.getBoundingBox();

		//draw hand
		gPointer.beginDraw();
		gPointer.background(0,0);
		gPointer.image(cursorImg, p.x-0.44f*cursorImg.width, p.y-0.54f*cursorImg.height);
		if(DEBUG_MODE) {
			gPointer.noFill();
			gPointer.stroke(0, 0, 255);
			gPointer.strokeWeight(3);
			gPointer.rect(bb.x, bb.y, bb.width, bb.height);
			gPointer.ellipse(p.x-2, p.y-2, 5, 5);
		}
		gPointer.endDraw();


		/*
		 * STEP2: Process current screen
		 */
		background(0);
		
		switch (currentScreen) {
		
		case HOME:
			homeScreen.draw(p);
			break;
		
		case HELP:
			helpScreen.draw(p);
			break;
		case TEMPLATE_CHOOSER:
			templateChooser.draw(p);
			break;

		case COLOR_CHOOSER:
			colorChooser.draw(p);
			break;

		case DRAWING:
			image(flipImage(kinect.getVideoImage()),0,0);
			drawingScreen.draw(p);
			break;
			
		default:
			break;
		}


		//always draw hand cursor on top
		image(gPointer, 0, 0); 

		
		
		//fill(255);
		//text("nearest depth: " + hand.getDistance(),10,frameHeight + 35);
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

	}

	public void stop() {
		kinect.quit();
		super.stop();
	}

	public void setExpertMode(boolean enableExpertMode) {
		this.expertMode = enableExpertMode;
	}
	public boolean isExpertMode() {
		return expertMode;
	}
	
	
	public void setCurrentScreen(Screens currentScreen) {
		this.currentScreen = currentScreen;
	}

	public Screens getCurrentScreen() {
		return currentScreen;
	}
	

	public void setDrawColor(Color color){
		drawingScreen.setStrokeColor(color);
	}
	
	public void setHelpInfo(int helpIndex){
		helpScreen.setHelpInfo(helpIndex);
	}

	public static void main(String _args[]) {
		PApplet.main(new String[] { mpikinect.AppMain.class.getName() });
	}
}
