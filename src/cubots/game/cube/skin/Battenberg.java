package cubots.game.cube.skin;

import java.awt.Color;
import java.awt.Graphics2D;

public class Battenberg extends CubeSkin {
	
	
	public Battenberg() {
		super(Color.PINK, Color.PINK);
		
		Graphics2D g2d;
		
		g2d = getFaceGraphics(1);
		drawEnd(g2d);
		
		g2d = getFaceGraphics(2);
		drawSide(g2d);
		
		setFace(3, 2);
		setFace(4, 2);
		setFace(5, 2);
		setFace(6, 1);
	}

	private void drawSide(Graphics2D g2d) {
		for (int i = 0; i < RESOLUTION; i++) {
			for (int j = 0; j < RESOLUTION; j++) {
				int l = (int) (127.0 + 127.0 * Math.random());
				g2d.setColor(new Color(l, l, l));
				g2d.drawLine(i, j, i, j);
			}
		}
		g2d.setColor(new Color(255, 127, 127, 200));
		g2d.fillRect(T0, T0, T10, T10);
	}

	private void drawEnd(Graphics2D g2d) {
		for (int i = 0; i < RESOLUTION; i++) {
			for (int j = 0; j < RESOLUTION; j++) {
				int l = (int) (127.0 + 127.0 * Math.random());
				g2d.setColor(new Color(l, l, l));
				g2d.drawLine(i, j, i, j);
			}
		}
		g2d.setColor(new Color(255, 127, 127, 200));
		g2d.fillRect(T0, T0, T1, T10);
		g2d.fillRect(T0, T0, T10, T1);
		g2d.fillRect(T9, T1, T1, T10);
		g2d.fillRect(T1, T9, T10, T1);
		g2d.setColor(new Color(255, 127, 127, 127));
		g2d.fillRect(T1, T1, T4, T4);
		g2d.fillRect(T5, T5, T4, T4);
		g2d.setColor(new Color(255, 255, 0, 127));
		g2d.fillRect(T5, T1, T4, T4);
		g2d.fillRect(T1, T5, T4, T4);
	}

}
