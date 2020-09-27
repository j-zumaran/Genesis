package tech.zumaran.genesis.exception;

import lombok.Getter;

public class NotFoundException extends GenesisException {

	private static final long serialVersionUID = 7503036353295821977L;
	
	@Getter private Object id;
	
	public NotFoundException(Class<?> type, Object id) {
		super(type.getSimpleName() + " identified by " + id + " not found in database.");
		this.id = id;
	}
	
}
