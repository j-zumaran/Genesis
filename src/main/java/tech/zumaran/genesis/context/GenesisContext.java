package tech.zumaran.genesis.context;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.zumaran.genesis.framework.GenesisEntity;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public abstract class GenesisContext extends GenesisEntity {
	
	private static final long serialVersionUID = 7389942231760491853L;
	
	@Column(nullable = false, unique = true)
	@Setter private long contextId;
	
	@JsonIgnore
	public long getContextId() {
		return contextId;
	}
	
}
