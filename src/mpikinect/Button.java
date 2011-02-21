package mpikinect;

import java.awt.Point;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

public class Button {
	public static int LOADING_BOTTOM_TO_TOP = 0;
	public static int LOADING_LEFT_TO_RIGHT = 1;
	public static int LOADING_RIGHT_TO_LEFT = 2;
	
	
	public static int LOADING_DURATION = 2000; //in ms 
	
	
	
	private PImage imgEmpty, imgFull, imgOverlay;
	private int width, height;
	private int posX, posY;
	private PApplet pApplet;
	
	private float clickStartTime;
	private float clickProgress; // 0...1
	private boolean isHovered;
	private int loadingDirection;
	
	
//	PGraphics g;
	
	public Button(PImage imageEmpty, PImage imageLoaded, int posX, int posY, int loadingDirection, PApplet pApplet) {
		this.imgEmpty = imageEmpty;
		this.imgFull = imageLoaded;
		this.width = imgEmpty.width;
		this.height = imageEmpty.height;
		this.imgOverlay = new PImage(width, height, PImage.ARGB);
		imgOverlay.copy(imgEmpty, 0,0,width,height,0,0,width,height);
		this.posX = posX;
		this.posY = posY;
		this.pApplet = pApplet;
		this.loadingDirection = loadingDirection;
		
		
//		  g = pApplet.createGraphics(width, height, PApplet.JAVA2D);
//		  g.background(0,0);
	}
		
	/**
	 * processes button animation
	 * @param time current time in ms
	 * @return true if button animation is over, i.e. if button is pressed long enough
	 */
	public boolean hover(float time) {
		if(!isHovered) {
			clickStartTime = time;
			isHovered = true;
		}
			
		float dt = time-clickStartTime;
		clickProgress = PApplet.min(1, dt/LOADING_DURATION);
		
		if(loadingDirection==LOADING_BOTTOM_TO_TOP) {
			int dy = (int)(height*clickProgress);
			imgOverlay.copy(imgFull, 0, height-dy, width, dy, 0, height-dy, width, dy);	
		}
		else if(loadingDirection==LOADING_LEFT_TO_RIGHT) {
			int dx = (int)(width*clickProgress);
			imgOverlay.copy(imgFull, 0, 0, dx, height, 0, 0, dx, height);	
		}
		else if(loadingDirection==LOADING_RIGHT_TO_LEFT) {
			int dx = (int)(width*clickProgress);
            imgOverlay.copy(imgFull, width-dx, 0, dx, height, width-dx, 0, dx, height);
		}
		
		return (clickProgress >= 1);
		
	}
	
	public void release() {
		if(isHovered) {
			isHovered = false;
			clickProgress = 0;
			imgOverlay.copy(imgEmpty, 0,0,width,height,0,0,width,height);
		}
	}
	
	
	

	/**
	 * draws button including animation onto main pApplet
	 */
	public void draw() {
		
		//pApplet.image(imgEmpty, posX, posY);
		
		//imgEmpty.copy(imgFull, 0, 0, width, height/2, 0, 0, width, height/2);
		//pApplet.image(imgEmpty, posX, posY);
		
		/*
		g.background(0,0);
		g.image(imgEmpty, 0,0);
		int dh = (int)(height*clickProgress);
		g.copy(imgFull, 0, 0, width, dh, 0,0, width, dh);
		pApplet.image(g, posX,posY);
		*/
		
		pApplet.image(imgEmpty, posX, posY);
		if(isHovered)
			pApplet.image(imgOverlay, posX, posY);
		
	}
	
	public boolean isPointOnButton(Point p) {
		return p.x >= posX && p.x <= posX+width && p.y >= posY && p.y <= posY+height;
	}
	

}
