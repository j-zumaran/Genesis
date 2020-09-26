package tech.zumaran.genesis.response;

import org.springframework.http.HttpStatus;

import tech.zumaran.genesis.framework.GenesisEntity;

public abstract class SingleEntityResponse extends TransactionResponse {
	
	private GenesisEntity entity;

	SingleEntityResponse(HttpStatus status, String message, GenesisEntity entity) {
		super(status, message);
		this.entity = entity;
	}

	public GenesisEntity getEntity() {
		return entity;
	}
}
