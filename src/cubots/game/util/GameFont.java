package cubots.game.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

import cubots.applet.CubotApplet;

public class GameFont {
	
	Map<Character, Image> map = new HashMap<Character, Image>();
	private final int size;
	private final Color main;
	private final Color outline;
	private final int full;
	
	private boolean top, bottom;
	private boolean left, right;
	private int x, y;
	
	public GameFont(int size, int full, Color main, Color outline) {
		this.size = size;
		this.full = full;
		this.main = main;
		this.outline = outline;
	}
	
	public void setTop(int y) {
		top = true;
		bottom = false;
		this.y = y;
	}
	public void setMiddle(int y) {
		top = false;
		bottom = false;
		this.y = y;
	}
	public void setLeft(int x) {
		left = true;
		right = false;
		this.x = x;
	}
	public void setRight(int x) {
		left = false;
		right = true;
		this.x = x;
	}
	public void setCentre(int x) {
		left = false;
		right = false;
		this.x = x;
	}
	
	public void render(Graphics2D g, String text) {
		
		int x, y;

		Font oldFont = g.getFont();
		g.setFont(new Font(g.getFont().getName(), g.getFont().getStyle(), size));
		Rectangle2D bounds = g.getFontMetrics().getStringBounds(text, g);
		
		if (left) {
			x = this.x;
		}
		else if (right) {
			x = CubotApplet.DRAWABLE_WIDTH - (int)bounds.getWidth() - this.x;
		}
		else {
			x = (int) ((CubotApplet.DRAWABLE_WIDTH - bounds.getWidth()) / 2.0) + this.x;
		}
		
		if (top) {
			y = this.y;
		}
		else if (bottom) {
			y = CubotApplet.DRAWABLE_HEIGHT - (int)bounds.getHeight() - this.y;
		}
		else {
			y = (int) ((CubotApplet.DRAWABLE_HEIGHT - bounds.getHeight()) / 2.0) + this.y;
		}
		
		for (int i = full; i >= 0 ; i--) {
			double prop = (double)i / (double)full;
			double inv = 1.0 - prop;
			int red = (int) (inv * main.getRed() + prop * outline.getRed());
			int grn = (int) (inv * main.getGreen() + prop * outline.getGreen());
			int blu = (int) (inv * main.getBlue() + prop * outline.getBlue());
			Color colour = new Color(red, grn, blu);
			g.setColor(colour);
			for (double theta = 0.0; theta < 2.0 * Math.PI; theta += Math.PI/16.0) {
				int j = x + (int) (i * Math.sin(theta));
				int k = y + (int) (i * Math.cos(theta));
				g.drawString(text, j, k);
			}
		}
		
		g.setFont(oldFont);
	}

		
	
}
