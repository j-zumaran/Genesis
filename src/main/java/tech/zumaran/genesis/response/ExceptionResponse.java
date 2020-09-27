package tech.zumaran.genesis.response;

import java.net.URI;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.ToString;

@ToString
public class ExceptionResponse extends GenesisResponse {
	
	@Getter private URI path;
	
	@Getter private Exception exception;
	
	public ExceptionResponse(HttpStatus status, URI path, Exception exception) {
		super(status, exception.getMessage());
		this.exception = exception;
		this.path = path;
	}
}
