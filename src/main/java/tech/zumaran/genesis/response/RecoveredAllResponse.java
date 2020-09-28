package tech.zumaran.genesis.response;

import java.util.List;

import org.springframework.http.HttpStatus;

import tech.zumaran.genesis.GenesisEntity;

public class RecoveredAllResponse extends ListEntityResponse {

	public RecoveredAllResponse(List<? extends GenesisEntity> entities) {
		super(HttpStatus.OK, "list recovered from recycle bin", entities);
	}

}
