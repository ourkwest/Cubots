package cubots.game.player;

import java.util.HashMap;
import java.util.Map;

import cubots.game.cube.Orientation;

public class Octant {
	
	private int x, y;
	private Orientation one, two;
	
	public Octant(int xDiff, int yDiff, Orientation orientation1, Orientation orientation2) {
		x = xDiff;
		y = yDiff;
		one = orientation1;
		two = orientation2;
		rearrange();
	}

	private void rearrange() {
		if (x < -0.5) {
			one = Orientation.flip(one, Orientation.NS);
			two = Orientation.flip(two, Orientation.NS);
		}
		if (y < -0.5) {
			one = Orientation.flip(one, Orientation.EW);
			two = Orientation.flip(two, Orientation.EW);
		}
		x = Math.abs(x);
		y = Math.abs(y);
		if (x > y) {
			one = Orientation.flip(one, Orientation.XY);
			two = Orientation.flip(two, Orientation.XY);
			int temp = x;
			x = y;
			y = temp;
		}
	}
	
	static Map<Orientation, Integer> orientScore = new HashMap<Orientation, Integer>();
	static {
		orientScore.put(Orientation.Northward,   0);
		orientScore.put(Orientation.Eastward,  -10);
		orientScore.put(Orientation.Westward,   10);
		orientScore.put(Orientation.Upward,      0);
		orientScore.put(Orientation.Downward,    0);
		orientScore.put(Orientation.Southward,   0);
	}
	
	public int getScore() {
		int orientationScore = orientScore.get(one) + orientScore.get(two);
		return -1 * (Math.abs(2 - y) + Math.abs(0 - x)) + orientationScore;
	}

	public int getNumber() {
		try {
			return getNumber0();
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println(x + " : " + y);
		}
		return Memory.totalMemory;
	}
	
	public int getNumber0() {
		
		if (y >= Memory.positions.length) {
			return Memory.totalMemory;
		}
		
		// one & two & position;
		int position = Memory.positions[y][x];
		int orientation1 = one.ordinal();
		int orientation2 = two.ordinal();
		int memorySlot = 
			position +
			Memory.totalPositions * orientation1 +
			Memory.totalPositions * Memory.totalOrientations * orientation2;
		return memorySlot;
	}
}