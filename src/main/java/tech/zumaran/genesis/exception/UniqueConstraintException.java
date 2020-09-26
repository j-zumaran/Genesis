package tech.zumaran.genesis.exception;

public class UniqueConstraintException extends GenesisException {

	private static final long serialVersionUID = 9001922127249568730L;
	
	public UniqueConstraintException(String...msg) {
		super(msg);
	}

}
