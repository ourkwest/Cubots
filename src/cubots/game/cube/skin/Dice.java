package cubots.game.cube.skin;

import java.awt.Color;
import java.awt.Graphics2D;

public class Dice extends CubeSkin {
	
	public Dice() {
		super(Color.RED, Color.BLACK);
		
		Graphics2D g2d;
		
		g2d = getFaceGraphics(1);
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, RESOLUTION, RESOLUTION);
		g2d.setColor(Color.RED);
		g2d.fillArc(T3, T3, T4, T4, 0, 360);
		
		g2d = getFaceGraphics(2);
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, RESOLUTION, RESOLUTION);
		g2d.setColor(Color.BLACK);
		g2d.fillArc(T2, T4, T2, T2, 0, 360);
		g2d.fillArc(T6, T4, T2, T2, 0, 360);
		
		g2d = getFaceGraphics(3);
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, RESOLUTION, RESOLUTION);
		g2d.setColor(Color.BLACK);
		g2d.fillArc(T1, T4, T2, T2, 0, 360);
		g2d.fillArc(T4, T4, T2, T2, 0, 360);
		g2d.fillArc(T7, T4, T2, T2, 0, 360);
		
		g2d = getFaceGraphics(4);
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, RESOLUTION, RESOLUTION);
		g2d.setColor(Color.BLACK);
		g2d.fillArc(T2, T2, T2, T2, 0, 360);
		g2d.fillArc(T6, T2, T2, T2, 0, 360);
		g2d.fillArc(T2, T6, T2, T2, 0, 360);
		g2d.fillArc(T6, T6, T2, T2, 0, 360);
		
		g2d = getFaceGraphics(5);
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, RESOLUTION, RESOLUTION);
		g2d.setColor(Color.BLACK);
		g2d.fillArc(T2, T2, T2, T2, 0, 360);
		g2d.fillArc(T6, T2, T2, T2, 0, 360);
		g2d.fillArc(T2, T6, T2, T2, 0, 360);
		g2d.fillArc(T6, T6, T2, T2, 0, 360);
		g2d.fillArc(T4, T4, T2, T2, 0, 360);

		g2d = getFaceGraphics(6);
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, RESOLUTION, RESOLUTION);
		g2d.setColor(Color.BLACK);
		g2d.fillArc(T1, T2, T2, T2, 0, 360);
		g2d.fillArc(T4, T2, T2, T2, 0, 360);
		g2d.fillArc(T7, T2, T2, T2, 0, 360);
		g2d.fillArc(T1, T6, T2, T2, 0, 360);
		g2d.fillArc(T4, T6, T2, T2, 0, 360);
		g2d.fillArc(T7, T6, T2, T2, 0, 360);
	}

}
