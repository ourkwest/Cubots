package cubots.game.player.levels;

import java.awt.Color;
import java.awt.Graphics2D;

import cubots.applet.WhiteBoard;
import cubots.applet.phase.Phase;
import cubots.game.player.DirectionProvider;
import cubots.game.player.Inert;
import cubots.game.player.MultiProvider;
import cubots.game.player.RandomDirector;
import cubots.game.util.GameFont;

public class Levels {
	
	static MultiProvider[] providers = new MultiProvider[12]; 
	static {
		providers[0] = new MultiProvider();
		providers[1] = new MultiProvider();
		providers[2] = new MultiProvider();
		providers[3] = new MultiProvider();
		providers[4] = new MultiProvider();
		providers[5] = new MultiProvider();
		providers[6] = new MultiProvider();
		providers[7] = new MultiProvider();
		providers[8] = new MultiProvider();
		providers[9] = new MultiProvider();
		providers[10] = new MultiProvider();
		providers[11] = new MultiProvider();
		
		providers[0].add(1.0, new Inert());
		
		providers[1].add(1.0, WhiteBoard.memory);
		providers[1].add(1.0, new Inert(), 15);

		providers[2].add(1.0, WhiteBoard.memory);
		providers[2].add(1.0, new Inert(), 10);

		providers[3].add( 1.0, WhiteBoard.memory);
		providers[3].add( 0.1, new RandomDirector());
		providers[3].add( 1.0, new Inert(), 7);
		providers[3].add( 0.1, WhiteBoard.target);

		providers[4].add( 1.0, WhiteBoard.memory);
		providers[4].add( 0.2, new RandomDirector());
		providers[4].add( 1.0, new Inert(), 5);
		providers[4].add( 0.1, WhiteBoard.target);

		providers[5].add( 1.0, WhiteBoard.memory);
		providers[5].add( 0.3, new RandomDirector());
		providers[5].add( 1.0, new Inert(), 2);
		providers[5].add( 0.1, WhiteBoard.target);

		providers[6].add( 1.0, WhiteBoard.memory);
		providers[6].add( 0.4, new RandomDirector());
		providers[6].add( 1.0, new Inert());
		providers[6].add( 0.1, WhiteBoard.target);

		providers[7].add( 1.0, WhiteBoard.memory);
		providers[7].add( 0.5, new RandomDirector());
		providers[7].add( 1.0, new Inert());
		providers[7].add( 0.1, WhiteBoard.target);

		providers[8].add( 1.0, WhiteBoard.memory);
		providers[8].add( 0.4, new RandomDirector());
		providers[8].add( 1.0, new Inert());
		providers[8].add( 0.2, WhiteBoard.target);

		providers[9].add( 1.0, WhiteBoard.memory);
		providers[9].add( 0.3, new RandomDirector());
		providers[9].add( 1.0, new Inert());
		providers[9].add( 0.3, WhiteBoard.target);

		providers[10].add( 1.0, WhiteBoard.memory);
		providers[10].add( 0.1, new RandomDirector());
		providers[10].add( 1.0, new Inert());
		providers[10].add( 0.5, WhiteBoard.target);
		
		providers[11].add( 1.0, WhiteBoard.memory);
		providers[11].add( 0.5, WhiteBoard.target);
	}
	
	public static DirectionProvider getDirectionProvider() {
		if (WhiteBoard.getLevel() >= 20) {
			return providers[11];
		}
		if (WhiteBoard.getLevel() >= 10) {
			return providers[10];
		}
		return providers[WhiteBoard.getLevel() % providers.length];
	}
	
	public static Phase getScreen() {
		return new Phase() {
			@Override
			public void render(Graphics2D g) {
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, WhiteBoard.WIDTH, WhiteBoard.HEIGHT);
				GameFont effect = new GameFont(100, 3, Color.YELLOW, Color.CYAN); 
				effect.setCentre(0);
				effect.setMiddle(50);
				effect.render(g, "Round " + (int)(WhiteBoard.getLevel() + 1));
				effect = new GameFont(25, 1, Color.ORANGE, Color.RED); 
				effect.setCentre(0);
				effect.setMiddle(75);
				effect.render(g, WhiteBoard.getScore());
			}
		};
	}
}
