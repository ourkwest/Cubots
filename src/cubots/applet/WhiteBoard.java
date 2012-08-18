package cubots.applet;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

import cubots.game.Board;
import cubots.game.Cube;
import cubots.game.player.MnemonicDirector;
import cubots.game.player.TargetedDirector;

public class WhiteBoard implements KeyListener {

	public static final WhiteBoard INSTANCE = new WhiteBoard();
	public static final int HEIGHT = 600;
	public static final int WIDTH  = 800;
	
	public static MnemonicDirector memory = new MnemonicDirector();
	public static TargetedDirector target = new TargetedDirector();
	
	private static int level;
	private static int scoreA;
	private static int scoreB;
	
	private static boolean twoPlayer = false;
	
	public static void reset() {
		memory = new MnemonicDirector();
		target = new TargetedDirector();
		level = 0;
		scoreA = 0;
		scoreB = 0;
		twoPlayer = false;
		INSTANCE.listeners.clear();
	}
	
	public static int getLevel() {
		return level;
	}
	
	public static void setLevel(int i) {
		level = i;
	}
	
	public static BufferedImage createImage() {
		return new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
	}
	
	private final Set<KeyListener> listeners = new HashSet<KeyListener>();
	private static boolean b;

	public void addKeyListener(KeyListener listener) {
		synchronized (listeners) {
			listeners.add(listener);
		}
	}

	public void removeKeyListener(KeyListener listener) {
		synchronized (listeners) {
			listeners.remove(listener);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		synchronized (listeners) {
			for (KeyListener listener : listeners) {
				listener.keyTyped(e);
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		synchronized (listeners) {
			for (KeyListener listener : listeners) {
				listener.keyPressed(e);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		synchronized (listeners) {
			for (KeyListener listener : listeners) {
				listener.keyReleased(e);
			}
		}
	}

	public static void setBoard(Board board) {
		memory.setBoard(board);
		target.setBoard(board);
	}

	public static void setOtherCube(Cube cube) {
		memory.setOtherCube(cube);
		target.setOtherCube(cube);
	}

	public static void setThisCube(Cube cube) {
		memory.setThisCube(cube);
		target.setThisCube(cube);
	}

	public static void score(boolean b) {
		if (b) {
			scoreB++;
			return;
		}
		scoreA++;
	} 
	
	public static String getScore() {
		if (scoreA + scoreB > 0) {
			return scoreA + "  :  " + scoreB;
		}
		return "";
	}

	public static void setTwoPlayer(boolean b) {
		WhiteBoard.twoPlayer = b;
	}

	public static boolean getTwoPlayer() {
		return twoPlayer;
	}

}
