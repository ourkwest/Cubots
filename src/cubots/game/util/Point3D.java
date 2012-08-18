package cubots.game.util;

public class Point3D {
	
	public double x, y, z;
	
	public Point3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Point3D(Point3D point) {
		this(point.x, point.y, point.z);
	}
	
	@Override
	public String toString() {
		return "(" + 
		String.format("%.3g", x) +
		", " + 
		String.format("%.3g", y) +
		", " + 
		String.format("%.3g", z) +
		")";
		
	}
	
	public Point3D add(Point3D other) {
		this.x += other.x;
		this.y += other.y;
		this.z += other.z;
		return this;
	}
	
	public Point3D subtract(Point3D other) {
		this.x -= other.x;
		this.y -= other.y;
		this.z -= other.z;
		return this;
	}
	
	public double magnitude() {
		return Math.sqrt(x * x + y * y + z * z);
	}
	
	public Point3D asUnitVector() {
		double m = magnitude(); 
		return new Point3D(x / m, y / m, z / m);
	}
	
	public static Point3D crossProduct(Point3D one, Point3D two) {
		double x = (one.y * two.z) - (one.z * two.y);
		double y = (one.z * two.x) - (one.x * two.z);
		double z = (one.x * two.y) - (one.y * two.x);
		return new Point3D(x, y, z); 
	}
	
	public static double dotProduct(Point3D one, Point3D two) {
		return ((one.x * two.x) + 
				(one.y * two.y) + 
				(one.z * two.z)); 
	}

	public Point3D multiply(double prop) {
		this.x *= prop;
		this.y *= prop;
		this.z *= prop;
		return this;
	}
	
}
