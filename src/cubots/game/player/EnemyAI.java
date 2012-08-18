package cubots.game.player;

import cubots.applet.WhiteBoard;
import cubots.game.Board;
import cubots.game.Cube;
import cubots.game.Direction;
import cubots.game.util.RollingAverage;

public class EnemyAI implements DirectionProvider {
	
	
	MnemonicDirector memory;
	RandomDirector random = new RandomDirector();
	Inert inert = new Inert();
	
	DirectionProvider current = inert;
	
	private RollingAverage chooseMemory = new RollingAverage(0.5, 0.9999);
	private RollingAverage chooseRandom = new RollingAverage(0.0, 0.999);
	private double dRandom = 0.1;
	
	
	
	
	
	public EnemyAI() {
		memory = WhiteBoard.memory;
		
	}
	
	public void setBoard(Board board) {
		memory.setBoard(board);
	}
	
	public void setThisCube(Cube cube) {
		memory.setThisCube(cube);
	}
	
	public void setOtherCube(Cube cube) {
		memory.setOtherCube(cube);
	}
	
	
	@Override
	public Direction getNext() {
		return chooseProvider().getNext();
	}
	
	private int ticker;
	private DirectionProvider chooseProvider() {
		chooseMemory.addNext(0.85);
		chooseRandom.addNext(dRandom);
		dRandom -= 0.00001;
		
		ticker++;
		ticker%=100;
		if (ticker == 0) {
			System.out.println("Random: " + chooseRandom.getCurrent());
			System.out.println("Memory: " + chooseMemory.getCurrent());
		}
		
		if (Math.random() < chooseMemory.getCurrent()) {
			return memory;
		}
		if (Math.random() < chooseRandom.getCurrent()) {
			return random;
		}
		return inert;
	}
	
}
