package tech.zumaran.genesis.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import tech.zumaran.genesis.GenesisEntity;

public abstract class SingleEntityResponse extends GenesisResponse {
	
	@Getter private GenesisEntity entity;

	SingleEntityResponse(HttpStatus status, String message, GenesisEntity entity) {
		super(status, message);
		this.entity = entity;
	}
}
