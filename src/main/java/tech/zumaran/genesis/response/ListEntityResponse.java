package tech.zumaran.genesis.response;

import java.util.List;

import org.springframework.http.HttpStatus;

import tech.zumaran.genesis.framework.GenesisEntity;

public abstract class ListEntityResponse extends TransactionResponse {

	private List<? extends GenesisEntity> entities;

	public ListEntityResponse(HttpStatus status, String message, List<? extends GenesisEntity> entities) {
		super(status, message);
		this.entities = entities;
	}

	public List<? extends GenesisEntity> getEntities() {
		return entities;
	}
}
