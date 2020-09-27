package tech.zumaran.genesis.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString
@RequiredArgsConstructor
public abstract class GenesisResponse {

	@Getter private final HttpStatus status;
	
	@Getter private final String message;
	
	public ResponseEntity<? extends GenesisResponse> createResponse() {
		log.info("{}", this);
		return ResponseEntity.status(getStatus()).body(this);
	}

}
