package tech.zumaran.genesis.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;

import tech.zumaran.genesis.response.ResponseFactory;

import java.sql.SQLIntegrityConstraintViolationException;

public abstract class ExceptionHandlerController extends ResponseEntityExceptionHandler {
	
	@Autowired
	private ResponseFactory responseFactory;
	
	@ExceptionHandler(Exception.class)
	private ResponseEntity<?> handleException(Exception e) {
		return responseFactory.exception(HttpStatus.BAD_REQUEST, e);
	}
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<?> notFound(NotFoundException e) {
		return responseFactory.notFound(e);
	}
	
	@ExceptionHandler(GenesisException.class)
	public ResponseEntity<?> genesisError(GenesisException e) {
		return responseFactory.exception(HttpStatus.BAD_REQUEST, e);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<?> dataIntegrity(DataIntegrityViolationException e) {
		Throwable cause = e.getMostSpecificCause();
		String error = cause.getMessage();
		
		if (cause instanceof SQLIntegrityConstraintViolationException) {
			SQLIntegrityConstraintViolationException sqlEx = (SQLIntegrityConstraintViolationException) cause;
			error += " State: " + sqlEx.getSQLState() 
					+ " Error code: " + sqlEx.getErrorCode();
		}
		return handleException(new Exception(error, cause));
	}
	
	@ExceptionHandler(HttpMessageConversionException.class)
	public ResponseEntity<?> httpConversionError(HttpMessageConversionException e) throws Throwable {
		Throwable cause = e.getMostSpecificCause();
		
		String error = cause.getMessage() + "\n";
		if (cause instanceof InvalidDefinitionException) {
			InvalidDefinitionException invalid = (InvalidDefinitionException) cause;
			String a = "Path: " + invalid.getPath() + "\n";
			String b = "Property " + invalid.getProperty() + "\n";
			String c = "Type: " + invalid.getType().getRawClass().getSimpleName() + "\n";
			error += a + b + c;
		}
		return handleException(new Exception(error, cause));
	}

}
