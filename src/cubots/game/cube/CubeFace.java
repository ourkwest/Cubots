package cubots.game.cube;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.util.List;

import cubots.game.Viewport;
import cubots.game.cube.skin.CubeSkin;
import cubots.game.util.Point3D;

public class CubeFace {
	
	private Vertex a, b, c, d;
	
	private static final int RESOLUTION = CubeSkin.RESOLUTION;
	private BufferedImage image;
	private Color colour;
	
	public CubeFace(int a, int b, int c, int d, BufferedImage image, Color colour) {
		this.a = new Vertex(a);
		this.b = new Vertex(b);
		this.c = new Vertex(c);
		this.d = new Vertex(d);
		this.image = image.getSubimage(0, 0, RESOLUTION, RESOLUTION);
		this.colour = colour;
	}

	public CubeFace(CubeFace face) {
		this.a = face.a;
		this.b = face.b;
		this.c = face.c;
		this.d = face.d;
		this.image = face.image;
		this.colour = face.colour;
	}
	
	public void render(Graphics2D g, Viewport viewport, List<Point3D> points, int xLocationOffset, int yLocationOffset, Color sheen) {
		
		Point p1 = viewport.transform(a.extract(points));
		Point p2 = viewport.transform(b.extract(points));
		Point p3 = viewport.transform(c.extract(points));
		Point p4 = viewport.transform(d.extract(points));
		
		Point3D crossProduct = Point3D.crossProduct(
				new Point3D(p1.x - p2.x, p1.y - p2.y, 0), 
				new Point3D(p1.x - p4.x, p1.y - p4.y, 0));
		
		if (crossProduct.z < 0 ^ viewport.isInvert()) {
			return;
		}
		
		Color shade;
		if (sheen == null) {
			shade = calculateShade(points);
		}
		else {
			shade = new Color(sheen.getRed(), sheen.getGreen(), sheen.getBlue(), 175);
		}
		
		g.translate(xLocationOffset, yLocationOffset);
		renderBackdrop(g, p1, p2, p3, p4);
		renderShading(g, shade, p1, p2, p3, p4);
		renderTexture(g,  p1, p2, p3, p4, viewport);
		renderShading(g, shade, p1, p2, p3, p4);
		renderWireFrame(g, p1, p2, p3, p4);
		g.translate(-xLocationOffset, -yLocationOffset);
		
	}

	private void renderTexture(Graphics2D g, Point p1, Point p2, Point p3, Point p4, Viewport viewport) {
		// http://en.wikipedia.org/wiki/Matrix_(mathematics)
		final double sideScale = RESOLUTION;
		// a => v1.x
		double m00 = (p2.x - p1.x) / sideScale;
		// b => v1.y
		double m10 = (p2.y - p1.y) / sideScale;
		// c => v2.x
		double m01 = (p4.x - p1.x) / sideScale;
		// d => v2.y
		double m11 = (p4.y - p1.y) / sideScale;
		// x location
		double m02 = p1.x;
		// y location
		double m12 = p1.y;
		AffineTransform xform = new AffineTransform(
				m00,m10,
				m01,m11,
				m02,m12);
		
		Shape oldClip = g.getClip();
		Shape faceClip = new Polygon(new int[]{p1.x, p2.x, p3.x, p4.x}, new int[]{p1.y, p2.y, p3.y, p4.y}, 4);
		if (viewport.isInvert()) {
			Area area = new Area(faceClip);
			area.intersect(new Area(oldClip));
			faceClip = area;
		}
		g.setClip(faceClip);
		g.drawImage(image, xform, null);
		g.setClip(oldClip);
	}

	private void renderWireFrame(Graphics2D g, Point p1, Point p2, Point p3, Point p4) {
		g.setColor(colour);
		g.drawPolygon(new int[]{p1.x, p2.x, p3.x, p4.x}, new int[]{p1.y, p2.y, p3.y, p4.y}, 4);
	}

	private void renderShading(Graphics2D g, Color shade,
			Point p1, Point p2,
			Point p3, Point p4) {
		g.setColor(shade);
		g.fillPolygon(new int[]{p1.x, p2.x, p3.x, p4.x}, new int[]{p1.y, p2.y, p3.y, p4.y}, 4);
	}

	private Color calculateShade(List<Point3D> points) {
		Point3D v1 = a.extract(points);
		Point3D v2 = b.extract(points);
		Point3D v3 = d.extract(points);
		Point3D normal = Point3D.crossProduct(
				new Point3D(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z), 
				new Point3D(v1.x - v3.x, v1.y - v3.y, v1.z - v3.z));
		Point3D lighting = new Point3D(5, 10, 7);
		double light = Math.abs(Point3D.dotProduct(normal.asUnitVector(), lighting.asUnitVector()));
		Color shade = new Color(0.0f, 0.0f, 0.0f, 0.5f - (float) light/2.0f);
		return shade;
	}

	private void renderBackdrop(Graphics2D g, Point p1, Point p2, Point p3, Point p4) {
		g.setColor(colour);
		g.fillPolygon(new int[]{p1.x, p2.x, p3.x, p4.x}, new int[]{p1.y, p2.y, p3.y, p4.y}, 4);
	}

}
