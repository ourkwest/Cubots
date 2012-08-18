package cubots.game;

import java.awt.Point;

import cubots.applet.CubotApplet;
import cubots.game.util.Point3D;

public class Viewport {
	
	private final double zFactor;

	double scale = CubotApplet.DRAWABLE_WIDTH / 24; //25;  // 15 - 600 : 25 - 800
	int xOffset = CubotApplet.DRAWABLE_WIDTH / 2;
	int yOffset = CubotApplet.DRAWABLE_HEIGHT / 2 + 30;
	
	double isometricFactor0 = 0.0; 
	double isometricFactor1 = 1.0; 
	double isometricFactor2 = 0.5; 
	double isometricFactor3 = Math.sqrt(3.0) * 0.5; 
	
	double xToU = -isometricFactor3;
	double xToV =  isometricFactor2;
	double yToU =  isometricFactor3;
	double yToV =  isometricFactor2;
	double zToU =  isometricFactor0;
	double zToV = -isometricFactor1;

	private final boolean invert;
	
	public Viewport(boolean invert) {
		this.invert = invert;
		zFactor = (invert ? -1.0 : 1.0);
//		xToV *= zFactor;
//		yToV *= zFactor;
		zToV *= zFactor;
	}
	
	public boolean isInvert() {
		return invert;
	}
	
	public Point transform(Point3D point) {
		return transform(point.x, point.y, point.z);
	}
	
	public Point transform(double x, double y, double z) {
		
		
//		final double d = 0.001;
//		xToU += (Math.random() - 0.5) * d;
//		xToV += (Math.random() - 0.5) * d;
//		yToU += (Math.random() - 0.5) * d;
//		yToV += (Math.random() - 0.5) * d;
//		zToU += (Math.random() - 0.5) * d;
//		zToV += (Math.random() - 0.5) * d;
		
	
		double uIso = (x * xToU) + (y * yToU) + (z * zToU);
		double vIso = (x * xToV) + (y * yToV) + (z * zToV);
		
		int uDraw = xOffset + (int)(scale * uIso);
		int vDraw = yOffset + (int)(scale * vIso);
		
		return new Point(uDraw, vDraw);
	}

	public double proximity(Point3D point) {
		return point.x + point.y + (invert ? -point.z : point.z);
	}
}
