package tech.zumaran.genesis;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import tech.zumaran.genesis.exception.GenesisException;
import tech.zumaran.genesis.exception.NotFoundException;
import tech.zumaran.genesis.exception.NotFoundInRecycleBin_Exception;

public abstract class GenesisService<Entity extends GenesisEntity> {

    protected abstract Class<Entity> entityType();

    @Autowired
	protected GenesisRepository<Entity> repository;

    @Transactional(readOnly = true)
    public List<Entity> findAll() {
        return repository.findAll();
    }
    
    @Transactional(readOnly = true, noRollbackFor = NotFoundException.class)
    public Entity findById(long id) throws NotFoundException {
        Optional<Entity> entity = repository.findById(id);
        if (entity.isPresent()) 
        	return entity.get();
        else 
        	throw new NotFoundException(entityType(), id);
    }

    @Transactional
    public Entity insert(Entity entity) throws GenesisException {
    	return repository.saveAndFlush(entity);
    }

    @Transactional
    public List<Entity> insertAll(Collection<Entity> entities) throws GenesisException {
    	return repository.saveAll(entities);
    }
    
    protected abstract void update(Entity old, Entity updated);

    @Transactional(noRollbackFor = NotFoundException.class)
    public Entity update(Long id, Entity updated) throws NotFoundException {
        Entity old = findById(id);
        update(old, updated);
        repository.flush();
        return old;
    }

    @Transactional(noRollbackFor = NotFoundException.class)
    public Entity delete(Long id) throws NotFoundException {
        Entity deleted = findById(id);
        deleted.setDeleted(true);
        return deleted;
    }
    
    @Transactional
    public List<Entity> deleteAllById(Collection<Long> ids) {
    	return deleteAll(repository.findAllById(ids));
    }
    
    @Transactional
    public List<Entity> deleteAll(List<Entity> entities) {
    	entities.forEach(e -> e.setDeleted(true));
    	//repository.flush();
        return entities;
    }
    
    @Transactional(noRollbackFor = NotFoundInRecycleBin_Exception.class)
    public Entity recover(Long id) throws NotFoundInRecycleBin_Exception {
		Entity deleted = findInRecycleBinById(id);
		deleted.setDeleted(false);
        return deleted;
    }
    
    @Transactional
    public List<Entity> recoverAllById(Collection<Long> ids) {
    	return recoverAll(repository.findAllById(ids));
    }
    
    @Transactional
    public List<Entity> recoverAll(List<Entity> entities) {
    	entities.forEach(e -> e.setDeleted(false));
        //repository.flush();
        return entities;
    }

    @Transactional(noRollbackFor = NotFoundInRecycleBin_Exception.class)
    public Entity purge(Long id) throws NotFoundInRecycleBin_Exception {
    	Entity purged = findInRecycleBinById(id);
		repository.delete(purged);
		return purged;
    }
    
    @Transactional(noRollbackFor = NotFoundInRecycleBin_Exception.class)
    public List<Entity> purgeAllById(Collection<Long> ids) throws NotFoundInRecycleBin_Exception {
    	List<Entity> entities = repository.findAllById(ids);
    	purgeAll(entities);
    	return entities;
    }
    
    @Transactional(noRollbackFor = NotFoundInRecycleBin_Exception.class)
    public void purgeAll(List<Entity> entities) throws NotFoundInRecycleBin_Exception {
    	Optional<Entity> notDeleted = entities.stream().filter(e -> !e.isDeleted()).findFirst();
    	
    	if (notDeleted.isPresent()) {
    		throw new NotFoundInRecycleBin_Exception(entityType(), notDeleted.get().getId());
    	}
        repository.deleteAll(entities);
	}
    
    @Transactional(readOnly = true)
    public List<Entity> recycleBin() {
    	return repository.findAllRecycleBin();
    }
    
    @Transactional(readOnly = true)
    public Entity findInRecycleBinById(long id) throws NotFoundInRecycleBin_Exception {
    	Optional<Entity> maybeEntity = repository.findInRecycleBinById(id);
    	if (maybeEntity.isPresent())
    		return maybeEntity.get();
    	else
    		throw new NotFoundInRecycleBin_Exception(entityType(), id);
    }
}
