package tech.zumaran.genesis.response;

import org.springframework.http.HttpStatus;

import tech.zumaran.genesis.GenesisEntity;

public class InsertedResponse extends SingleEntityResponse {
	
	public InsertedResponse(GenesisEntity entity) {
		super(HttpStatus.CREATED, entity.getType() + " inserted to database.", entity);
	}
}
