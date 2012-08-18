package cubots.game.cube;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import cubots.game.Viewport;
import cubots.game.cube.skin.CubeSkin;
import cubots.game.util.Point3D;
import cubots.game.util.TwoPoints;

public class CubeGeometry {
	
	public static final double RADIUS = 0.5;

	private static final double WEAPON_LENGTH = 8.0;
	private static final double PROXIMITY = (WEAPON_LENGTH * RADIUS);

	private final List<CubeFace> faces = new ArrayList<CubeFace>();
	private final List<Point3D> vertices = new ArrayList<Point3D>();
	
	private final Color weaponOutline;
	private final Color weaponHue;
	private final Double weaponHueRotation;
	
	public CubeGeometry(CubeSkin skin) {

		Point3D p0 = new Point3D( 0.0,  0.0,  0.0);

		Point3D p1 = new Point3D( RADIUS,  RADIUS,  RADIUS);
		Point3D p2 = new Point3D( RADIUS, -RADIUS,  RADIUS);
		Point3D p3 = new Point3D(-RADIUS, -RADIUS,  RADIUS);
		Point3D p4 = new Point3D(-RADIUS,  RADIUS,  RADIUS);
		Point3D p5 = new Point3D( RADIUS,  RADIUS, -RADIUS);
		Point3D p6 = new Point3D( RADIUS, -RADIUS, -RADIUS);
		Point3D p7 = new Point3D(-RADIUS, -RADIUS, -RADIUS);
		Point3D p8 = new Point3D(-RADIUS,  RADIUS, -RADIUS);

		Point3D p9 = new Point3D(0.0,  0.0, RADIUS);
		Point3D p10 = new Point3D(0.0,  0.0, WEAPON_LENGTH * RADIUS);

		vertices.add(p0);
		vertices.add(p1);
		vertices.add(p2);
		vertices.add(p3);
		vertices.add(p4);
		vertices.add(p5);
		vertices.add(p6);
		vertices.add(p7);
		vertices.add(p8);
		vertices.add(p9);
		vertices.add(p10);

		CubeFace f1 = new CubeFace(4, 1, 2, 3, skin.getFace(1), skin.getOutline()); // 1
		CubeFace f2 = new CubeFace(3, 2, 6, 7, skin.getFace(2), skin.getOutline()); // 2
		CubeFace f5 = new CubeFace(2, 1, 5, 6, skin.getFace(3), skin.getOutline()); // 3
		CubeFace f4 = new CubeFace(1, 4, 8, 5, skin.getFace(4), skin.getOutline()); // 5
		CubeFace f6 = new CubeFace(4, 3, 7, 8, skin.getFace(5), skin.getOutline()); // 4
		CubeFace f3 = new CubeFace(5, 8, 7, 6, skin.getFace(6), skin.getOutline()); // 6

		faces.add(f1);
		faces.add(f2);
		faces.add(f3);
		faces.add(f4);
		faces.add(f5);
		faces.add(f6);
		
		weaponOutline = skin.getWeaponOutline();
		weaponHueRotation = skin.getHueRotation();
		
		double split = Math.PI * 2.0 / 3.0;
		int red   = (int) Math.min(255, Math.max(0, 255.0 * (1.0 + Math.sin(weaponHueRotation))));
		int green = (int) Math.min(255, Math.max(0, 255.0 * (1.0 + Math.sin(weaponHueRotation - split))));
		int blue  = (int) Math.min(255, Math.max(0, 255.0 * (1.0 + Math.sin(weaponHueRotation + split))));
		weaponHue = new Color(red, green, blue);
		
	}

	public CubeGeometry(CubeGeometry toCopy) {
		for (CubeFace face : toCopy.faces) {
			faces.add(new CubeFace(face));
		}
		for (Point3D vertex : toCopy.vertices) {
			vertices.add(new Point3D(vertex));
		}
		this.weaponHueRotation = toCopy.getWeaponHueRotation();
		this.weaponOutline = toCopy.getWeaponOutline();
		this.weaponHue = toCopy.weaponHue;
		this.weaponTrailA = toCopy.weaponTrailA;
		this.weaponTrailB = toCopy.weaponTrailB;
	}

	public void render(Graphics2D g, Viewport viewport, int xLocationOffset, int yLocationOffset, Color sheen, Intersection intersection) {
		renderWeapon(g, viewport, xLocationOffset,  yLocationOffset, true, intersection);
		for (CubeFace face : faces) {
			face.render(g, viewport, vertices, xLocationOffset,  yLocationOffset, sheen);
		}
		renderWeapon(g, viewport, xLocationOffset,  yLocationOffset, false, intersection);
	}

	private void renderWeapon(Graphics2D g, Viewport viewport, int xLocationOffset, int yLocationOffset, boolean inFront, Intersection intersection) {

		Point3D p1 = new Point3D(vertices.get(9));
		Point3D p2 = new Point3D(vertices.get(10));
		Point3D p3 = new Point3D(0, 0, 0);
		
		if (intersection.isOccuring()) {
			final Point3D difference = new Point3D(p2);
			difference.subtract(p1);
			double prop = (intersection.getDistance() - RADIUS) / difference.magnitude();
			p2 = new Point3D(p1).add(difference.multiply(prop));
		}
		
		final boolean behind = viewport.proximity(p1) > viewport.proximity(p2);
		if (behind ^ inFront) {
			return;
		}
		
		p1.z += 0.5;
		p2.z += 0.5;
		p3.z += 0.5;
		
		if (p2.z > 0) {
			p3 = new Point3D(p2);
		}
		else {
			p3.x = intersectZeroAt(p1.x, p1.z, p2.x, p2.z);
			p3.y = intersectZeroAt(p1.y, p1.z, p2.y, p2.z);
			p3.z -= 0.5;
		}
		
		p1.z -= 0.5;
		p2.z -= 0.5;
		p3.z -= 0.5;

		Point v1 = viewport.transform(p1);
		Point v3 = viewport.transform(p3);
		
		v1.x += xLocationOffset;
		v1.y += yLocationOffset;
		v3.x += xLocationOffset;
		v3.y += yLocationOffset;
		
		final Color colour = this.getWeaponOutline();
		final Color colour2 = new Color(colour.getRed(), colour.getGreen(), colour.getBlue(), 100);
		
		g.setColor(colour2);
		g.setStroke(new BasicStroke(5));
		g.drawLine(v1.x, v1.y, v3.x, v3.y);
		g.setColor(colour);
		g.setStroke(new BasicStroke(1));
		g.drawLine(v1.x, v1.y, v3.x, v3.y);
		
		renderWeaponTrail(g, v1.x, v1.y, v3.x, v3.y, viewport);
		
	}
	
	List<TwoPoints> weaponTrailA = new ArrayList<TwoPoints>();
	List<TwoPoints> weaponTrailB = new ArrayList<TwoPoints>();

	private void renderWeaponTrail(Graphics2D g, int x1, int y1, int x2, int y2, Viewport viewport) {
		final int limit = 10;
		final TwoPoints newTwoPoints = new TwoPoints(x1, y1, x2, y2);
		TwoPoints lastTwoPoints = newTwoPoints;
		int opacity = 128;
		int opacity2 = 255;
		
		double colourRotation = this.getWeaponHueRotation();
		double split = Math.PI * 2.0 / 3.0;
	
		List<TwoPoints> weaponTrail = viewport.isInvert() ? weaponTrailA : weaponTrailB;
		
		for (TwoPoints twoPoints : weaponTrail) {
			
			int red = (int)Math.max(0, 100 + 155.0 * Math.sin(colourRotation));
			int green = (int)Math.max(0, 100 + 155.0 * Math.sin(colourRotation - split));
			int blue = (int)Math.max(0, 100 + 155.0 * Math.sin(colourRotation + split));
			colourRotation += 0.8;
			
			int[] xPoints = new int[]{
					twoPoints.a.x, 
					twoPoints.b.x, 
					lastTwoPoints.b.x, 
					lastTwoPoints.a.x};
			int[] yPoints = new int[]{
					twoPoints.a.y, 
					twoPoints.b.y, 
					lastTwoPoints.b.y, 
					lastTwoPoints.a.y};
			
			g.setColor(new Color(red, green, blue, opacity));
			opacity *= 0.8;
			g.fillPolygon(xPoints, yPoints, 4);
			
			g.setColor(new Color(
					this.getWeaponOutline().getRed(),
					this.getWeaponOutline().getGreen(),
					this.getWeaponOutline().getBlue(),
					opacity2));
			opacity2 *= 0.8;
			g.drawLine(
					twoPoints.b.x, 
					twoPoints.b.y, 
					lastTwoPoints.b.x, 
					lastTwoPoints.b.y);
			
			lastTwoPoints = twoPoints;
		}
		
		
		
		weaponTrail.add(0, newTwoPoints);
		if (weaponTrail.size() > limit) {
			weaponTrail.remove(limit);
		}
	}
	
	private double intersectZeroAt(double x1, double y1, double x2, double y2) {
		// Y = mX + c
		if (Math.abs(x2 - x1) < 0.001) { // div 0
			return x1;
		}
		double m = (y2 - y1) / (x2 - x1);
		double c = y1 - (m * x1);
		return -c / m;
	}

	public CubeGeometry apply(Transformation transformation, double proportion) {
		CubeGeometry transformed = new CubeGeometry(this);
		for (Point3D point : transformed.vertices) {
			transformation.apply(point, proportion);
		}
		return transformed;
	}

	public CubeGeometry apply(Rotation rotation, double proportion) {
		CubeGeometry transformed = new CubeGeometry(this);
		for (Point3D point : transformed.vertices) {
			rotation.apply(point, proportion);
		}
		return transformed;
	}

	public double getZOrder() {
		return vertices.get(0).x + vertices.get(0).y;
	}
	
	public Point3D getCentre() {
		return vertices.get(0);
	}

	public Intersection intersects(Point thisOffset, CubeGeometry otherGeometry, Point otherOffset) {
		
		Point3D start = new Point3D(this.vertices.get(9)).add(new Point3D(thisOffset.x, thisOffset.y, 0));
		Point3D end   = new Point3D(this.vertices.get(10)).add(new Point3D(thisOffset.x, thisOffset.y, 0));
		Point3D centre = new Point3D(otherGeometry.vertices.get(0)).add(new Point3D(otherOffset.x, otherOffset.y, 0));
		
		Point3D distanceToStart = new Point3D(start).subtract(centre);
		Point3D distanceToEnd   = new Point3D(end).subtract(centre);
		
		double totalDistance = distanceToStart.magnitude() + distanceToEnd.magnitude();
		return new Intersection(totalDistance < PROXIMITY, distanceToStart.magnitude());
	}

	public Color getWeaponOutline() {
		return weaponOutline;
	}

	public Double getWeaponHueRotation() {
		return weaponHueRotation;
	}
	
	public Color getWeaponHue() {
		return weaponHue;
	}
	
	public Orientation getOrientation() {
		return Orientation.getOrientation(vertices.get(9), vertices.get(10));
	}
}
