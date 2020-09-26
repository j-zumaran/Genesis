package tech.zumaran.genesis.response;

import java.util.List;

import org.springframework.http.HttpStatus;

import tech.zumaran.genesis.framework.GenesisEntity;

public class InsertedAllResponse extends ListEntityResponse {

	public InsertedAllResponse(List<? extends GenesisEntity> entities) {
		super(HttpStatus.CREATED, "List inserted to database.", entities);
	}

}
