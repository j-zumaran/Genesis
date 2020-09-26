package tech.zumaran.genesis.framework.preference;

public interface PreferenceValue<T> {
	String stringValue();
	T fromString(String string);
}
