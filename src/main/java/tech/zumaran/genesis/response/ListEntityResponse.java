package tech.zumaran.genesis.response;

import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import tech.zumaran.genesis.GenesisEntity;

public abstract class ListEntityResponse extends GenesisResponse {

	@Getter private List<? extends GenesisEntity> entities;

	public ListEntityResponse(HttpStatus status, String message, List<? extends GenesisEntity> entities) {
		super(status, message);
		this.entities = entities;
	}
}
