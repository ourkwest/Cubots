package cubots.applet.phase.transition;

import java.awt.Graphics2D;

import cubots.applet.phase.Phase;

public abstract class Transition extends Phase {
	
	protected final Phase from;
	protected final Phase into;
	
	private long frames;
	private final long duration;

	public Transition(Phase from, Phase into, long duration) {
		this.from = from;
		this.into = into;
		this.duration = duration;
	}
	
	public Phase process() {
		frames++;
		into.process();
		return isOver() ? into : this;
	}
	
	protected double proportion() {
		return (double)frames / (double)duration;
	}
	
	public abstract void render(Graphics2D g);
	
	public boolean isOver() {
		return frames > duration;
	}
	
}
