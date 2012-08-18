package cubots.game.cube;

import java.util.List;

import cubots.game.util.Point3D;

public class Vertex {
	
	private final int id;
	
	public Vertex(int id) {
		this.id = id;
	}
	
	public Point3D extract(List<Point3D> points) {
		return points.get(id);
	}
	
}
