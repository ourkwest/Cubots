package cubots.applet.phase.transition;

import java.awt.Graphics2D;

import cubots.applet.phase.Phase;

public class Pause extends Transition {

	public Pause(Phase on, long duration) {
		super(null, on, duration);
	}
	
	@Override
	public void render(Graphics2D g) {
		into.render(g);
	}
	
}
