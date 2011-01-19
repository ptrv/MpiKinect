package processingkinectdraw;

import hypermedia.video.Blob;

import java.awt.Point;
import java.awt.Rectangle;

public class Hand {

	private Blob blob;
	private float distance;
	private Point nearPoint;
	
	public Hand(Blob blob, Point nearPoint, float distance) {
		this.blob = blob;
		this.distance = distance;
		this.nearPoint = nearPoint;
	}
	
	public Point getCentroid() {
		return blob.centroid;
	}
	public float getDistance() {
		return distance;
	}
	public Rectangle getBoundingBox() {
		return blob.rectangle;
	}
	public Point getNearestPoint() {
		return nearPoint;
	}
}
