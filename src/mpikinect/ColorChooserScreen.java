package mpikinect;

import java.awt.Color;
import java.awt.Point;

import mpikinect.AppMain.Screens;

import processing.core.PApplet;
import processing.core.PImage;

public class ColorChooserScreen extends Screen {

	Button graffitiRed, graffitiGreen, graffitiOrange, graffitiBlue, undo;
	
	public ColorChooserScreen(AppMain p) {
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
		adjustImageSize(imgButton1e, imgButton1f, imgButton2e, imgButton2f, imgButton3e, imgButton3f, imgButton4e, imgButton4f, imgButton5e, imgButton5f);

		this.graffitiRed = 		new Button(imgButton1e, imgButton1f, (int)(0.05*AppMain.frameWidth), (int)(0.77*AppMain.frameHeight), Button.LOADING_BOTTOM_TO_TOP, p);
		this.graffitiGreen = 	new Button(imgButton2e, imgButton2f, (int)(0.20*AppMain.frameWidth), (int)(0.77*AppMain.frameHeight), Button.LOADING_BOTTOM_TO_TOP, p);
		this.graffitiOrange = 	new Button(imgButton3e, imgButton3f, (int)(0.35*AppMain.frameWidth), (int)(0.77*AppMain.frameHeight), Button.LOADING_BOTTOM_TO_TOP, p);
		this.graffitiBlue = 	new Button(imgButton4e, imgButton4f, (int)(0.50*AppMain.frameWidth), (int)(0.77*AppMain.frameHeight), Button.LOADING_BOTTOM_TO_TOP, p);
		this.undo = 			new Button(imgButton5e, imgButton5f, (int)(0.80*AppMain.frameWidth), (int)(0.77*AppMain.frameHeight), Button.LOADING_RIGHT_TO_LEFT, p);
		
	}

	@Override
	void draw(Point p) {
		pApplet.image(background, 0,0);
		
        if(graffitiRed.isPointOnButton(p)) {
            if(graffitiRed.hover(pApplet.millis())) { 
                System.out.println("graffitiRed is clicked!!!");
                pApplet.setDrawColor(Color.RED);
                pApplet.setCurrentScreen(Screens.DRAWING);
                graffitiRed.release();
            }
        }
        else
        	graffitiRed.release();

        graffitiRed.draw();

		
		if(graffitiGreen.isPointOnButton(p)) {
            if(graffitiGreen.hover(pApplet.millis())) { 
                System.out.println("graffitiGreen is clicked!!!");
                pApplet.setDrawColor(Color.GREEN);
                pApplet.setCurrentScreen(Screens.DRAWING);
                graffitiGreen.release();
            }
        }
        else
        	graffitiGreen.release();

		graffitiGreen.draw();
		
        if(graffitiOrange.isPointOnButton(p)) {
            if(graffitiOrange.hover(pApplet.millis())) { 
                System.out.println("graffitiOrange is clicked!!!");
                pApplet.setDrawColor(Color.ORANGE);
                pApplet.setCurrentScreen(Screens.DRAWING);
                graffitiOrange.release();
            }
        }
        else
        	graffitiOrange.release();

        graffitiOrange.draw();
		
        if(graffitiBlue.isPointOnButton(p)) {
            if(graffitiBlue.hover(pApplet.millis())) { 
                System.out.println("graffitiBlue is clicked!!!");
                pApplet.setDrawColor(Color.BLUE);
                pApplet.setCurrentScreen(Screens.DRAWING);
                graffitiBlue.release();
            }
        }
        else
        	graffitiBlue.release();

		graffitiBlue.draw();
		
		
        if(undo.isPointOnButton(p)) {
            if(undo.hover(pApplet.millis())) { 
                System.out.println("TODO: implement undo action!");
            }
        }
        else
        	undo.release();

        undo.draw();


	}

}
