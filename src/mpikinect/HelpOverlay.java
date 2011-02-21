package mpikinect;

import processing.core.PImage;

public class HelpOverlay {

	public static int OVERLAY_DURATION = 9000; //ms
	
	PImage helpImg;
	AppMain pApplet;
	float startTime, dt;
	boolean overlayActive = false;
	boolean overlayEnabled = true;
	int countShowings = 0;
	int maxNumShowings = 1;
	
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
			countShowings++;
		}
		dt = time-startTime;
		
		if(dt > OVERLAY_DURATION) {
			overlayActive = false;
			return false;
		}
		
		return true;
		
	}
	
	public void setOverlayEnabled(boolean enable) {
		overlayEnabled = enable;
		
		if(overlayEnabled && countShowings>=maxNumShowings)
			overlayEnabled = false;
	}
	
	public boolean isEnabled() {
		return overlayEnabled;
	}
	
	
	public void draw() {
		if(!overlayEnabled || !overlayActive)
			return;
		
		
		
		
		pApplet.image(helpImg, 0, 0);
		
		int sec = (int)Math.ceil((OVERLAY_DURATION-dt)/1000);
		pApplet.fill(255);
		pApplet.textSize(30);
		pApplet.text(""+sec, (int)(0.58*AppMain.frameWidth), (int)(0.34*AppMain.frameHeight));
		
		
		//TODO: Countdown and/or cancel button
		
	}
}
