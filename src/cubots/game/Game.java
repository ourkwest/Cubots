package cubots.game;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cubots.applet.CubotApplet;
import cubots.applet.ImmediatePlayerKeyListener;
import cubots.applet.WhiteBoard;
import cubots.game.cube.skin.SkinProvider;
import cubots.game.player.DirectionProvider;
import cubots.game.player.EnemyAI;
import cubots.game.player.MnemonicDirector;
import cubots.game.player.levels.Levels;

public abstract class Game {

	Viewport viewport1;
	Viewport viewport2;
	Board board;
	List<Cube> cubes = new ArrayList<Cube>();
	Cube player;
	
	private ScoreBoard scoreBoard;
	private boolean finalRendered;
	
	private DirectionProvider player1;
	private DirectionProvider player2; 
	private List<KeyListener> listeners = new ArrayList<KeyListener>();
	
	boolean overable = true;
	
	public Game() {
		
		player1 = getPlayerOne();
		player2 = getPlayerTwo();
		
		viewport1 = new Viewport(false);
		viewport2 = new Viewport(true);
		
		board = new Board();
		
		WhiteBoard.setBoard(board);
		
		SkinProvider skins = new SkinProvider();
		
		final Cube cube1 = new Cube(board, 3, -3, player1, skins);
		final Cube cube2 = new Cube(board, -3, 3, player2, skins);
		player = cube1;
		
		WhiteBoard.setOtherCube(cube1);
		WhiteBoard.setThisCube(cube2);
		
		cubes.add(cube1);
		cubes.add(cube2);
		
		scoreBoard = new ScoreBoard(cube1, cube2);
	}
	
	protected abstract DirectionProvider getPlayerOne();
	protected abstract DirectionProvider getPlayerTwo();
	
	public void render(Graphics2D g) {
		render0(g);
		scoreBoard.render(g);
	}
	
	public void render0(Graphics2D g) {
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, CubotApplet.DRAWABLE_WIDTH, CubotApplet.DRAWABLE_HEIGHT);
		g.addRenderingHints(CubotApplet.AWESOME_HINTS);
		for (Cube cube : cubes) {
			cube.renderSparks(g, viewport1, false);
		}
		Collections.sort(cubes, Cube.byZOrder);
		board.renderBackdrop(g, viewport1);
		g.setClip(board.getClip(viewport1));
		for (Cube cube : cubes) {
			cube.render(g, viewport2);
		}
		board.renderSurface(g, viewport1);
		g.setClip(null);
		for (Cube cube : cubes) {
			cube.renderSparks(g, viewport2, true);
		}
		for (Cube cube : cubes) {
			cube.render(g, viewport1);
			cube.renderSparks(g, viewport1, true);
		}
	}
	
	public void renderFinal(Graphics2D g) {
		if (!finalRendered) {
			render0(g);
		}
		finalRendered = true;
	}

	private void score(Cube other, Cube cube) {
		WhiteBoard.memory.score(other);
		scoreBoard.score(1, cube);
	}
	public void process() {
		for (Cube cube : cubes) {
			cube.process();
			if (!cube.alive() && overable) {
				overFor(cube);
				overable = false;
//				setOver();
			}
		}
		for (Cube cube : cubes) {
			for (Cube other : cubes) {
				if (other.intersects(cube)) {
					score(cube, other);
				}
			}			
		}
	}
	
	private void overFor(Cube cube) {
		WhiteBoard.score(cube == this.player);
		WhiteBoard.setLevel(WhiteBoard.getLevel() + 1);
		setOver();
	}

	
	protected void addListener(KeyListener l) {
		listeners.add(l);
		WhiteBoard.INSTANCE.addKeyListener(l);
	}
	
	private void removeListeners() {
		for (KeyListener l : listeners) {
			WhiteBoard.INSTANCE.removeKeyListener(l);
		}
	}
	
	public boolean over() {
		final boolean gameOverFor = scoreBoard.isOver();//scorer.gameOverFor(player);
		if (gameOverFor) {
			removeListeners();
		}
		return gameOverFor;
	}

	public void setOver() {
		scoreBoard.setOver();
		removeListeners();
	}
	
}
