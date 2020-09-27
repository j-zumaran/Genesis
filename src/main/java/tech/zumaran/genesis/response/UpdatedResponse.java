package tech.zumaran.genesis.response;

import org.springframework.http.HttpStatus;

import tech.zumaran.genesis.GenesisEntity;

public class UpdatedResponse extends SingleEntityResponse {

	public UpdatedResponse(GenesisEntity entity) {
		super(HttpStatus.OK, entity.getType() + " ID " + entity.getId() + " updated.", entity);
	}

}
