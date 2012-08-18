package cubots.game;

import java.awt.Color;
import java.awt.Graphics2D;

import cubots.applet.CubotApplet;
import cubots.game.util.GameFont;

public class ScoreBoard {

	private final Cube cube1;
	private final Cube cube2;
	private boolean over;
	
	public ScoreBoard(Cube cube1, Cube cube2) {
		this.cube1 = cube1;
		this.cube2 = cube2;
	}

	public void render(Graphics2D g) {
	
		final int totalWidth = CubotApplet.DRAWABLE_WIDTH;
		
		int x = 20, y = 20;
		int width = totalWidth/2 - 40;
		int healthWidth = (int) ((double)width * cube1.health());
		
		g.setColor(Color.BLACK);
		g.fillRect(x, y, width, 5);
		g.setColor(cube1.geometry.getWeaponHue());
		g.fillRect(x, y, (int) ((double)width * cube1.health()), 5);
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, 5);

		healthWidth = (int) ((double)width * cube2.health());
		x = totalWidth/2 + 20;
		
		g.setColor(Color.BLACK);
		g.fillRect(x, y, width, 5);
		g.setColor(cube2.geometry.getWeaponHue());
		g.fillRect(x + width - healthWidth, y, healthWidth, 5);
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, 5);
		
		GameFont effect = new GameFont(20, 3, Color.YELLOW, Color.BLACK); 
		effect.setTop(50);
		effect.setLeft(50);
		effect.render(g, "Health: " + (int)(100.0 * cube1.health()));
		effect.setRight(50);
		effect.render(g, "Health: " + (int)(100.0 * cube2.health()));
		
	}

	public void score(int i, Cube cube) {
		
	}

	public boolean isOver() {
		return over || (!(cube1.alive() && cube2.alive()));
	}

	public void setOver() {
		over = true;
	}

}
