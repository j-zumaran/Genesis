package tech.zumaran.genesis.framework.code;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnore;

import tech.zumaran.genesis.framework.GenesisEntity;

@MappedSuperclass
public abstract class CodeEntity extends GenesisEntity {

	private static final long serialVersionUID = -7837254358350358492L;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private CodeFormat codeFormat;
	
	public void setCodeFormat(CodeFormat codeFormat) {
		this.codeFormat = codeFormat;
	}
	
	@JsonIgnore
	public CodeFormat getCodeFormat() {
		return codeFormat;
	}
	
	public String getCode() throws CodeFormatError {
		return applyFormat(codeFormat);
	}
	
	protected String applyFormat(CodeFormat codeFormat) throws CodeFormatError {
		String regex = codeFormat.getRegex();
		return String.format("", getId(), getClass().getSimpleName(), regex);
	}

	public static class CodeFormatError extends Exception {

		private static final long serialVersionUID = 7024557472863429170L;
		
		public CodeFormatError(String msg) {
			super(msg);
		}
		
	}
	
}
