package tech.zumaran.genesis.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class GenesisResponse {

	private final HttpStatus status;
	
	private final String message;
	
	public GenesisResponse(HttpStatus status, String message) {
		this.status = status;
		this.message = message == null? "No message available.": message;
	}
	
	public HttpStatus getStatus() {
		return status;
	}
	
	public String getMessage() {
		return message;
	}
	
	@Override
	public String toString() {
		return "Response status: " + status + "\n"
				+ "Message: " + message + "\n"
				;
	}
	
	public ResponseEntity<? extends GenesisResponse> createResponse() {
		LOG.info("{}", this);
		return ResponseEntity.status(getStatus()).body(this);
	}

	static final Logger LOG = LoggerFactory.getLogger(GenesisResponse.class);

}
