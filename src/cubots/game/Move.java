package cubots.game;

import java.awt.Point;

import cubots.game.cube.CubeGeometry;

public class Move {
	
	private static final double INIT = 0.0; 
	private static final double LIMIT = 1.0; 
	private static final double STEP = 0.1;
	
	double proportion;
	Direction direction;
	
//	CubeGeometry base;
//	CubeGeometry current;
//	CubeGeometry target;

	private boolean depleted = false;
	
	public Move(Direction direction) {
		this.direction = direction;
		this.proportion = INIT;
	}
	
	public void progress() {
		if (depleted) {
			return;
		}
		proportion += STEP;
		if (proportion > LIMIT) {
			proportion = LIMIT;
			depleted = true;
		}
	}
	
	public boolean isDepleted() {
		return direction == Direction.Inert || depleted;
	}
	
	public CubeGeometry getTarget(CubeGeometry base) {
		return direction.getCurrent(base, LIMIT);
	}
	
	public CubeGeometry getCurrent(CubeGeometry base) {
		return direction.getCurrent(base, proportion);
	}
	
	public CubeGeometry getFinal(CubeGeometry base) {
		return direction.getFinal(base, LIMIT);
	}
	
	public void apply(Point location) {
		direction.apply(location);
	}
	
}
