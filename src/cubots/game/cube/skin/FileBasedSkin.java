package cubots.game.cube.skin;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class FileBasedSkin extends CubeSkin {

	public FileBasedSkin(String fileName) {
		super(Color.BLACK, Color.WHITE);
		URL resource = FileBasedSkin.class.getResource(fileName);
		BufferedImage img = null;
		try {
		    img = ImageIO.read(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < 6; i++) {
			getFaceGraphics(i + 1).drawImage(img,
					0, 0, RESOLUTION, RESOLUTION,
					RESOLUTION * i, 0, 
					RESOLUTION * i + RESOLUTION, RESOLUTION, 
					null);
		}
		setWeaponOutline(new Color(img.getRGB(120, 0)));
		setWeapon(new Color(img.getRGB(120, 10)));
		setOutline(new Color(img.getRGB(120, 19)));
	}
	
	public FileBasedSkin(CubeSkin skin, String fileName) {
		super(skin.getWeaponOutline(), skin.getOutline());
		BufferedImage image = new BufferedImage(RESOLUTION * 6 + 1, RESOLUTION, BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.getGraphics();
		for (int i = 0; i < 6; i++) {
			System.out.println(g.drawImage(skin.getFace(i + 1),
					RESOLUTION * i, 0, 
					RESOLUTION * i + RESOLUTION, RESOLUTION, 
					0, 0, RESOLUTION, RESOLUTION,
					null));
		}
		g.setColor(getWeaponOutline());
		g.drawLine(RESOLUTION * 6, T0, RESOLUTION * 6, T8);
		g.setColor(getOutline());
		g.drawLine(RESOLUTION * 6, T8, RESOLUTION * 6, T10);
		try {
			System.out.println(ImageIO.write(image, "png", new File(fileName)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
