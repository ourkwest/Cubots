package cubots.applet.phase.transition;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import cubots.applet.WhiteBoard;
import cubots.applet.phase.Phase;
import cubots.applet.phase.Snapshot;

public class CrossFade extends Transition {

	public CrossFade(Phase from, Phase into, long duration) {
		super(new Snapshot(from), into, duration);
	}
	
	@Override
	public void render(Graphics2D g) {
		from.render(g);
		BufferedImage img = WhiteBoard.createImage();
		Graphics2D g2 = img.createGraphics();
		into.render(g2);
		int[] iArray = new int[WhiteBoard.WIDTH * WhiteBoard.HEIGHT];
		for (int i = 0; i < WhiteBoard.WIDTH * WhiteBoard.HEIGHT; i++) {
			iArray[i] = (int)(255.0 * proportion());
		}
		img.getAlphaRaster().setPixels(0, 0, WhiteBoard.WIDTH, WhiteBoard.HEIGHT, iArray);
		g.drawImage(img, 0, 0, null);
	}

}
