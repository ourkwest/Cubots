package cubots.game;

import java.awt.Point;

import cubots.game.cube.CubeGeometry;
import cubots.game.cube.Rotation;
import cubots.game.cube.Transformation;
import cubots.game.util.Point3D;
import cubots.game.util.UnitVector;

public enum Direction {
	
	North(UnitVector.PLUS_X, UnitVector.PLUS_Z, -1, 0), 
	South(UnitVector.MINUS_X, UnitVector.PLUS_Z, 1, 0), 
	East(UnitVector.PLUS_Y, UnitVector.PLUS_Z, 0, -1), 
	West(UnitVector.MINUS_Y, UnitVector.PLUS_Z, 0, 1),
	Inert(UnitVector.IDENTITY, UnitVector.IDENTITY, 0, 0);
	
	private final Transformation transformation;
	private final Rotation rotation;
	private final int dx;
	private final int dy;
	
	static final Point3D radiusOffset = 
		new Point3D(CubeGeometry.RADIUS, CubeGeometry.RADIUS, CubeGeometry.RADIUS);

	private Direction(UnitVector primary, UnitVector secondary, int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
		this.rotation = new Rotation(primary, secondary);
		Point3D offset = 
			new Point3D(CubeGeometry.RADIUS, CubeGeometry.RADIUS, CubeGeometry.RADIUS);
		primary.apply(offset, Math.abs(primary.extract(offset)));
		secondary.apply(offset, secondary.extract(offset));
		transformation = new Transformation(rotation, offset);
	}
	
	private Direction(Rotation rotation, int dx, int dy) {
		this.rotation = rotation;
		this.dx = dx;
		this.dy = dy;
		this.transformation = new Transformation(rotation, new Point3D(0, 0, 0));
	}

	public CubeGeometry getCurrent(CubeGeometry base, double proportion) {
		return base.apply(transformation, proportion);
	}
	
	public CubeGeometry getFinal(CubeGeometry base, double proportion) {
		return base.apply(rotation, proportion);
	}

	public void apply(Point location) {
		location.x += dx;
		location.y += dy;
	}
	
	public boolean cancelledBy(Direction other) {
		return (this.dx + other.dx == 0) && (this.dy + other.dy == 0);
	}
}
