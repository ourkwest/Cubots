package cubots.game.cube;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import cubots.game.Board;
import cubots.game.Viewport;
import cubots.game.util.Point3D;

public class Spark {
	
	double x, y, z;
	double dx, dy, dz;
	float brightness = 1.0f;
	double db;
	private final Double hue;
	
	public Spark(Point3D location, Double hue) {
		this(location, hue, false);
	}
	
	public Spark(Point3D location, Double hue, boolean extraBig) {
		
//		dy = (Math.random() - 0.5) * 0.3;
		double extraBigFactor = extraBig ? 5.0 : 1.0;
		
		x = location.x + (Math.random() - 0.5) * 0.5;
		y = location.y + (Math.random() - 0.5) * 0.5;
		z = location.z + (Math.random() - 0.5) * 0.5;
		double thetaH = Math.random() * 2.0 * Math.PI;
		double thetaV = Math.random() * 2.0 * Math.PI;
		double hypotenuse = 0.05 + Math.random() * 0.1 * extraBigFactor;
		dx = Math.sin(thetaH) * hypotenuse;
		dy = Math.cos(thetaH) * hypotenuse;
		dz = Math.sin(thetaV) * hypotenuse;
		db = (2.99 + Math.random()) / 4.0;
		this.hue = hue;
	}

	public void process() {
		x += dx;
		y += dy;
		z += dz;
		dz -= 0.05;
		
		if (Board.onBoard(x + 0.5, y + 0.5)) {
			dx *= 0.99;
			dy *= 0.99;
			dz *= 0.75;//0.85;
			if (z < 0) {
				dz = Math.abs(dz);
			}
		}
		brightness *= db; //0.98;
	}

	public void render(Graphics2D g, Viewport viewport, boolean infront) {
		
		if (z > 0 ^ infront) {
			return;
		}
		
		double split = Math.PI * 2.0 / 3.0;
		double f = 1.5;
		int red   = (int) Math.min(255, Math.max(0, f * 255.0 * (double)brightness * (1.0 + Math.sin(hue))));
		int green = (int) Math.min(255, Math.max(0, f * 255.0 * (double)brightness * (1.0 + Math.sin(hue - split))));
		int blue  = (int) Math.min(255, Math.max(0, f * 255.0 * (double)brightness * (1.0 + Math.sin(hue + split))));
		g.setColor(new Color(red, green, blue, (int)(255.0 * brightness)));
		
		Point a = viewport.transform(x, y, z);
		Point b = viewport.transform(x - dx, y - dy, z- dz);
		g.drawLine(a.x, a. y, b.x, b.y);
	}
	
	public boolean stillAlive() {
		return brightness > 0.1;
	}
	
}
