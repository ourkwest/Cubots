package cubots.applet;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import cubots.game.Cube;
import cubots.game.Direction;
import cubots.game.player.DirectionProvider;

public class ImmediatePlayerKeyListener extends KeyAdapter implements
		DirectionProvider {

	private static final Integer NONE  = -1;
	
	public static final Integer LEFT  = 37;
	public static final Integer UP    = 38;
	public static final Integer RIGHT = 39;
	public static final Integer DOWN  = 40;
	
	HashMap<Integer, Direction> directions;
	
	public ImmediatePlayerKeyListener(int left, int up, int right, int down) {
		directions = new HashMap<Integer, Direction>();
		directions.put(NONE,  Direction.Inert);
		directions.put(left,  Direction.East);  // 37 = Left  (East  = Up + Left)
		directions.put(up,    Direction.North); // 38 = Up    (North = Up + Right)
		directions.put(right, Direction.West);  // 39 = Right (West  = Down + Right)
		directions.put(down,  Direction.South); // 40 = Down  (South = Down + Left)
	}
	
	private Integer lastPressed = NONE;
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (directions.keySet().contains(e.getKeyCode())) {
			lastPressed = e.getKeyCode();
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		if (lastPressed == e.getKeyCode()) {
			lastPressed = NONE;
		}
	}
	
	@Override
	public Direction getNext() {
		return directions.get(lastPressed);
	}

}
