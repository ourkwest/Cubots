package cubots.applet.phase;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import cubots.applet.WhiteBoard;

public class Snapshot extends Phase {
	
	private BufferedImage img;
	private Phase phase;
	
	public Snapshot(Color colour) {
		img = WhiteBoard.createImage();
		Graphics g = img.getGraphics();
		g.setColor(colour);
		g.fillRect(0, 0, WhiteBoard.WIDTH, WhiteBoard.HEIGHT);
	}
	
	public Snapshot(Phase phase) {
		this.phase = phase;
	}
	
	@Override
	public Phase process() {
		if (img == null) {
			img = WhiteBoard.createImage();
			phase.render(img.createGraphics());
		}
		return super.process();
	}
	
	@Override
	public void render(Graphics2D g) {
		g.drawImage(img, 0, 0, null);
	}

}
