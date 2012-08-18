package cubots.game.player;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import cubots.game.Direction;

public class MultiProvider implements DirectionProvider {
	
	Map<DirectionProvider, Double> providers = new HashMap<DirectionProvider, Double>();
	Map<DirectionProvider, Integer> turns = new HashMap<DirectionProvider, Integer>();
	double total;
	
	public void add(double odds, DirectionProvider provider) {
		add(odds, provider, 1);
	}
	public void add(double odds, DirectionProvider provider, int turns) {
		total += odds;
		providers.put(provider, odds);
		this.turns.put(provider, turns);
	}
	
	DirectionProvider choosenProvider;
	int turnsRemaining;
	
	@Override
	public Direction getNext() {
		if (--turnsRemaining < 0) {
			choosenProvider = chooseProvider();
			turnsRemaining = turns.get(choosenProvider);
		}
		return choosenProvider.getNext();
	}

	private DirectionProvider chooseProvider() {
		double random = Math.random() * total;
		double running = 0;
		for (Entry<DirectionProvider, Double> entry : providers.entrySet()) {
			running += entry.getValue();
			if (random < running) {
				return entry.getKey();
			}
		}
		throw new NullPointerException();
	}
	
}
