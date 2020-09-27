package tech.zumaran.genesis.context;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import lombok.Setter;
import tech.zumaran.genesis.GenesisEntity;

@MappedSuperclass
public abstract class ContextEntity<Context extends GenesisContext> extends GenesisEntity {
	
	private static final long serialVersionUID = 6275836790674590500L;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	@Setter private Context context;

}
