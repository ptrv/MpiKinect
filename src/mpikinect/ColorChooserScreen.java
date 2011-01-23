package mpikinect;

import java.awt.Color;
import java.awt.Point;

import mpikinect.AppMain.Screens;

import processing.core.PApplet;
import processing.core.PImage;

public class ColorChooserScreen extends Screen {

	Button graffitiRed, graffitiGreen, graffitiBlue;
	
	public ColorChooserScreen(AppMain p) {
		super(p);
		this.background = pApplet.loadImage("testBackgroundColor.png");
		
		PImage imgButton1e = pApplet.loadImage("buttons/button_graffiti_red_1.png");
		PImage imgButton1f = pApplet.loadImage("buttons/button_graffiti_red_2.png");
		PImage imgButton2e = pApplet.loadImage("buttons/button_graffiti_red_1.png");
		PImage imgButton2f = pApplet.loadImage("buttons/button_graffiti_red_2.png");
		PImage imgButton3e = pApplet.loadImage("buttons/button_graffiti_red_1.png");
		PImage imgButton3f = pApplet.loadImage("buttons/button_graffiti_red_2.png");
		adjustImageSize(imgButton1e, imgButton1f, imgButton2e, imgButton2f, imgButton3e, imgButton3f);

		this.graffitiRed = new Button(imgButton1e, imgButton1f, (int)(0.05*AppMain.frameWidth), (int)(0.80*AppMain.frameHeight), Button.LOADING_BOTTOM_TO_TOP, p);
		this.graffitiGreen = new Button(imgButton2e, imgButton2f, (int)(0.20*AppMain.frameWidth), (int)(0.80*AppMain.frameHeight), Button.LOADING_BOTTOM_TO_TOP, p);
		this.graffitiBlue = new Button(imgButton3e, imgButton3f, (int)(0.35*AppMain.frameWidth), (int)(0.80*AppMain.frameHeight), Button.LOADING_BOTTOM_TO_TOP, p);

	}

	@Override
	void draw(Point p) {
		pApplet.image(background, 0,0);
		
        if(graffitiRed.isPointOnButton(p)) {
            if(graffitiRed.hover(pApplet.millis())) { //ok, button is hovered, update overlay animation
                System.out.println("graffitiRed is clicked!!!");
                pApplet.setExpertMode(true);
                pApplet.setDrawColor(Color.RED);
                pApplet.setCurrentScreen(Screens.DRAWING);
                graffitiRed.release();
            }
        }
        else
        	graffitiRed.release();

		graffitiRed.draw();

        if(graffitiGreen.isPointOnButton(p)) {
            if(graffitiGreen.hover(pApplet.millis())) { //ok, button is hovered, update overlay animation
                System.out.println("graffitiGreen is clicked!!!");
                pApplet.setExpertMode(true);
                pApplet.setDrawColor(Color.GREEN);
                pApplet.setCurrentScreen(Screens.DRAWING);
                graffitiGreen.release();
            }
        }
        else
        	graffitiGreen.release();

		graffitiGreen.draw();
		
        if(graffitiBlue.isPointOnButton(p)) {
            if(graffitiBlue.hover(pApplet.millis())) { //ok, button is hovered, update overlay animation
                System.out.println("graffitiBlue is clicked!!!");
                pApplet.setExpertMode(true);
                pApplet.setDrawColor(Color.BLUE);
                pApplet.setCurrentScreen(Screens.DRAWING);
                graffitiBlue.release();
            }
        }
        else
        	graffitiBlue.release();

		graffitiBlue.draw();

	}

}
