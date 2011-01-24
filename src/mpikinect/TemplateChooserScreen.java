package mpikinect;

import java.awt.Point;

import mpikinect.AppMain.Screens;

import processing.core.PApplet;
import processing.core.PImage;

public class TemplateChooserScreen extends Screen {

	static final int NUM_TEMPLATES = 1;
	
//	PImage[] templates = new PImage[NUM_TEMPLATES];
	
	private int currentTemplateIndex;
	private Button buttonPrev, buttonPick, buttonNext;
	private HelpOverlay helpOverlay;
	private boolean interactionEnabled = true;
	
	public TemplateChooserScreen(AppMain p) {
		super(p);
		this.background = pApplet.loadImage("screen2_bg_1024.png");
		this.background.resize(AppMain.frameWidth, AppMain.frameHeight);
		
		PImage imgButton1e = pApplet.loadImage("buttons/button_2_1_empty.png");
		PImage imgButton1f = pApplet.loadImage("buttons/button_2_1_full.png");
		PImage imgButton2e = pApplet.loadImage("buttons/button_2_2_empty.png");
		PImage imgButton2f = pApplet.loadImage("buttons/button_2_2_full.png");
		PImage imgButton3e = pApplet.loadImage("buttons/button_2_3_empty.png");
		PImage imgButton3f = pApplet.loadImage("buttons/button_2_3_full.png");
		adjustImageSize(imgButton1e, imgButton1f, imgButton2e, imgButton2f, imgButton3e, imgButton3f);
		
		this.buttonPrev = new Button(imgButton1e, imgButton1f, (int)(0.07*AppMain.frameWidth), (int)(0.67*AppMain.frameHeight), Button.LOADING_RIGHT_TO_LEFT, p);
		this.buttonPick = new Button(imgButton2e, imgButton2f, (int)(0.38*AppMain.frameWidth), (int)(0.67*AppMain.frameHeight), Button.LOADING_LEFT_TO_RIGHT, p);
		this.buttonNext = new Button(imgButton3e, imgButton3f, (int)(0.75*AppMain.frameWidth), (int)(0.67*AppMain.frameHeight), Button.LOADING_LEFT_TO_RIGHT, p);

		currentTemplateIndex = 0;
		
		PImage helpImg = pApplet.loadImage("help_screen1_1024.png");
		helpOverlay = new HelpOverlay(helpImg, p);
		
	}

	@Override
	void draw(Point p) {
		pApplet.image(background, 0,0);
		
        if(buttonPrev.isPointOnButton(p) && interactionEnabled) {
            if(buttonPrev.hover(pApplet.millis())) { //ok, button is hovered, update overlay animation
                System.out.println("buttonPrev is clicked!!!");
                currentTemplateIndex--;
                buttonPrev.release();
            }
        }
        else
        	buttonPrev.release();

        buttonPrev.draw();

        if(buttonNext.isPointOnButton(p) && interactionEnabled) {
            if(buttonNext.hover(pApplet.millis())) { //ok, button is hovered, update overlay animation
                System.out.println("buttonNext is clicked!!!");
                currentTemplateIndex++;
                buttonNext.release();
            }
        }
        else
        	buttonNext.release();

        buttonNext.draw();

        if(buttonPick.isPointOnButton(p) && interactionEnabled) {
            if(buttonPick.hover(pApplet.millis())) { //ok, button is hovered, update overlay animation
                System.out.println("buttonPick is clicked!!!");
                pApplet.setCurrentScreen(Screens.DRAWING);
                buttonPick.release();
            }
        }
        else
        	buttonPick.release();

        buttonPick.draw();
        
        showTemplateThumbnail(currentTemplateIndex);
        
        
        
        
        if(pApplet.isHelpMode()) {
        	if(!helpOverlay.overlay(pApplet.millis())) {
        		helpOverlay.setOverlayEnabled(false);
        		interactionEnabled = true;
        	}
        	else
        		interactionEnabled = false;
        			
        	helpOverlay.draw();
        }

	}

	private void showTemplateThumbnail(int index) {
		if(index > NUM_TEMPLATES)
            currentTemplateIndex = NUM_TEMPLATES-1;
        else if(index < 0)
            currentTemplateIndex = 0;
	}

	public void setCurrentTemplateIndex(int currentTemplateIndex) {
		this.currentTemplateIndex = currentTemplateIndex;
	}

	public int getCurrentTemplateIndex() {
		return currentTemplateIndex;
	}

}
