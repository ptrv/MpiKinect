package processingkinectdraw;

import java.awt.Point;

import processing.core.PImage;
import hypermedia.video.Blob;
import hypermedia.video.OpenCV;

public class HandDetector {
	
	private static final int 	rawDepthRangeMin = 400;
	private static final int 	rawDepthRangeMax = 1000;
	private static final int 	findNearestDistanceOffset = 4;
	private static final float 	depthThreshold = 0.04f;
	
	
	
	private int imgWidth, imgHeight;
	private OpenCV opencv;
	private int[] rawDepth;
	private PImage depthTh;
	
	// We'll use a lookup table so that we don't have to repeat the math over and over
	private float[] depthLookUp = new float[2048];

	public HandDetector(int imgWidth, int imgHeight) {
		this.imgWidth = imgWidth;
		this.imgHeight = imgHeight;
		
		depthTh = new PImage(imgWidth, imgHeight);
		
		opencv = new OpenCV();
		opencv.allocate(imgWidth,imgHeight);
		
		// Lookup table for all possible depth values (0 - 2047)
		for (int i = 0; i < depthLookUp.length; i++) {
			depthLookUp[i] = rawDepthToMeters(i);
		}
	}
	
	public void update(int[] rawDepth) {
		this.rawDepth = rawDepth;
	}
	
	public Hand detectHand() {
		if(rawDepth==null)
			return null;
		
		int nearestRawDepth = getNearestDistance();
		
		float nearestDist = depthLookUp[nearestRawDepth];
		
		Point nearPoint = null;
		
		float threshold_max = nearestDist * (1.0f + depthThreshold);
		float threshold_min = nearestDist * (1.0f - depthThreshold);
		for(int y=0; y<imgHeight; y++) {
			for(int x=0; x<imgWidth; x++) {
				int depth = rawDepth[y*imgWidth + x];
				float realDepth = depthLookUp[depth];
					
				if(realDepth <= threshold_max && realDepth >= threshold_min) {
					depthTh.set((imgWidth-1)-x, y, 0xffffff);
					if(realDepth <= nearestDist) {
						nearestDist = realDepth;
						nearPoint = new Point((imgWidth-1)-x,y);
					}

				}
					
				else
					depthTh.set((imgWidth-1)-x, y, 0x000000);

			}
		}

		opencv.copy(depthTh);

		// find blobs
		Blob[] blobs = opencv.blobs(50, imgWidth*imgHeight/2, 5, false);

		if(blobs.length==0 || blobs == null)
			return null;


		Blob handBlob = blobs[0];	 // take biggest blob
		
		
		Hand hand = new Hand(handBlob, nearPoint, nearestDist);
		
		return hand;
		
	}
	
	
	private int getNearestDistance() {

		// Get the raw depth as array of integers
		if(rawDepth==null)
			return -1;

		int nearestRawDepth = rawDepthRangeMax;
		for(int x=0; x<imgWidth; x+=findNearestDistanceOffset) {
			for(int y=0; y<imgHeight; y+=findNearestDistanceOffset) {
				int depth = rawDepth[x+y*imgWidth];
				//float realDepth = depthLookUp[depth];
//				if(realDepth < nearestRawDepth && realDepth > 0)
//					nearestRawDepth = depth;
				if(depth < nearestRawDepth && depth > rawDepthRangeMin)
					nearestRawDepth = depth;
			}
		}
		
		return nearestRawDepth;
	}
	
	/*
	private int getHighestBrightness(PImage depthImg) {

		int highestBrightness = 0;
		for(int x=0; x<imgWidth; x+=findNearestDistanceOffset) {
			for(int y=0; y<imgHeight; y+=findNearestDistanceOffset) {
				int brightness = depthImg.get(x, y);
				brightness = brightness & 0xff;
				if(brightness > highestBrightness)
					highestBrightness = brightness;
			}
		}
		
		return highestBrightness;
	}
	*/
	
	// These functions come from: http://graphics.stanford.edu/~mdfisher/Kinect.html
	private float rawDepthToMeters(int depthValue) {
	  if (depthValue < 2047) {
	    return (float)(1.0 / ((double)(depthValue) * -0.0030711016 + 3.3309495161));
	  }
	  return 0.0f;
	}
	
	
}
