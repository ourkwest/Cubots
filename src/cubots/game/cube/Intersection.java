package cubots.game.cube;

public class Intersection {
	
	private final boolean occuring;
	private final double distance;

	public Intersection(boolean occuring, double distance) {
		this.occuring = occuring;
		this.distance = distance;
	}
	
	public boolean isOccuring() {
		return occuring;
	}
	
	public double getDistance() {
		return distance;
	}
	
}
