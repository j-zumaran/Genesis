package tech.zumaran.genesis.framework.context;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import tech.zumaran.genesis.framework.GenesisEntity;

@Entity
@Table(name = "context")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class AppContext extends GenesisEntity {
	
	private static final long serialVersionUID = -5470352666092550472L;

	@NotNull
	@Column(nullable = false, unique = true)
	private long contextId;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "context")
    private Set<ContextPreference> preferences = new HashSet<>();
	
	public AppContext() {}
	
	public AppContext(long contextId) {
		this.contextId = contextId;
	}
	
	@JsonIgnore
	public long getContextId() {
		return contextId;
	}
	
	@JsonIgnore
	public Set<ContextPreference> getPreferences() {
		return preferences;
	}
	
	public void setPreferences(Set<ContextPreference> preferences) {
		this.preferences = preferences;
	}

}
