package cubots.game.cube;

import cubots.game.util.Point3D;

public class Transformation {
	
	private final Rotation rotation;
	private final Point3D offset;

	public Transformation(Rotation rotation, Point3D offset) {
		this.rotation = rotation;
		this.offset = offset;
	}
	
	public void apply(Point3D point, double proportion) {
		point.add(offset);
		rotation.apply(point, proportion);
		point.subtract(offset);
	}

}
