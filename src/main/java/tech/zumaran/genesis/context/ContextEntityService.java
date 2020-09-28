package tech.zumaran.genesis.context;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import tech.zumaran.genesis.exception.NotFoundException;
import tech.zumaran.genesis.exception.NotFoundInRecycleBin_Exception;

public abstract class ContextEntityService
			<Context extends GenesisContext, 
			Entity extends ContextEntity<Context>,
			Repository extends ContextEntityRepository<Context, Entity>> {
	 
    @Autowired
    protected Repository repository;
    
    protected abstract Class<Entity> entityType();
    
    @Transactional(readOnly = true)
    public List<Entity> findAll(long contextId) {
        return repository.findAllByContextId(contextId);
    }

    @Transactional(readOnly = true, noRollbackFor = NotFoundException.class)
    public Entity findById(long id, long contextId) throws NotFoundException {
        Optional<Entity> entityOpt = repository.findByIdAndContextId(id, contextId);
        if (entityOpt.isPresent()) 
        	return entityOpt.get();
        else 
        	throw new NotFoundException(entityType(), id);
    }

    @Transactional
    public Entity insert(Entity entity, Context context) {
        entity.setContext(context);
        return repository.save(entity);
    }
    
    @Transactional
    public List<Entity> insertAll(Collection<Entity> entities, Context context) {
        entities.forEach(e -> e.setContext(context));
        return repository.saveAll(entities);
    }
    
    protected abstract void update(Entity old, Entity updated);

    @Transactional(noRollbackFor = NotFoundException.class)
    public Entity update(Long id, long contextId, Entity updated) throws NotFoundException {
        Entity e = findById(id, contextId);
        update(e, updated);
        repository.flush();
        return e;
    }

    @Transactional(noRollbackFor = NotFoundException.class)
    public Entity delete(Long id, long contextId) throws NotFoundException {
		Entity e = findById(id, contextId);
		e.setDeleted(true);
		//entityRepository.flush();
		return e;
	}
    
    @Transactional
	public List<Entity> deleteAllById(List<Long> ids, long contextId) {
    	List<Entity> found = findAll(contextId).stream()
    			.filter(e -> ids.contains(e.getId()))
    			.collect(Collectors.toList());
    	
		return deleteAll(found);
	}
	
	@Transactional
    protected List<Entity> deleteAll(List<Entity> entities) {
    	entities.forEach(e -> e.setDeleted(true));
    	//repository.flush();
        return entities;
    }
    
    @Transactional(noRollbackFor = NotFoundInRecycleBin_Exception.class)
    public Entity recover(Long id, long contextId) throws NotFoundInRecycleBin_Exception {
		Entity e = findInRecycleBinById(id, contextId);
		e.setDeleted(false);
		//entityRepository.flush();
		return e;
	}
    
    @Transactional
	public List<Entity> recoverAllById(List<Long> ids, long contextId) {
    	List<Entity> foundInRecycleBin = recycleBin(contextId).stream()
    			.filter(e -> ids.contains(e.getId()))
    			.collect(Collectors.toList());
    	
		return deleteAll(foundInRecycleBin);
	}
	
	@Transactional
    protected List<Entity> recoverAll(List<Entity> entities) {
    	entities.forEach(e -> e.setDeleted(false));
    	//repository.flush();
        return entities;
    }
    
    @Transactional(readOnly = true)
    public List<Entity> recycleBin(long contextId) {
    	return repository.findAllRecycleBin(contextId);
    }
    
    @Transactional(readOnly = true, noRollbackFor = NotFoundInRecycleBin_Exception.class)
    public Entity findInRecycleBinById(long id, long contextId) throws NotFoundInRecycleBin_Exception {
    	Optional<Entity> maybeDeleted = repository.findInRecycleBinById(id, contextId);
    	if (maybeDeleted.isPresent())
    		return maybeDeleted.get();
    	else 
    		throw new NotFoundInRecycleBin_Exception(entityType(), id);
    }

}
