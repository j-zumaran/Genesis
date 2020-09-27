package tech.zumaran.genesis.framework;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import tech.zumaran.genesis.exception.NotFoundException;
import tech.zumaran.genesis.exception.NotFoundInRecycleBin_Exception;
import tech.zumaran.genesis.response.ResponseFactory;

public abstract class GenesisController<Entity extends GenesisEntity> {

	@Autowired
	protected GenesisService<Entity> service;

	@Autowired
    protected ResponseFactory responseFactory;

	@GetMapping("/all")
    public ResponseEntity<List<Entity>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Entity> getById(@PathVariable long id) throws NotFoundException {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<?> insert(@RequestBody Entity entity) {
    	return responseFactory.created(service.insert(entity));
    }
    
    @PostMapping("/addall")
    public ResponseEntity<?> insertAll(@RequestBody List<Entity> entities) {
        return responseFactory.created(service.insertAll(entities));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Entity entity) throws NotFoundException {
        return responseFactory.updated(service.update(id, entity));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) throws NotFoundException {
        return responseFactory.deleted(service.delete(id));
    }
    
    @DeleteMapping("/delete/all")
    public ResponseEntity<?> deleteAll(@PathVariable List<Long> ids) {
        return responseFactory.deleted(service.deleteAllById(ids));
    }

    @DeleteMapping("/purge/{id}")
    public ResponseEntity<?> purge(@PathVariable long id) throws NotFoundInRecycleBin_Exception {
        return responseFactory.purged(service.purge(id));
    }
    
    @DeleteMapping("/purgeall/{id}")
    public ResponseEntity<?> purge(@PathVariable List<Long> ids) throws NotFoundInRecycleBin_Exception {
        return responseFactory.purged(service.purgeAllById(ids));
    }
    
    @GetMapping("/recyclebin")
    public ResponseEntity<List<Entity>> recycleBin() {
        return ResponseEntity.ok(service.recycleBin());
    }
}
