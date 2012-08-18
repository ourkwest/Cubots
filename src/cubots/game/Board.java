package cubots.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.Stroke;

import cubots.game.cube.CubeGeometry;
import cubots.game.util.Point3D;

public class Board {

	private static final int SIZE = 13;
	private static final double RADIUS = SIZE/2 + CubeGeometry.RADIUS;
	private static final int INT_RADIUS = (int)(SIZE/2 + CubeGeometry.RADIUS);

	private Point3D corner1 = new Point3D( RADIUS,  RADIUS, 0.0); 
	private Point3D corner2 = new Point3D( RADIUS, -RADIUS, 0.0); 
	private Point3D corner3 = new Point3D(-RADIUS, -RADIUS, 0.0); 
	private Point3D corner4 = new Point3D(-RADIUS,  RADIUS, 0.0); 

	private static final double INSET = RADIUS - 0.5;
	private Point3D corner5 = new Point3D( INSET,  INSET, -1.0); 
	private Point3D corner6 = new Point3D( INSET, -INSET, -1.0); 
//	private Point3D corner7 = new Point3D(-INSET, -INSET, -1.0); 
	private Point3D corner8 = new Point3D(-INSET,  INSET, -1.0); 

	private boolean[][] occupied = new boolean[SIZE][SIZE];

	public void renderSurface(Graphics2D g, Viewport viewport) {
		Point p1 = viewport.transform(corner1);
		Point p2 = viewport.transform(corner2);
		Point p3 = viewport.transform(corner3);
		Point p4 = viewport.transform(corner4);
		Polygon topSurface = new Polygon(
				new int[]{p1.x, p2.x, p3.x, p4.x}, 
				new int[]{p1.y, p2.y, p3.y, p4.y}, 
				4);

		g.setColor(new Color(125, 125, 200, 200));
		g.fillPolygon(topSurface);
		g.setColor(new Color(0, 0, 0));
		g.drawPolygon(topSurface);
		
		int x1, y1, x2, y2;
		double xTravel1 = p2.x - p1.x;
		double yTravel1 = p2.y - p1.y;
		double xTravel2 = p2.x - p3.x;
		double yTravel2 = p2.y - p3.y;
		
		for (int i = 1; i <= SIZE-1; i++) {
			g.setColor(new Color(200, 200, 250, 50));
			double iFactor = (double)i / (double)SIZE; 
			x1 = p1.x + (int)(iFactor * xTravel1);
			y1 = p1.y + (int)(iFactor * yTravel1);
			x2 = p4.x + (int)(iFactor * xTravel1);
			y2 = p4.y + (int)(iFactor * yTravel1);
			g.drawLine(x1, y1, x2, y2);
			x1 = p4.x + (int)(iFactor * xTravel2);
			y1 = p4.y + (int)(iFactor * yTravel2);
			x2 = p3.x + (int)(iFactor * xTravel2);
			y2 = p3.y + (int)(iFactor * yTravel2);
			g.drawLine(x1, y1, x2, y2);
		}
	}

	public void renderBackdrop(Graphics2D g, Viewport viewport) {

		Point p1 = viewport.transform(corner1);
		Point p2 = viewport.transform(corner2);
		Point p3 = viewport.transform(corner3);
		Point p4 = viewport.transform(corner4);
		Point p5 = viewport.transform(corner5);
		Point p6 = viewport.transform(corner6);
//		Point p7 = viewport.transform(corner7);
		Point p8 = viewport.transform(corner8);

		Polygon topSurface = new Polygon(
				new int[]{p1.x, p2.x, p3.x, p4.x}, 
				new int[]{p1.y, p2.y, p3.y, p4.y}, 
				4);
		Polygon frontLeft = new Polygon(
				new int[]{p1.x, p2.x, p6.x, p5.x}, 
				new int[]{p1.y, p2.y, p6.y, p5.y}, 
				4);
		Polygon frontRight = new Polygon(
				new int[]{p1.x, p4.x, p8.x, p5.x}, 
				new int[]{p1.y, p4.y, p8.y, p5.y}, 
				4);
		Polygon outline = new Polygon(
				new int[]{p2.x, p3.x, p4.x, p8.x, p5.x, p6.x}, 
				new int[]{p2.y, p3.y, p4.y, p8.y, p5.y, p6.y}, 
				6);

		final Stroke stroke = g.getStroke();
		g.setColor(Color.GRAY);
		g.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g.drawPolygon(outline);
		g.setStroke(stroke);
		
		g.setColor(Color.DARK_GRAY);
		g.fillPolygon(topSurface);
		g.fillPolygon(frontLeft);
		g.fillPolygon(frontRight);
		
		g.setColor(new Color(125, 125, 200));
		g.fillPolygon(topSurface);
		g.setColor(new Color(75, 75, 100));
		g.fillPolygon(frontLeft);
		g.setColor(new Color(50, 50, 75));
		g.fillPolygon(frontRight);
		
		g.setColor(new Color(0,0,0));
		g.drawPolygon(topSurface);
		g.drawPolygon(frontLeft);
		g.drawPolygon(frontRight);
	}

	public boolean checkLocation(Point location) {
		return checkRequestLocation(location, false);
	}

	public boolean requestLocation(Point location) {
		return checkRequestLocation(location, true);
	}

	private boolean checkRequestLocation(Point location, boolean getLock) {
		int x = location.x + INT_RADIUS;
		int y = location.y + INT_RADIUS;
		if (x < 0 || y < 0 || x >= SIZE || y >= SIZE) {
			return false;
		}
		boolean free = !occupied[x][y];
		if (getLock) { occupied[x][y] = true; }
		return free;
	}

	public void freeLocation(Point location) {
		int x = location.x + INT_RADIUS;
		int y = location.y + INT_RADIUS;
		if (x > 0 || y > 0 || x < SIZE || y < SIZE) {
			occupied[x][y] = false;
		}
	}

	public static boolean onBoard(double x, double y) {
		double dx = x + INT_RADIUS;
		double dy = y + INT_RADIUS;
		return dx >= 0 && dy >= 0 && dx <= SIZE && dy <= SIZE;
	}

	public Shape getClip(Viewport viewport) {

		Point p1 = viewport.transform(corner1);
		Point p2 = viewport.transform(corner2);
		Point p3 = viewport.transform(corner3);
		Point p4 = viewport.transform(corner4);
		return new Polygon(
				new int[]{p1.x, p2.x, p3.x, p4.x}, 
				new int[]{p1.y, p2.y, p3.y, p4.y}, 
				4);

	}

}
