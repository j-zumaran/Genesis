package tech.zumaran.genesis.framework.preference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import tech.zumaran.genesis.framework.GenesisEntity;

@Entity
@Table(name = "preference")
public class PreferenceKeyEntity extends GenesisEntity implements PreferenceKey {
	
	private static final long serialVersionUID = -1848303021824598740L;
	
	@Column(nullable = false, unique = true)
	private String keyName;
	
	@Column(nullable = false)
	private String defaultValue;
	
	public PreferenceKeyEntity(PreferenceKey key) {
		this.keyName = key.getKeyName();
		this.defaultValue = key.getDefaultValue();
	}
	
	public PreferenceKeyEntity() {}

	@Override
	public String getKeyName() {
		return keyName;
	}

	@Override
	public String getDefaultValue() {
		return defaultValue;
	}

}

