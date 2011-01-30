package mpikinect;

import hypermedia.video.Blob;

import java.awt.Point;
import java.awt.Rectangle;

public class Hand {

	private Blob blob;
	private float distance;
	private Point nearPoint;

	private float ratioW,ratioH;
	
	
	public Hand(Blob blob, Point nearPoint, float distance) {
		//note: blob positions were always calculated for 640x480 
		this.blob = blob;
		this.distance = distance;
		this.nearPoint = nearPoint;
		
		this.ratioW = AppMain.frameWidth / 640f;
		this.ratioH = AppMain.frameHeight / 480f;
	}
	
	public Point getCentroid() {
		//consider stretching factor
		float f = AppMain.detectionStretchFactor;
		float _x = blob.centroid.x*f - ((640f*f - 640f)/2f);
		float _y = blob.centroid.y*f - ((480f*f - 480f)/2f);
		
		//adjust point to frameSize;
		_x *= ratioW;
		_y *= ratioH;
		
		_x = AppMain.constrain(_x, 0, AppMain.frameWidth);
		_y = AppMain.constrain(_y, 0, AppMain.frameHeight);
		
		Point cent = new Point((int)(_x), (int)(_y));
		return cent;
	}
	
	public float getDistance() {
		return distance;
	}
	

	
}
