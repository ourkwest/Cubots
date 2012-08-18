package cubots.game;

import java.awt.event.KeyEvent;

import cubots.applet.ImmediatePlayerKeyListener;
import cubots.game.player.DirectionProvider;

public class TwoPlayerGame extends Game {

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
		final ImmediatePlayerKeyListener player2 = new ImmediatePlayerKeyListener(
				KeyEvent.VK_A,
				KeyEvent.VK_W,
				KeyEvent.VK_D,
				KeyEvent.VK_S);
		addListener(player2);
		return player2;
	}

}
