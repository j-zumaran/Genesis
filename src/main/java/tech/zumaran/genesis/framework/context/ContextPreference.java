package tech.zumaran.genesis.framework.context;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import tech.zumaran.genesis.framework.preference.PreferenceKeyEntity;

@Entity
@Table(name = "context_preferences")
public class ContextPreference extends ContextEntity {

	private static final long serialVersionUID = 5322195561800301986L;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private PreferenceKeyEntity key;
	
	@Column(nullable = false)
	private String value;
	
	public ContextPreference(PreferenceKeyEntity key) {
		this.key = key;
	}
	
	public ContextPreference() {}
	
	public PreferenceKeyEntity getKey() {
		return key;
	}
	
	public void setKey(PreferenceKeyEntity key) {
		this.key = key;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
}
