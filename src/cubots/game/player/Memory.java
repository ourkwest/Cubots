package cubots.game.player;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import cubots.game.cube.Orientation;

public class Memory {

	static int[][] positions = new int[][]{
			{ 0}, // <-- unused, but makes indexing nicer
			{ 1,  2}, 
			{ 3,  4,  5}, 
			{ 6,  7,  8,  9}, 
			{10, 11, 12, 13, 14}, 
			{15, 16, 17, 18, 19, 20}, 
	};
	static int totalPositions = 20;
	static int totalOrientations = Orientation.values().length;
	static int totalMemory = totalPositions * totalOrientations * totalOrientations;
	private static int N = 10;
	
	private double[] memories = new double[totalMemory + 1];
	private List<Integer> lastN = new ArrayList<Integer>();
	
	public void record(
			Point location1, Orientation orientation1,
			Point location2, Orientation orientation2) {

		lastN.add(0, lookup(location1, orientation1, location2, orientation2));
		if (lastN.size() > N) {
			lastN.remove(N);
		}
	}
	
	public double remind(
			Point location1, Orientation orientation1,
			Point location2, Orientation orientation2) {
		
		int slot = lookup(location1, orientation1, location2, orientation2);
		return memories[slot];
	}
	
	public void remember(double goodness) {
		for (Integer slot : lastN) {
			memories[slot] += goodness;
			goodness = goodness / 2.0;
		}
	}
	
	private int lookup(
			Point location1, Orientation orientation1,
			Point location2, Orientation orientation2) {
		
		// Position / 14
		// Orientation1 / 6
		// Orientation2 / 6
		
		int xDiff = location2.x - location1.x;
		int yDiff = location2.y - location1.y;
		Octant octant = new Octant(xDiff, yDiff, orientation1, orientation2);
		return octant.getNumber();
	}

}
