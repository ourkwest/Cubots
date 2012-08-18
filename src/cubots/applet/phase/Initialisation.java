package cubots.applet.phase;

import java.awt.Color;
import java.awt.Graphics2D;

import cubots.applet.WhiteBoard;
import cubots.applet.phase.transition.CrossFade;
import cubots.applet.phase.transition.Pause;
import cubots.applet.phase.transition.TransitionManager;
import cubots.applet.phase.transition.Wipe;
import cubots.game.player.levels.Levels;

public class Initialisation extends Phase {
	
	@Override
	public Phase process() {
		Phase one = new Snapshot(Color.BLACK);
		Phase two = new Snapshot(Levels.getScreen());
		Phase three = new GamePlay();
		return new TransitionManager(
				new Pause(one, 5),
				new CrossFade(one, two, 25),
				new Pause(two, 15),
				new Wipe(two, three, 15)
		); 
	}
	
	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WhiteBoard.WIDTH, WhiteBoard.HEIGHT);
	}

}
