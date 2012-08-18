package cubots.game.util;

public class RollingAverage {

	
	double average;
	private final double factor;
	private final double inverseFactor;
	
	
	public RollingAverage(double initial, double factor) {
		this.average = initial;
		this.factor = factor;
		this.inverseFactor = 1.0 - factor;
	}
	
	public void addNext(double value) {
		average = (average * factor) + (value * inverseFactor);
	}
	
	public double getCurrent() {
		return average;
	}
	
}
