package tech.zumaran.genesis.response;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import tech.zumaran.genesis.GenesisEntity;
import tech.zumaran.genesis.exception.NotFoundException;

@Component
public class ResponseFactory {
	
	private static URI getCurrentURI() {
		return ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUri();
	}
	
	//=============================================================================================================

	public ResponseEntity<?> notFound() {
		return ResponseEntity.notFound().build();
	}
	
	public ResponseEntity<?> notFound(NotFoundException e) {
		return new NotFoundResponse(getCurrentURI(), e).createResponse();
	}
	
	//============================================================================================================

	public ResponseEntity<?> created(GenesisEntity entity) {
		return new InsertedResponse(entity).createResponse();
	}
	
	public ResponseEntity<?> created(List<? extends GenesisEntity> entities) {
		return new InsertedAllResponse(entities).createResponse();
	}
	
	public ResponseEntity<?> updated(GenesisEntity updated) {
		return new UpdatedResponse(updated).createResponse();
	}
	
	public ResponseEntity<?> deleted(GenesisEntity deleted) {
		return new DeletedResponse(deleted).createResponse();
	}
	
	public ResponseEntity<?> deleted(List<? extends GenesisEntity> deleted) {
		return new DeletedAllResponse(deleted).createResponse();
	}
	
	public ResponseEntity<?> purged(GenesisEntity entity) {
		return new PurgedResponse(entity).createResponse();
	}
	
	public ResponseEntity<?> purged(List<? extends GenesisEntity> purged) {
		return new PurgedAllResponse(purged).createResponse();
	}
	
	//============================================================================================
	
	public ResponseEntity<?> exception(HttpStatus status, Exception e) {
		return new ExceptionResponse(status, getCurrentURI(), e).createResponse();
	}
	
	public ResponseEntity<ExceptionResponse> forbidden() {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}
	
	//==============================================================================================================
	
	/*public ResponseEntity<ExceptionResponse> unauthorized(String error) {
		ExceptionResponse response = new ExceptionResponse(HttpStatus.UNAUTHORIZED, error, currentPath.get().toString());
		LOG.info("{}", response);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	}*/

}
