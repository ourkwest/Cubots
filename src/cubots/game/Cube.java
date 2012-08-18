package cubots.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import cubots.game.cube.Bam;
import cubots.game.cube.CubeGeometry;
import cubots.game.cube.Intersection;
import cubots.game.cube.Orientation;
import cubots.game.cube.Spark;
import cubots.game.cube.skin.SkinProvider;
import cubots.game.player.DirectionProvider;
import cubots.game.util.Point3D;

public class Cube {

	Point location = new Point();
	private final Board board;
	Move move = new Move(Direction.Inert);

	CubeGeometry geometry;
	private final DirectionProvider directionProvider;
	private double zOrder;
	private Color sheen;
	private List<Spark> sparks = new ArrayList<Spark>();
	private Double hue;
	private Intersection currentIntersection = new Intersection(false, 0);
	
	private static final int MAX_HEALTH = 100;
	private int health = MAX_HEALTH;
	private Bam bam;

	public Cube(Board board, int x, int y, DirectionProvider directionProvider, SkinProvider skinProvider) {
		this.geometry = new CubeGeometry(skinProvider.getSkin());
		this.board = board;
		this.location.x = x;
		this.location.y = y;
		this.directionProvider = directionProvider;
		if (!board.requestLocation(location)) {
			throw new IllegalArgumentException("Requested Cube location is occupied!");
		}
	}
	
	public boolean alive() {
		return health >= 0;
	}
	
	public double health() {
		if (alive()) {
			return (double)health / (double)MAX_HEALTH;
		}
		return 0;
	}

	public void process() {
		if (alive()) {
			processMove();
		}
		addSparks();
		processSparks();
	}

	private void processMove() {
		if (move.isDepleted()) {
			finishLastMove();
			move = getNewMove();
		}
		move.progress();
	}

	private void addSparks() {
		if (!alive()) {
			return;
		}
		final CubeGeometry currentGeometry = move.getCurrent(geometry);
		Point3D currentCentre = currentGeometry.getCentre().add(new Point3D(location.x, location.y, CubeGeometry.RADIUS));
		if (sheen != null) {
			int sparkCount = 10;
			boolean extraBig = false;
			if (health == 0) {
				health--;
				sparkCount = 2500;
				extraBig = true;
				bam = new Bam(currentCentre, hue);
			}
			for (int i = sparkCount; i > 0; i--) {
				sparks.add(new Spark(currentCentre, hue, extraBig));
			}
			sheen = null;
		}
	}

	private void processSparks() {
		final List<Spark> nextSparks = new ArrayList<Spark>();
		for (Spark spark : sparks) {
			spark.process();
			if (spark.stillAlive()) {
				nextSparks.add(spark);
			}
		}
		sparks = nextSparks;
		if (bam != null) {
			bam.process();
		}
	}

	private void finishLastMove() {
		if (move.direction != Direction.Inert) {
			board.freeLocation(location);
		}
		geometry = move.getFinal(geometry);
		move.apply(location);
	}
	
	private Move getNewMove() {
		final Move newMove = new Move(generateNewDirection());
		Point newLocation = new Point(location);
		newMove.apply(newLocation);
		boolean moveOk = board.requestLocation(newLocation);
		if (moveOk) {
			return newMove;
		}
		return new Move(Direction.Inert);
	}

	private Direction generateNewDirection() {
		final Direction next = directionProvider.getNext();
		return next;
	}


	public void render(Graphics2D g, Viewport viewport) {
		if (!alive()) {
			return;
		}
		Point centre = viewport.transform(0, 0, 0);
		Point screenLocation = viewport.transform(location.x, location.y, CubeGeometry.RADIUS);
		int xLocationOffset = screenLocation.x - centre.x;
		int yLocationOffset = screenLocation.y - centre.y;
		final CubeGeometry currentGeometry = move.getCurrent(geometry);
		zOrder = currentGeometry.getZOrder();
		currentGeometry.render(g, viewport, xLocationOffset,  yLocationOffset, sheen, currentIntersection);
	}
	
	public void renderSparks(Graphics2D g, Viewport viewport, boolean infront) {
		for (Spark spark : sparks) {
			spark.render(g, viewport, infront);
		}
		if (bam != null) {
			bam.render(g, viewport);
		}
	}
	
	public static Comparator<Cube> byZOrder = new Comparator<Cube>() {
		@Override
		public int compare(Cube o1, Cube o2) {
			final double zLocation1 = o1.location.x + o1.location.y + o1.zOrder;
			final double zLocation2 = o2.location.x + o2.location.y + o2.zOrder;
			return (int) (zLocation1 * 100.0 - zLocation2 * 100.0);
		}
	};

	public boolean intersects(Cube cube) {
		if (cube == this) {
			return false;
		}
		if ((!cube.alive()) || (!alive())) {
			currentIntersection = new Intersection(false, 0);
			return false;
		}
		final CubeGeometry thisGeometry = this.move.getCurrent(this.geometry);
		final CubeGeometry otherGeometry = cube.move.getCurrent(cube.geometry);
		final Intersection intersection = thisGeometry.intersects(location, otherGeometry, cube.location);
		currentIntersection = intersection;
		if (intersection.isOccuring()) {
			cube.sheen = otherGeometry.getWeaponOutline();
			cube.hue = thisGeometry.getWeaponHueRotation();
			cube.health--;
		}
		else {
			cube.sheen = null;
		}
		return intersection.isOccuring();
	}
	
	public Point getLocation() {
		return new Point(location);
	}
	
	public Point getNextLocation() {
		Point newLocation = new Point(location);
		move.apply(newLocation);
		return newLocation;
	}
	
	public Orientation getOrientation() {
		return this.geometry.getOrientation();
	}
	
	public Orientation getNextOrientation() {
		return move.getFinal(geometry).getOrientation();
	}
	
	public Orientation getPossibleOrientation(Move possibleMove) {
		return possibleMove.getFinal(geometry).getOrientation();
	}
}

