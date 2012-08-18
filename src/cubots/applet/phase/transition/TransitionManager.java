package cubots.applet.phase.transition;

import java.awt.Graphics2D;

import cubots.applet.phase.Phase;

public class TransitionManager extends Phase {
	
	private final Transition[] transitions;
	private int index;

	public TransitionManager(Transition...transitions) {
		this.transitions = transitions;
		this.index = 0; // not strictly necessary
	}
	
	@Override
	public Phase process() {
		final Phase result = transitions[index].process();
		if (transitions[index].isOver() && ++index == transitions.length) {
			return result;
		}
		return this;
	}
	
	
	@Override
	public void render(Graphics2D g) {
		transitions[index].render(g);
	}
	
}
