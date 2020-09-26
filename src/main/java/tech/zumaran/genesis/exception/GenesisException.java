package tech.zumaran.genesis.exception;

public abstract class GenesisException extends Exception {
	
	private static final long serialVersionUID = -5572573910666284817L;
	
	public GenesisException(String...msg) {
		super(String.join(" ", msg));
	}
}
