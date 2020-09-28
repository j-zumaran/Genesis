package tech.zumaran.genesis.context;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;

import tech.zumaran.genesis.exception.NotFoundException;
import tech.zumaran.genesis.exception.NotFoundInRecycleBin_Exception;
import tech.zumaran.genesis.response.ResponseFactory;

public abstract class ContextEntityController
			<Context extends GenesisContext,
			ContextRepository extends GenesisContextRepository<Context>,
			ContextService extends GenesisContextService<Context, ContextRepository>,
			Entity extends ContextEntity<Context>,
			Repository extends ContextEntityRepository<Context, Entity>,
			Service extends ContextEntityService<Context, Entity, Repository>> {

	@Autowired
	protected Service service;
	
	@Autowired
	protected ContextService contextService;

	@Autowired
	protected ResponseFactory responseFactory;

	@GetMapping("/all")
    public ResponseEntity<List<Entity>> findAll(@RequestAttribute long contextId) {
        return ResponseEntity.ok(service.findAll(contextId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Entity> getById(@PathVariable long id, @RequestAttribute long contextId) throws NotFoundException {
        return ResponseEntity.ok(service.findById(id, contextId));
    }

    @PostMapping("/add")
    public ResponseEntity<?> insert(@RequestBody Entity entity, @RequestAttribute long contextId) throws NotFoundException {
    	Context context = contextService.findByContextId(contextId);
    	return responseFactory.created(service.insert(entity, context));
    }
    
    @PostMapping("/addall")
    public ResponseEntity<?> insertAll(@RequestBody List<Entity> entities, @RequestAttribute long contextId) throws NotFoundException {
    	Context context = contextService.findByContextId(contextId);
    	return responseFactory.created(service.insertAll(entities, context));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestAttribute long contextId, @RequestBody Entity entity) throws NotFoundException {
        return responseFactory.updated(service.update(id, contextId, entity));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable long id, @RequestAttribute long contextId) throws NotFoundException {
        return responseFactory.deleted(service.delete(id, contextId));
    }
    
    @DeleteMapping("/delete/all")
    public ResponseEntity<?> deleteAll(@PathVariable List<Long> ids, @RequestAttribute long contextId) {
        return responseFactory.deleted(service.deleteAllById(ids, contextId));
    }
    
    @PutMapping("/recover/{id}")
    public ResponseEntity<?> recover(@PathVariable long id, @RequestAttribute long contextId) throws NotFoundInRecycleBin_Exception {
        return responseFactory.recovered(service.recover(id, contextId));
    }
    
    @PutMapping("/recover/all")
    public ResponseEntity<?> recoverAll(@RequestBody List<Long> ids, @RequestAttribute long contextId) {
        return responseFactory.recovered(service.recoverAllById(ids, contextId));
    }
    
    @GetMapping("/recyclebin")
    public ResponseEntity<List<Entity>> recycleBin(@RequestAttribute long contextId) {
        return ResponseEntity.ok(service.recycleBin(contextId));
    }
    
    @GetMapping("/recyclebin/{id}")
    public ResponseEntity<Entity> recycleBinById(@PathVariable long id, @RequestAttribute long contextId) throws NotFoundInRecycleBin_Exception {
        return ResponseEntity.ok(service.findInRecycleBinById(id, contextId));
    }
}
