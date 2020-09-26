package tech.zumaran.genesis.response;

import java.net.URI;

import org.springframework.http.HttpStatus;

public class ExceptionResponse extends GenesisResponse {
	
	private URI path;
	
	private Exception exception;
	
	public ExceptionResponse(HttpStatus status, URI path, Exception exception) {
		super(status, exception.getMessage());
		this.exception = exception;
		this.path = path;
	}

	public String getPath() {
		return path.toString();
	}
	
	public Exception getException() {
		return exception;
	}
}
