package cubots.game.player;

import cubots.game.Direction;

public class Inert implements DirectionProvider {

	@Override
	public Direction getNext() {
		return Direction.Inert;
	}

}
