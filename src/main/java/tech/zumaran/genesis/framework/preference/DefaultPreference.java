package tech.zumaran.genesis.framework.preference;

public enum DefaultPreference implements PreferenceKey {
	CODE_FORMAT("CODE")
	;
	
	private final String defaultVAlue;
	
	DefaultPreference(String defaultVAlue) {
		this.defaultVAlue = defaultVAlue;
	}

	@Override
	public String getKeyName() {
		return name();
	}

	@Override
	public String getDefaultValue() {
		return defaultVAlue;
	}

}
