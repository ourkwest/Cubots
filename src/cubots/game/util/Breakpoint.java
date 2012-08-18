package cubots.game.util;

public class Breakpoint {
	
	static boolean enabled;
	
	static int count = 0;
	
	public static void enable() {
		enabled = true;
	}
	
	public static void disable() {
		enabled = false;
	}
	
	public static void passBreakpoint() {
		if (enabled) { 
			count++;
		}
	}

}
