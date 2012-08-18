package cubots.applet.phase.transition;

import java.awt.Graphics2D;

import cubots.applet.WhiteBoard;
import cubots.applet.phase.Phase;

public class Wipe extends Transition {

	private final Direction direction;

	public Wipe(Phase from, Phase into, int duration) {
		super(from, into, duration);
		this.direction = Direction.values()[(int) (Math.random() * Direction.values().length)];
	}

	@Override
	public void render(Graphics2D g) {
		int xOffset = (int) (WhiteBoard.WIDTH  * proportion());
		int yOffset = (int) (WhiteBoard.HEIGHT * proportion());
		
		switch (direction) {
		case Right:
			g.translate(xOffset, 0);
			from.render(g);
			g.translate(-WhiteBoard.WIDTH, 0);
			into.render(g);
			g.translate(WhiteBoard.WIDTH, 0);
			g.translate(-xOffset, 0);
			break;
		case Left:
			g.translate(-xOffset, 0);
			from.render(g);
			g.translate(WhiteBoard.WIDTH, 0);
			into.render(g);
			g.translate(-WhiteBoard.WIDTH, 0);
			g.translate(xOffset, 0);
			break;
		case Up:
			g.translate(0, yOffset);
			from.render(g);
			g.translate(0, -WhiteBoard.HEIGHT);
			into.render(g);
			g.translate(0, WhiteBoard.HEIGHT);
			g.translate(0, -yOffset);
			break;
		case Down:
			g.translate(0, -yOffset);
			from.render(g);
			g.translate(0, WhiteBoard.HEIGHT);
			into.render(g);
			g.translate(0, -WhiteBoard.HEIGHT);
			g.translate(0, yOffset);
			break;
		}
	}

	public static enum Direction {
		Up, Down, Left, Right;
	}
	
}
