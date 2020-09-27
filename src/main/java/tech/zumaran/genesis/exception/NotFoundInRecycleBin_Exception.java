package tech.zumaran.genesis.exception;

import lombok.Getter;

public class NotFoundInRecycleBin_Exception extends GenesisException {

	private static final long serialVersionUID = -7827134268056847627L;
	
	@Getter private Object id;
	
	public NotFoundInRecycleBin_Exception(Class<?> type, Object id) {
		super(type.getSimpleName() + " identified by " + id + " not found in recycle bin.");
		this.id = id;
	}
}
