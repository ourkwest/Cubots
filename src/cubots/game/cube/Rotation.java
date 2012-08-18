package cubots.game.cube;

import cubots.game.util.Point3D;
import cubots.game.util.UnitVector;

public class Rotation {
	
	UnitVector primary;
	UnitVector secondary;
	
	public Rotation(UnitVector primary, UnitVector secondary) {
		this.primary = primary;
		this.secondary = secondary;
	}
	
	public void apply(Point3D point, double proportion) {

		double theta = proportion * (Math.PI / 2.0);
		
		double x1 = primary.extract(point);
		double y1 = secondary.extract(point);
		
		double x2 = Math.cos(theta) * x1 - Math.sin(theta) * y1;
		double y2 = Math.sin(theta) * x1 + Math.cos(theta) * y1;
		
		primary.apply(point, x2);
		secondary.apply(point, y2);
	}

}
