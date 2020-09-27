package tech.zumaran.genesis.response;

import org.springframework.http.HttpStatus;

import tech.zumaran.genesis.GenesisEntity;

public class PurgedResponse extends SingleEntityResponse {

	PurgedResponse(GenesisEntity entity) {
		super(HttpStatus.OK, entity.getType() + " ID " + entity.getId() + " purged from recycle bin.", entity);
	}

}
