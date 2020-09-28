package tech.zumaran.genesis.response;

import org.springframework.http.HttpStatus;

import tech.zumaran.genesis.GenesisEntity;

public class RecoveredResponse extends SingleEntityResponse {

	RecoveredResponse(GenesisEntity entity) {
		super(HttpStatus.OK, entity.getType() + " recovered ID: " + entity.getId(), entity);
	}

}
