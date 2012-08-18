package cubots.applet.phase;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import cubots.applet.ImmediatePlayerKeyListener;
import cubots.applet.WhiteBoard;
import cubots.applet.phase.transition.CrossFade;
import cubots.applet.phase.transition.Pause;
import cubots.applet.phase.transition.TransitionManager;
import cubots.applet.phase.transition.Wipe;
import cubots.game.Game;
import cubots.game.SinglePlayerGame;
import cubots.game.TwoPlayerGame;
import cubots.game.player.levels.Levels;

public class GamePlay extends Phase {

	final Game game;
	ImmediatePlayerKeyListener playerListener;
	final KeyAdapter listener;
	boolean over = false;
	boolean returned = false;
	
	public GamePlay() {
		
		game = WhiteBoard.getTwoPlayer() ? new TwoPlayerGame() : new SinglePlayerGame();
		listener = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					over = true;
				}
			}
		};
		WhiteBoard.INSTANCE.addKeyListener(listener);
	}
	
	@Override
	public Phase process() {
		game.process();
		if (over && !returned) {
			returned = true;
			WhiteBoard.INSTANCE.removeKeyListener(playerListener);
			WhiteBoard.INSTANCE.removeKeyListener(listener);
			
			Phase one = this;
			Phase two = new Snapshot(Color.BLACK);
			Phase three = new GamePlay();
			
			return new TransitionManager(
					new CrossFade(one, two, 15),
					new Wipe(two, three, 15)
			); 
		}
		if (game.over() && !returned) {
			returned = true;
			WhiteBoard.INSTANCE.removeKeyListener(playerListener);
			WhiteBoard.INSTANCE.removeKeyListener(listener);
			
			Phase one = this;
			Phase two = new Snapshot(Levels.getScreen());
			Phase three = new GamePlay();
			
			return new TransitionManager(
					new Pause(one, 75),
					new CrossFade(one, two, 15),
					new Pause(two, 15),
					new Wipe(two, three, 15)
			); 
		}
		return this;
	}
	
	@Override
	public void render(Graphics2D g) {
		game.render(g);
	}
}
