package tech.zumaran.genesis.framework.code;

import javax.persistence.Column;
import javax.persistence.Entity;

import tech.zumaran.genesis.framework.GenesisEntity;

@Entity
public class CodeFormat extends GenesisEntity {
	
	@Column(nullable = false, unique = true)
	private String type;

	@Column(nullable = false)
	private String regex;
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
	public void setRegex(String regex) {
		this.regex = regex;
	}
	
	public String getRegex() {
		return regex;
	}
	
	public static CodeFormat from(String type, String regex) {
		CodeFormat format = new CodeFormat();
		format.setType(type);
		format.setRegex(regex);
		return format;
	}
	
	private static final long serialVersionUID = -1995249111947096348L;

}
