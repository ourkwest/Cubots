package cubots.game.player;

import cubots.game.Direction;

public class RandomDirector implements DirectionProvider {
	
	@Override
	public Direction getNext() {
		Direction[] options = Direction.values();
		int i = (int)(Math.random() * options.length);
		return options[i];
	}
	
}
