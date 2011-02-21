package mpikinect;

import hypermedia.video.Blob;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

import org.openkinect.processing.Kinect;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;




public class AppMain extends PApplet {
	
	public static boolean DEBUG_MODE = false;
	
	/* * * * * * * * * * * * 
	 * BEGIN CONFIGURATION
	 * * * * * * * * * * * */
	
	//dimension of application
	public static int frameWidth = 1024;
	public static int frameHeight = 768;
	
	//dimension for which images/icons etc. are designed
	public static int originalWidth = 1024;
	public static int originalHeight = 768;
	
	//virtually stretch depth image; i.e. boundary areas of the depth images are mapped to regions outside the frame size
	//makes it easier to reach border areas on the frame 
	public static float detectionStretchFactor = 1.3f; 
	
	private static float filtering_motion_threshold = 0.015f; //relative to frameSize
	private static float filtering_oldhand_weight = 0.75f;
	
	/* * * * * * * * * * * * 
	 * END CONFIGURATION
	 * * * * * * * * * * * */
	
	
	
	
	
	
	
	public enum Screens
	{
		HOME, TEMPLATE_CHOOSER, DRAWING
	}


	private Kinect kinect;
	private boolean enableDepth = true;
	private boolean enableRGB = false;
	private int tiltDegrees = 18;


	private HandDetector handDetector;
	private PGraphics gPointer;
	private PImage cursorImg;

	private HomeScreen homeScreen;
	private TemplateChooserScreen templateChooser;
	private DrawingScreen drawingScreen;
	private Screens currentScreen = Screens.HOME;
	
	
	private boolean helpModeEnabled; 
	

	private Point oldHandCentroid;
	private boolean filtering_enabled = true;
	
	public void setup() {
		if(DEBUG_MODE)
			size(frameWidth + 640, frameHeight+40);
		else
			size(frameWidth, frameHeight);

		kinect = new Kinect(this);
		kinect.start();
		kinect.enableDepth(enableDepth);
		kinect.enableRGB(enableRGB);
		kinect.tilt(tiltDegrees);
		kinect.processDepthImage(false);

		handDetector = new HandDetector(640, 480);

		gPointer = createGraphics(frameWidth, frameHeight, JAVA2D);
		gPointer.stroke(255,0,0);
		cursorImg = loadImage("cursor.png");
		cursorImg.resize((int)(cursorImg.width*((float)frameWidth/originalWidth)), (int)(cursorImg.height*((float)frameHeight/originalHeight)));

		homeScreen = new HomeScreen(this);
		templateChooser = new TemplateChooserScreen(this);
		drawingScreen = new DrawingScreen(this);
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
		
		
		
		//filtering of centroid: also take old point into account
		if(oldHandCentroid!=null && filtering_enabled) {
			float dx = Math.abs(p.x - oldHandCentroid.x);
			float dy = Math.abs(p.y - oldHandCentroid.y);
			if(dx < frameWidth*filtering_motion_threshold && dy < frameHeight*filtering_motion_threshold) {
				p.x = (int)Math.round(filtering_oldhand_weight*oldHandCentroid.x + (1-filtering_oldhand_weight)*p.x);
				p.y = (int)Math.round(filtering_oldhand_weight*oldHandCentroid.y + (1-filtering_oldhand_weight)*p.y);
//				oldHandCentroid.x = (int)Math.round( (oldHandCentroid.x + p.x)/2f );
//				oldHandCentroid.y = (int)Math.round( (oldHandCentroid.y + p.y)/2f );
				oldHandCentroid = p;
			}
			else
				oldHandCentroid = p;
		}
		else
			oldHandCentroid = p;
		
		

		//draw hand
		gPointer.beginDraw();
		gPointer.background(0,0);
		gPointer.image(cursorImg, p.x-0.44f*cursorImg.width, p.y-0.54f*cursorImg.height);
		gPointer.endDraw();


		/*
		 * STEP2: Process current screen
		 */
		if(DEBUG_MODE)
			background(80);
		else
			background(0);
		
		switch (currentScreen) {
		
		case HOME:
			homeScreen.draw(p);
			break;
		

		case TEMPLATE_CHOOSER:
			templateChooser.draw(p);
			break;

		case DRAWING:
			//image(flipImage(kinect.getVideoImage()),0,0);
			drawingScreen.draw(p);
			break;
			
		default:
			break;
		}


		//always draw hand cursor on top
		image(gPointer, 0, 0); 

		
		
		
		if(DEBUG_MODE) {
			fill(255);
			text("nearest depth: " + hand.getDistance(),10,frameHeight + 35);
			image(handDetector.getDepthImage(),frameWidth,0);
			
			Blob[] blobs = handDetector.getBlobs();
			
			noFill();
			stroke(0, 0, 255);
			strokeWeight(3);
			for (int i = 0; i < blobs.length; i++) {
				Rectangle bb = blobs[i].rectangle;
				Point c = blobs[i].centroid;
				ellipse(frameWidth+c.x-2, c.y-2, 5, 5);
				rect(frameWidth+bb.x, bb.y, bb.width, bb.height);
			}
			
			stroke(0, 255, 0);
			Point h = handDetector.getHandPos();
			ellipse(frameWidth+h.x-2, h.y-2, 5, 5);

			
		}

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
		if (key == 't') {
			enableDepth = !enableDepth;
			kinect.enableDepth(enableDepth);
		} 
		else if (key == 'r') {
			enableRGB = !enableRGB;
			kinect.enableRGB(enableRGB);
		}
		else if (key == 'd') {
			DEBUG_MODE = !DEBUG_MODE;
		}
		else if (key == 'f') {
			filtering_enabled = !filtering_enabled;
		}
		
		else if (key == '+') {
			HandDetector.increaseThreshold();
		}
		else if (key == '-') {
			HandDetector.decreaseThreshold();
		}

	}

	public void stop() {
		kinect.quit();
		super.stop();
	}

	public void setHelpMode(boolean enableHelpMode) {
		this.helpModeEnabled = enableHelpMode;
	}
	public boolean isHelpMode() {
		return helpModeEnabled;
	}
	
	
	public void setCurrentScreen(Screens currentScreen) {
		this.currentScreen = currentScreen;
	}

	public Screens getCurrentScreen() {
		return currentScreen;
	}
	


	/**
	 * resizes PImages in such way that they are adjusted to current frame dimensions
	 */
	public static void adjustImageSize(PImage... images) {
		float ratioW = (float)AppMain.frameWidth/AppMain.originalWidth;
		float ratioH = (float)AppMain.frameHeight/AppMain.originalHeight;
		
		for (int i = 0; i < images.length; i++) {
			PImage img = images[i];
			img.resize((int)(img.width*ratioW), (int)(img.height*ratioH));
		}
	}
	
	public void setCurrentTemplate(DrawTemplate template) {
		drawingScreen.setCurrentTemplate(template);
	}

	public static void main(String _args[]) {
		
		for (int i = 0; i < _args.length; i++) {
			if(_args[i].endsWith("debug"))
				AppMain.DEBUG_MODE = true;
		}
		
		PApplet.main(new String[] {"--present", mpikinect.AppMain.class.getName() });
	}
}
