package mpikinect;

import processing.core.PImage;

public class HelpOverlay {

	public static int OVERLAY_DURATION = 6000; //ms
	
	PImage helpImg;
	AppMain pApplet;
	float startTime;
	boolean overlayActive = false;
	boolean overlayEnabled = true;
	
	public HelpOverlay(PImage image, AppMain main) {
		this.helpImg = image;
		this.helpImg.resize(AppMain.frameWidth, AppMain.frameHeight);
		
		this.pApplet = main;
	}
	
	public boolean overlay(float time) {
		if(!overlayEnabled)
			return false;
		
		if(!overlayActive) {
			startTime = time;
			overlayActive = true;
		}
		float dt = time-startTime;
		
		if(dt > OVERLAY_DURATION) {
			overlayActive = false;
			return false;
		}
		
		return true;
		
	}
	
	public void setOverlayEnabled(boolean enable) {
		overlayEnabled = enable;
	}
	
	
	public void draw() {
		if(!overlayEnabled || !overlayActive)
			return;
		
		pApplet.image(helpImg, 0, 0);
		
		//TODO: Countdown and/or cancel button
		
	}
}
