package cubots.game.cube;

import java.util.HashMap;

import cubots.game.util.Point3D;

public enum Orientation {

	Upward,
	Downward,
	Northward,
	Southward,
	Eastward,
	Westward;
	
	public static final Flip NS = new Flip(); 
	public static final Flip EW = new Flip(); 
	public static final Flip XY = new Flip();
	
	static {
		NS.register(Northward, Southward);
		NS.register(Southward, Northward);
		EW.register(Eastward, Westward);
		EW.register(Westward, Eastward);
		XY.register(Westward, Southward);
		XY.register(Southward, Westward);
		XY.register(Eastward, Northward);
		XY.register(Northward, Eastward);
	}
	
	public static Orientation getOrientation(Point3D near, Point3D far) {
		
		if (far.z - near.z > 1) {
			return Upward; // +z
		}
		if (far.z - near.z < -1) {
			return Downward; // -z
		}
		if (far.y - near.y > 1) {
			return Westward; // +y
		}
		if (far.y - near.y < -1) {
			return Eastward; // -y
		}
		if (far.x - near.x > 1) {
			return Southward; // +x
		}
		if (far.x - near.x < -1) {
			return Northward; // -x
		}
		throw new NonSensicalOrientationError();
	}
	
	public static Orientation flip(Orientation orientation, Flip flip) {
		return flip.lookup(orientation);
	}
	
	public static class NonSensicalOrientationError extends RuntimeException {
		private static final long serialVersionUID = 1L;
	}
	
	private static class Flip {
		private HashMap<Orientation, Orientation> map = new HashMap<Orientation, Orientation>();
		protected void register(Orientation from, Orientation onto) {
			map.put(from, onto);
		}
		protected Orientation lookup(Orientation from) {
			if (map.containsKey(from)) {
				return map.get(from);
			}
			return from;
		}
	}
	
}
