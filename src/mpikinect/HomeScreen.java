package mpikinect;

import java.awt.Point;

import mpikinect.AppMain.Screens;

import processing.core.PApplet;
import processing.core.PImage;

public class HomeScreen extends Screen {

	Button button1;
	Button button2;
	
	public HomeScreen(AppMain p) {
		super(p);
		this.background = pApplet.loadImage("screen1_bg_640.png");
		
		PImage imgButton1e = pApplet.loadImage("buttons/button_1_1_empty.png");
		PImage imgButton1f = pApplet.loadImage("buttons/button_1_1_full.png");
		PImage imgButton2e = pApplet.loadImage("buttons/button_1_2_empty.png");
		PImage imgButton2f = pApplet.loadImage("buttons/button_1_2_full.png");
		this.button1 = new Button(imgButton1e, imgButton1f, 420, 250, Button.LOADING_LEFT_TO_RIGHT, p);
		this.button2 = new Button(imgButton2e, imgButton2f, 420, 320, Button.LOADING_LEFT_TO_RIGHT, p);

	}

	@Override
	void draw(Point p) {
		
		pApplet.image(background, 0,0);
		
		//process button1
		if(button1.isPointOnButton(p)) {
			if(button1.hover(pApplet.millis())) { //ok, button is hovered, update overlay animation
				System.out.println("Button1 is clicked!!!");
				pApplet.setExpertMode(true);
				pApplet.setCurrentScreen(Screens.TEMPLATE_CHOOSER);
				button1.release();
			}
		}
		else
			button1.release();

		button1.draw();
		
		
		//process button2
		if(button2.isPointOnButton(p)) {
			if(button2.hover(pApplet.millis())) { 
				System.out.println("Button2 is clicked!!!");
				pApplet.setExpertMode(false);
				pApplet.setCurrentScreen(Screens.TEMPLATE_CHOOSER);
				button2.release();
			}
		}
		else
			button2.release();

		button2.draw();

	}

}
