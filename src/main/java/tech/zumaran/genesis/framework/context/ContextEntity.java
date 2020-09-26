package tech.zumaran.genesis.framework.context;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import tech.zumaran.genesis.framework.GenesisEntity;

@MappedSuperclass
public abstract class ContextEntity extends GenesisEntity {

	private static final long serialVersionUID = 7833919886999932413L;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private AppContext context;

	public void setContext(AppContext context) {
		this.context = context;
	}

}
