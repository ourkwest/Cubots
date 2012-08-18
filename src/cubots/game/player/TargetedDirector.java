package cubots.game.player;

import java.awt.Point;

import cubots.game.Board;
import cubots.game.Cube;
import cubots.game.Direction;
import cubots.game.Move;
import cubots.game.cube.Orientation;

public class TargetedDirector implements DirectionProvider {

	private Cube cube1;
	private Cube cube2;
	
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
		
		Point location1;
		Orientation orientation1;

		Point location2 = cube2.getNextLocation();
		Orientation orientation2 = cube2.getNextOrientation();
		
		int bestValue = -Integer.MAX_VALUE;
		Direction bestDirection = Direction.Inert;
		for (Direction direction : possibleDirections) {
			Move possibleMove = new Move(direction);
			location1 = new Point(cube1.getLocation()); 
			possibleMove.apply(location1);
			if (direction != Direction.Inert && !board.checkLocation(location1)) {
				continue;
			}
			orientation1 = cube1.getPossibleOrientation(possibleMove);
			int thisValue = score(location1, orientation1, location2, orientation2);
			if (thisValue > bestValue) {
				bestValue = thisValue;
				bestDirection = direction;
			}
		}
		return bestDirection;
	}
	
	private int score(
			Point location1, Orientation orientation1,
			Point location2, Orientation orientation2) {
		
		int xDiff = location2.x - location1.x;
		int yDiff = location2.y - location1.y;
		Octant octant = new Octant(xDiff, yDiff, orientation1, orientation2);
		return octant.getScore();
	}

}
