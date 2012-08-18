package cubots.applet;

public class CubotRunnable implements Runnable {
	
	public static final int FRAME_RATE_TARGET = 30; // fps
	public static final int MILLISECONDS_PER_SECOND = 1000;
	public static final int DELAY_TARGET = MILLISECONDS_PER_SECOND / FRAME_RATE_TARGET;
	
	boolean running;
	private final CubotApplet applet;
	
	public CubotRunnable(CubotApplet applet) {
		this.applet = applet;
//		start();
	}
	
	public void start() {
		running = true;
		new Thread(this).start();
	}
	
	public void stop() {
		running = false;
	}
	
	public void run() {
		
		long frameStart;
		long frameDurationSoFar;
		long delay;
		
		boolean focused = applet.requestFocusInWindow();
		
		while (running) {
			
			if (!focused) {
				focused = applet.requestFocusInWindow();
			}
			
			frameStart = System.currentTimeMillis();
			applet.process();
			applet.repaint();
			frameDurationSoFar = System.currentTimeMillis() - frameStart;
			delay = Math.max(1, (long) DELAY_TARGET - frameDurationSoFar);
			pause(delay);
			frameCount++;
		}
	}

	private synchronized void pause(long delay) {
		averageDelay = averageDelay * 0.99 + delay * 0.01;
		try {
			wait(delay);
		} catch (InterruptedException e) {
			return;
		}
	}
	
	double averageDelay = 0;
	
	public double getAverageDelay() {
		return averageDelay;
	}
	
	private final long start = System.currentTimeMillis();
	private long frameCount = 0;
	
	public double getActualFrameRate() {
		return MILLISECONDS_PER_SECOND * ((double)frameCount) / (System.currentTimeMillis() - start);
	}

}
