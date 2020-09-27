package tech.zumaran.genesis.response;

import java.net.URI;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import tech.zumaran.genesis.exception.NotFoundException;

public class NotFoundResponse extends ExceptionResponse {
	
	@Getter private Object id;

	public NotFoundResponse(URI path, NotFoundException exception) {
		super(HttpStatus.NOT_FOUND, path, exception);
		this.id = exception.getId();
	}
}
