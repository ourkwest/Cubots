package cubots.game.player;

import java.awt.Point;

import cubots.game.Board;
import cubots.game.Cube;
import cubots.game.Direction;
import cubots.game.Move;
import cubots.game.cube.Orientation;

public class MnemonicDirector implements DirectionProvider {

	private DirectionProvider backup = new RandomDirector();
	private Cube cube1;
	private Cube cube2;
	private Memory memory = new Memory();
	
	private Direction[] possibleDirections = new Direction[]{
			Direction.Inert,
			Direction.North,
			Direction.South,
			Direction.East,
			Direction.West,
	};
	private Board board;
	
	public void setBoard(Board board) {
		this.board = board;
	}
	
	public void setThisCube(Cube cube) {
		cube1 = cube;
	}
	
	public void setOtherCube(Cube cube) {
		cube2 = cube;
	}
	
	@Override
	public Direction getNext() {
		
		// Record
		Point location1 = cube1.getLocation();
		Orientation orientation1 = cube1.getOrientation();
		
		Point location2 = cube2.getLocation();
		Orientation orientation2 = cube2.getOrientation();
		
		memory.record(location1, orientation1, location2, orientation2);
		
		// Predict
		Point location3;
		Orientation orientation3;
		
		Point location4 = cube2.getNextLocation();
		Orientation orientation4 = cube2.getNextOrientation();
		
		double bestValue = -Double.MAX_VALUE;
		Direction bestDirection = backup.getNext();
		
		for (Direction direction : possibleDirections) {
			Move possibleMove = new Move(direction);
			location3 = new Point(location1); 
			possibleMove.apply(location3);
			if (direction != Direction.Inert && !board.checkLocation(location3)) {
				continue;
			}
			orientation3 = cube1.getPossibleOrientation(possibleMove);
			double thisValue = memory.remind(location3, orientation3, location4, orientation4);
			if (thisValue > bestValue) {
				bestValue = thisValue;
				bestDirection = direction;
			}
		}
		
		return bestDirection;
	}

	public void score(Cube cube) {
		if (cube1 == cube) {
			memory.remember(-1.0);
		}
		else {
			memory.remember(1.0);
		}
	}
	
}
