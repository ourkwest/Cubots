package cubots.game.cube.skin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SkinProvider {

	private static final List<String> options;
	static {
		options = new ArrayList<String>();
		options.add("dice:cards");
		options.add("battenberg:swiss:sponge");
		options.add("swirl1:swirl2");
		options.add("neat:scrambled");
		options.add("snazy:groovy");
		options.add("zap:kapow:smash");
		options.add("hoop:slice:core");
	}
	private final List<String> names;
	private int nextName;
	
	public SkinProvider() {
		final String choosenOption = options.get((int) (Math.random() * options.size()));
		names = Arrays.asList(choosenOption.split(":"));
		Collections.shuffle(names);
		nextName = (int) (Math.random() * names.size());
	}
	
	public CubeSkin getSkin() {
		return new FileBasedSkin(getNextName());
	}

	private String getNextName() {
		nextName++;
		nextName%=names.size();
		return names.get(nextName) + ".png";
	}
	
}
