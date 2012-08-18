package cubots.game.util;

public enum UnitVector {
	
	PLUS_X(  1, 0, 0),
	PLUS_Y(  0, 1, 0),
	PLUS_Z(  0, 0, 1),
	MINUS_X(-1, 0, 0),
	MINUS_Y( 0,-1, 0),
	MINUS_Z( 0, 0,-1),
	IDENTITY(0, 0, 0);
	
	double x, y, z;
	double xI, yI, zI;
	
	private UnitVector(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.xI = (x == 0 ? 1 : 0);
		this.yI = (y == 0 ? 1 : 0);
		this.zI = (z == 0 ? 1 : 0);
	}
	
	public double extract(Point3D point) {
		return 
		x * point.x + 
		y * point.y + 
		z * point.z;
	}

	public void apply(Point3D point, double value) {
		point.x = point.x * xI + value * x;
		point.y = point.y * yI + value * y;
		point.z = point.z * zI + value * z;
	}

}
