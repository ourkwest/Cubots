package cubots.game;

import java.awt.event.KeyEvent;

import cubots.applet.ImmediatePlayerKeyListener;
import cubots.game.player.DirectionProvider;
import cubots.game.player.levels.Levels;

public class SinglePlayerGame extends Game {

	@Override
	protected DirectionProvider getPlayerOne() {
		final ImmediatePlayerKeyListener player1 = new ImmediatePlayerKeyListener(
				KeyEvent.VK_LEFT,
				KeyEvent.VK_UP,
				KeyEvent.VK_RIGHT,
				KeyEvent.VK_DOWN);
		addListener(player1);
		return player1;
	}

	@Override
	protected DirectionProvider getPlayerTwo() {
		return Levels.getDirectionProvider();
	}

}
