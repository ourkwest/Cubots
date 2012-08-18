package testbed;

import java.awt.Color;
import java.awt.event.KeyEvent;

public class TestBed {
	
	public static void main(String[] args) {
		
		System.out.println(KeyEvent.VK_A);
		System.out.println(KeyEvent.VK_S);
		System.out.println(KeyEvent.VK_D);
		System.out.println(KeyEvent.VK_W);
		
		int a, b, c = 5;
		a = b = c;
		System.out.println("A = " + a);
		System.out.println("B = " + b);
		System.out.println("C = " + c);
		
		double startRotation = 0;
		double colourRotation = 0;
		double split = Math.PI * 2.0 / 3.0;
		
		int distance = 255 * 3;
		int thisDistance;
		
		Color test = new Color(255, 0, 0);
		for (colourRotation = 0.0; colourRotation < Math.PI * 2.0; colourRotation += 0.01) {
			int red = (int)Math.max(0, 100 + 155.0 * Math.sin(colourRotation));
			int green = (int)Math.max(0, 100 + 155.0 * Math.sin(colourRotation - split));
			int blue = (int)Math.max(0, 100 + 155.0 * Math.sin(colourRotation + split));
			thisDistance = 
				Math.abs(test.getRed() - red) + 
				Math.abs(test.getGreen() - green) + 
				Math.abs(test.getBlue() - blue);
			if (thisDistance < distance) {
				distance = thisDistance;
				startRotation = colourRotation;
			}
		}
		
		colourRotation = startRotation;
		
		for (int i = 0; i < 20; i++) {
			
			int red = (int)Math.max(0, 100 + 155.0 * Math.sin(colourRotation));
			int green = (int)Math.max(0, 100 + 155.0 * Math.sin(colourRotation - split));
			int blue = (int)Math.max(0, 100 + 155.0 * Math.sin(colourRotation + split));
			
			System.out.println(colourRotation + " : " + new Color(red, green, blue));
			
			
			
			colourRotation += 0.8;
			
		}

		
		double x1 = 1.0;
		double y1 = 3.0;
		double x2 = -1.0;
		double y2 = -1.0;
		double result = intersectZeroAt(x1, y1, x2, y2);
		
		System.out.println(result);
		
		
	}
	
	private static double intersectZeroAt(double x1, double y1, double x2, double y2) {
		// Y = mX + c
		if (Math.abs(x2 - x1) < 0.001) { // div 0
			System.out.println("Returning x1");
			return x1;
		}
		
		double m = (y2 - y1) / (x2 - x1);
		System.out.println("m = " + m);
		double c = y1 - (m * x1);
		System.out.println("c = " + c);
		return c / m;
	}

}
