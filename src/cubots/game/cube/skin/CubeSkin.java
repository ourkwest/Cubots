package cubots.game.cube.skin;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import cubots.applet.CubotApplet;

public class CubeSkin {
	
	public static final int RESOLUTION = 20;
	public static final int T0 = 0;
	public static final int T1 = RESOLUTION / 10;
	public static final int T2 = T1 * 2;
	public static final int T3 = T1 * 3;
	public static final int T4 = T1 * 4;
	public static final int T5 = T1 * 5;
	public static final int T6 = T1 * 6;
	public static final int T7 = T1 * 7;
	public static final int T8 = T1 * 8;
	public static final int T9 = T1 * 9;
	public static final int T10 = T1 * 10;
	
	
	private Color outline;
	private Color weaponOutline;
	private double hueRotation; 
	private final BufferedImage[] faces;

	public CubeSkin(Color weapon, Color outline) {
		this.faces = new BufferedImage[6];
		setOutline(outline);
		setWeapon(weapon);
		setWeaponOutline(weapon);
	}

	protected void setOutline(Color outline) {
		this.outline = outline;
	}
	
	protected void setWeaponOutline(Color weapon) {
		this.weaponOutline = weapon;
	}
	protected void setWeapon(Color weapon) {
		this.hueRotation = calculateHueRotation(weapon);
	}
	
	private final double calculateHueRotation(Color weaponOutline) {
		double startRotation = 0;
		double split = Math.PI * 2.0 / 3.0;
		int proximity = 255 * 3;
		int thisDistance;
		for (double colourRotation = 0.0; colourRotation < Math.PI * 2.0; colourRotation += 0.01) {
			int red = (int)Math.max(0, 100 + 155.0 * Math.sin(colourRotation));
			int green = (int)Math.max(0, 100 + 155.0 * Math.sin(colourRotation - split));
			int blue = (int)Math.max(0, 100 + 155.0 * Math.sin(colourRotation + split));
			thisDistance = 
				Math.abs(weaponOutline.getRed() - red) + 
				Math.abs(weaponOutline.getGreen() - green) + 
				Math.abs(weaponOutline.getBlue() - blue);
			if (thisDistance < proximity) {
				proximity = thisDistance;
				startRotation = colourRotation;
			}
		}
		return startRotation;
	}
	
	protected final Graphics2D getFaceGraphics(int i) {
		BufferedImage image = new BufferedImage(RESOLUTION, RESOLUTION, BufferedImage.TYPE_INT_ARGB);
		this.faces[i - 1] = image;
		Graphics2D g2d = image.createGraphics();
		g2d.setRenderingHints(CubotApplet.AWESOME_HINTS);
		return g2d;
	}
	
	protected final void setFace(int i, int from) {
		this.faces[i - 1] = this.faces[from - 1];
	}

	public BufferedImage getFace(int i) {
		return faces[i - 1];
	}
	
	public Color getOutline() {
		return outline;
	}
	
	public Color getWeaponOutline() {
		return weaponOutline;
	}
	
	public double getHueRotation() {
		return hueRotation;
	}
	
}
