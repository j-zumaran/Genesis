package tech.zumaran.genesis.exception;

public class NotFoundException extends GenesisException {

	private static final long serialVersionUID = 7503036353295821977L;
	
	private Object id;
	
	public NotFoundException(Class<?> type, Object id) {
		super(type.getSimpleName() + " identified by " + id + " not found in database.");
		this.id = id;
	}
	
	public Object getId() {
		return id;
	}
	
}
