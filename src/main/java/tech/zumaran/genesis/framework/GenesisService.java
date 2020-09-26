package tech.zumaran.genesis.framework;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import tech.zumaran.genesis.exception.NotFoundException;
import tech.zumaran.genesis.exception.NotFoundInRecycleBin_Exception;

public abstract class GenesisService<Entity extends GenesisEntity> {

    protected abstract Class<Entity> entityType();

    @Autowired
	protected GenesisRepository<Entity> repository;

    public List<Entity> findAll() {
        return repository.findAll();
    }
    
    public Entity findById(long id) throws NotFoundException {
        Optional<Entity> entity = repository.findById(id);
        if (entity.isPresent()) 
        	return entity.get();
        else 
        	throw new NotFoundException(entityType(), id);
    }

    @Transactional
    public List<Entity> insertAll(Collection<Entity> entities) {
    	return repository.saveAll(entities);
    }

    @Transactional
    public Entity insert(Entity entity) {
    	return repository.saveAndFlush(entity);
    }

    @Transactional
    public Entity update(Long id, Entity updated) throws NotFoundException {
        Entity old = findById(id);
        update(old, updated);
        repository.flush();
        return old;
    }

    protected abstract void update(Entity old, Entity updated);

    @Transactional
    public Entity delete(Long id) throws NotFoundException {
        Entity deleted = findById(id);
        deleted.setDeleted(true);
        repository.flush();
        return deleted;
    }
    
    @Transactional
    public List<Entity> deleteAllById(Collection<Long> ids) {
    	return deleteAll(repository.findAllById(ids));
    }
    
    @Transactional
    public List<Entity> deleteAll(List<Entity> entities) {
    	entities.forEach(e -> e.setDeleted(true));
        repository.flush();
        return entities;
    }
    
    @Transactional
    public Entity recover(Long id) throws NotFoundInRecycleBin_Exception {
    	Optional<Entity> maybeDeleted = repository.findInRecycleBinById(id);
    	if (maybeDeleted.isPresent()) {
    		maybeDeleted.get().setDeleted(false);
            repository.flush();
            return maybeDeleted.get();
    	} else {
    		throw new NotFoundInRecycleBin_Exception(entityType(), id);
    	}
        
    }
    
    @Transactional
    public List<Entity> recoverAllById(Collection<Long> ids) {
    	return recoverAll(repository.findAllById(ids));
    }
    
    @Transactional
    public List<Entity> recoverAll(List<Entity> entities) {
    	entities.forEach(e -> e.setDeleted(false));
        repository.flush();
        return entities;
    }

    @Transactional
    public Entity purge(Long id) throws NotFoundInRecycleBin_Exception {
    	Optional<Entity> maybeDeleted = repository.findInRecycleBinById(id);
    	
        if (maybeDeleted.isPresent()) {
        	Entity purged = maybeDeleted.get();
    		repository.delete(purged);
    		return purged;
        } else 
        	throw new NotFoundInRecycleBin_Exception(entityType(), id);
    }
    
    @Transactional
    public List<Entity> purgeAllById(Collection<Long> ids) throws NotFoundInRecycleBin_Exception {
    	List<Entity> entities = repository.findAllById(ids);
    	purgeAll(entities);
    	return entities;
    }
    
    @Transactional
    public void purgeAll(List<Entity> entities) throws NotFoundInRecycleBin_Exception {
    	Optional<Entity> notDeleted = entities.stream().filter(e -> !e.isDeleted()).findFirst();
    	if (notDeleted.isPresent()) {
    		throw new NotFoundInRecycleBin_Exception(entityType(), notDeleted.get().getId());
    	}
        repository.deleteAll(entities);
	}
    
    public List<Entity> recycleBin() {
    	return repository.findAllRecycleBin();
    }
}
