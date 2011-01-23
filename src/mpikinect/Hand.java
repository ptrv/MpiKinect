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
		//adjust point to frameSize;
		Point cent = new Point((int)(blob.centroid.x*ratioW), (int)(blob.centroid.y*ratioH));
		return cent;
	}
	public float getDistance() {
		return distance;
	}
	public Rectangle getBoundingBox() {
		//adjust rectangle to frameSize;
		Rectangle bb = new Rectangle((int)(blob.rectangle.x*ratioW), (int)(blob.rectangle.y*ratioH), (int)(blob.rectangle.width*ratioW), (int)(blob.rectangle.height*ratioH));
		return bb;
	}
	public Point getNearestPoint() {
		//adjust point to frameSize;
		Point p = new Point((int)(nearPoint.x*ratioW), (int)(nearPoint.y*ratioH));
		return p;
	}
	
}
