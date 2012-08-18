package cubots.applet;

import java.applet.Applet;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import cubots.applet.phase.Initialisation;
import cubots.applet.phase.Phase;

public class CubotApplet extends Applet {
	
	private static final long serialVersionUID = 1L;

	public static final int DRAWABLE_HEIGHT = WhiteBoard.HEIGHT;
	public static final int DRAWABLE_WIDTH  = WhiteBoard.WIDTH;

	public static final Map<Object, Object> AWESOME_HINTS = new HashMap<Object, Object>();

	private Object screenLock = new Object();
	private BufferedImage onScreen; 
	Graphics2D g; 
	private CubotRunnable runner;

	private Phase currentPhase; 
	
	@Override
	public void init() {
		super.init();
		WhiteBoard.reset();
		WhiteBoard.setTwoPlayer(getParameter("twoplayer") != null);
		
		setSize(DRAWABLE_WIDTH, DRAWABLE_HEIGHT);
		onScreen = WhiteBoard.createImage();
		g = onScreen.createGraphics();
		AWESOME_HINTS.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		AWESOME_HINTS.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		addKeyListener(WhiteBoard.INSTANCE);
		currentPhase = new Initialisation();
		runner = new CubotRunnable(this);
	}
	
	public void process() {
		currentPhase = currentPhase.process();
		synchronized (screenLock) {
			currentPhase.render(g);
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		synchronized (screenLock) {
			g.drawImage(onScreen, 0, 0, this);
		}
	}
	
	private Dimension offScreenSize;
	private Image offScreenImage;
	private Graphics offScreenGraphics;

	public final synchronized void update (Graphics g){
		Dimension d = getSize();
		if (	(offScreenImage == null) ||
				(d.width != offScreenSize.width) ||
				(d.height != offScreenSize.height)
				) {
			offScreenImage = createImage(d.width,d.height);
			offScreenSize = d;
			offScreenGraphics = offScreenImage.getGraphics();
		}
//		offScreenGraphics.clearRect(0, 0, d.width, d.height);
		paint(offScreenGraphics);
		g.drawImage(offScreenImage,0,0,null);
	}

	@Override
	public void start() {
		super.start();
		runner.start();
	}
	
	@Override
	public void stop() {
		super.stop();
		runner.stop();
	}

	@Override
	public void destroy() {
		super.destroy();
	}
}
