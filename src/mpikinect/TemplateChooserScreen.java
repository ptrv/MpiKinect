package mpikinect;

import java.awt.Point;

import mpikinect.AppMain.Screens;

import processing.core.PApplet;
import processing.core.PImage;
import processing.xml.XMLElement;

public class TemplateChooserScreen extends Screen {

//	static final int NUM_TEMPLATES = 3;
	
	DrawTemplate[] templates;
	
	private int currentTemplateIndex;
	private HelpOverlay helpOverlay;
	private boolean interactionEnabled = true;
	
	private Button buttons[];
	
	public TemplateChooserScreen(AppMain p) {
		super(p);
		this.background = pApplet.loadImage("screen2_bg_1024.png");
		this.background.resize(AppMain.frameWidth, AppMain.frameHeight);
		
		initTemplates();
		initButtons(p);

		currentTemplateIndex = 0;
		
		PImage helpImg = pApplet.loadImage("help_screen1_1024.png");
		helpOverlay = new HelpOverlay(helpImg, p);
		
	}

	private void initButtons(AppMain p) {
		buttons = new Button[templates.length];
		for (int i = 0; i < templates.length; i++) {
			PImage imgButton1e = pApplet.loadImage(templates[i].getThumbnailEmpty());
			PImage imgButton1f = pApplet.loadImage(templates[i].getThumbnailFull());
			AppMain.adjustImageSize(imgButton1e, imgButton1f);
			
			// TODO Better solution for deviding coordinates through image width and height.
			buttons[i] = new Button(imgButton1e, imgButton1f, 
					(int)templates[i].getThumbnailTopX()*AppMain.frameWidth/AppMain.originalWidth, 
					(int)templates[i].getThumbnailTopY()*AppMain.frameHeight/AppMain.originalHeight, 
					Button.LOADING_BOTTOM_TO_TOP, p);

		}
		
	}

	private void initTemplates() {
		XMLElement xml = new XMLElement(pApplet, "templates.xml");
		int numTemplates = xml.getChildCount();
		templates = new DrawTemplate[numTemplates];
		for (int i = 0; i < numTemplates; i++) {
			XMLElement template = xml.getChild(i);
			templates[i] = new DrawTemplate(pApplet, template, i);			
		}
		
	}

	@Override
	void draw(Point p) {
		pApplet.image(background, 0,0);
		
		for (int i = 0; i < buttons.length; i++) {
			if(buttons[i].isPointOnButton(p) && interactionEnabled) {
	            if(buttons[i].hover(pApplet.millis())) { //ok, button is hovered, update overlay animation
	                System.out.println("button "+i+" is clicked!!!");
	                currentTemplateIndex = i;
	                pApplet.setCurrentTemplate(templates[i]);
	                pApplet.setCurrentScreen(Screens.DRAWING);
	                buttons[i].release();
	            }
	        }
	        else
	        	buttons[i].release();

			buttons[i].draw();
		}
		        
//        showTemplateThumbnail(currentTemplateIndex);
        
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

//	private void showTemplateThumbnail(int index) {
//		if(index > templates.length)
//            currentTemplateIndex = templates.length-1;
//        else if(index < 0)
//            currentTemplateIndex = 0;
//	}

	public void setCurrentTemplateIndex(int currentTemplateIndex) {
		this.currentTemplateIndex = currentTemplateIndex;
	}

	public int getCurrentTemplateIndex() {
		return currentTemplateIndex;
	}


	public DrawTemplate getCurrentTemplate() {
		return templates[currentTemplateIndex];
	}
}
