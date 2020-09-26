package tech.zumaran.genesis.framework.context;

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
import tech.zumaran.genesis.response.ResponseFactory;

public abstract class ContextEntityController<Context extends AppContext, Entity extends ContextEntity> {

	@Autowired
	private ContextEntityService<Context, Entity> service;

	@Autowired
    private ResponseFactory responseFactory;

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
    	return responseFactory.created(service.insert(entity, contextId));
    }
    
    @PostMapping("/addall")
    public ResponseEntity<?> insertAll(@RequestBody List<Entity> entities, @RequestAttribute long contextId) throws NotFoundException {
        return responseFactory.created(service.insertAll(entities, contextId));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestAttribute long contextId, @RequestBody Entity entity) throws NotFoundException {
        return responseFactory.updated(service.update(id, contextId, entity));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable long id, @RequestAttribute long contextId) throws NotFoundException {
        return responseFactory.deleted(service.delete(id, contextId));
    }
    
    /*@DeleteMapping("/delete/all")
    public ResponseEntity<?> deleteAll(@PathVariable List<Long> ids, @RequestAttribute long contextId) {
        return responseFactory.deleted(service.deleteAllById(ids, contextId));
    }

    @DeleteMapping("/purge/{id}")
    public ResponseEntity<?> purge(@PathVariable long id, @RequestAttribute long contextId) throws NotFoundInRecycleBin_Exception {
        return responseFactory.purged(service.purge(id, contextId));
    }
    
    @GetMapping("/recyclebin")
    public ResponseEntity<List<Entity>> recycleBin() {
        return ResponseEntity.ok(service.recycleBin());
    }*/
}
