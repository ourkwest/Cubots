package cubots.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;

import cubots.applet.CubotApplet;
import cubots.game.util.GameFont;

public class ScoreKeeper {
	
	Object a, b;
	Map<Object, Score> scores = new HashMap<Object, Score>();
	
	boolean over = false; 
	int finalScore;
	
	public ScoreKeeper(Object a, Object b) {
		scores.put(a, new Score(a));
		scores.put(b, new Score(b));
	}
	
	public void score(int amount, Object forObject) {
		for (Score score : scores.values()) {
			score.accumulate(amount, forObject);
		}
	}
	
	public boolean gameOverFor(Object object) {
		if (scores.get(object).gameOver()) {
			over = true;
		}
		return over;
	}
	
	public void gameIsOverFor(Cube player) {
		scores.get(player).gameIsOver();
		over = true;
	}
	
	public void render(Graphics2D g, Cube player) {
		
		final int totalWidth = CubotApplet.DRAWABLE_WIDTH - 40 + 1;
		g.setColor(Color.BLACK);
		g.fillRect(20, 20, totalWidth, 5);
		g.setColor(Color.YELLOW.darker());
		int width = scores.get(player).getPeakProportion(totalWidth);
		g.fillRect(20, 20, width, 5);
		g.setColor(Color.YELLOW);
		g.drawLine(20 + width, 20, 20 + width, 25);
		width = scores.get(player).getAccumulatedProportion(totalWidth);
		g.fillRect(20, 20, width, 5);
		g.setColor(Color.BLACK);
		g.drawRect(20, 20, totalWidth, 5);
		
		GameFont effect = new GameFont(15, 3, Color.YELLOW, Color.BLACK); 
		effect.setTop(50);
		effect.setLeft(50);
		effect.render(g, 
				"Score: " + 
				scores.get(player).accumulated + 
				" / " + 
				scores.get(player).peakScore);
		effect.setRight(50);
		effect.render(g, "Keys:  ←  ↑  →  ↓  Esc");
	}
	
	public void renderFinal(Graphics2D g, Cube player) {
		if (finalScore == 0) {
			finalScore = scores.get(player).peakScore;
		}
		String score = "Peak score: " + finalScore;
		GameFont effect = new GameFont(50, 10, Color.YELLOW, Color.BLACK);
		effect.setCentre(0);
		effect.setMiddle(0);
		effect.render(g, score);
		
		final String advice = "Press 'Esc' to continue.";
		effect = new GameFont(25, 10, Color.YELLOW, Color.BLACK);
		effect.setCentre(0);
		effect.setMiddle(50);
		effect.render(g, advice);
		
	}
	
	private static class Score {
		
		private static final double TARGET_SCORE = 1000.0;
		int total;
		int accumulated;
		int peakScore;
		private final Object forObject;
		
		public Score(Object forObject) {
			this.forObject = forObject;
		}
		
		public void gameIsOver() {
			accumulated = -1;
		}

		public boolean gameOver() {
			return accumulated < 0;
		}

		public void accumulate(int score, Object forObject) {
			int myScore = (this.forObject == forObject ? score : -score);
			if (myScore > 0) {
				total += myScore;
			}
			accumulated += myScore;
			peakScore = Math.max(accumulated, peakScore);
		}
		
		public int getAccumulatedProportion(int ofAmount) {
			return (int)((double)ofAmount * ((double)accumulated / Math.max((double)peakScore, TARGET_SCORE)));
		}
		
		public int getPeakProportion(int ofAmount) {
			return (int)((double)ofAmount * ((double)peakScore / Math.max((double)peakScore, TARGET_SCORE)));
		}
	}

}
