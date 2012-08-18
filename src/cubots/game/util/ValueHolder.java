package cubots.game.util;

public class ValueHolder<V> {
	
	private V value;

	public ValueHolder(V value) {
		this.value = value;
	}
	
	public V getValue() {
		return value;
	}
	
	public void setValue(V value) {
		this.value = value;
	}

}
