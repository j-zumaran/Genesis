package tech.zumaran.genesis.response;

import org.springframework.http.HttpStatus;

import tech.zumaran.genesis.framework.GenesisEntity;

public class DeletedResponse extends SingleEntityResponse {

	public DeletedResponse(GenesisEntity entity) {
		super(HttpStatus.OK, entity.getType() + " ID " + entity.getId() + " deleted from database.", entity);
	}

}
