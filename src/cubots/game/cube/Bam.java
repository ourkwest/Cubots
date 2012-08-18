package cubots.game.cube;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import cubots.game.Viewport;
import cubots.game.util.Point3D;

public class Bam {

	int size = 10;
	private final Point3D location;
	
	private int red;
	private int green;
	private int blue;
	
	public Bam(Point3D location, Double hue) {
		this.location = location;
		
		double split = Math.PI * 2.0 / 3.0;
		red   = (int) Math.min(255, Math.max(0, 255.0 * (1.0 + Math.sin(hue))));
		green = (int) Math.min(255, Math.max(0, 255.0 * (1.0 + Math.sin(hue - split))));
		blue  = (int) Math.min(255, Math.max(0, 255.0 * (1.0 + Math.sin(hue + split))));
	}
	
	public void process() {
		size+=75;
	}
	
	public void render(Graphics2D g, Viewport viewport) {
		if (!viewport.isInvert()) {
			return;
		}
		
		int alpha = 255 - (size / 3);
		
		if (alpha <= 0) {
			return;
		}
		
		Point p = viewport.transform(location);
		
		int width = size;
		int height = (int) ((double)size * 0.7);
		int halfWidth = width / 2;
		int halfHeight = height / 2;
		
		g.setColor(new Color(red, green, blue, alpha)); 
		g.fillArc(p.x - halfWidth, p.y - halfHeight, width, height, 0, 360);
		
	}
	
	
	
}
