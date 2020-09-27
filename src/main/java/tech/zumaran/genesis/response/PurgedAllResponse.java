package tech.zumaran.genesis.response;

import java.util.List;

import org.springframework.http.HttpStatus;

import tech.zumaran.genesis.GenesisEntity;

public class PurgedAllResponse extends ListEntityResponse {

	public PurgedAllResponse(List<? extends GenesisEntity> entities) {
		super(HttpStatus.OK, "list purged from recycle bin", entities);
	}

}
