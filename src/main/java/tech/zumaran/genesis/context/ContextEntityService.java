package tech.zumaran.genesis.context;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import tech.zumaran.genesis.exception.NotFoundException;

public abstract class ContextEntityService<Context extends GenesisContext, Entity extends ContextEntity<Context>> {

	@Autowired
	protected GenesisContextService<Context> appContextService;
	 
    @Autowired
    protected ContextEntityRepository<Context, Entity> entityRepository;
    
    protected abstract Class<Entity> entityType();
    
    @Transactional
    private Context getContext(long contextId) throws NotFoundException {
    	return appContextService.findByContextId(contextId);
    }

    @Transactional(readOnly = true)
    public List<Entity> findAll(long contextId) {
        return entityRepository.findAllByContextId(contextId);
    }

    @Transactional(readOnly = true, noRollbackFor = NotFoundException.class)
    public Entity findById(long id, long contextId) throws NotFoundException {
        Optional<Entity> entityOpt = entityRepository.findByIdAndContext(id, contextId);
        if (entityOpt.isPresent()) 
        	return entityOpt.get();
        else 
        	throw new NotFoundException(entityType(), id);
    }

    @Transactional(noRollbackFor = NotFoundException.class)
    public Entity insert(Entity entity, long contextId) throws NotFoundException {
        entity.setContext(getContext(contextId));
        return entityRepository.save(entity);
    }

    @Transactional(noRollbackFor = NotFoundException.class)
    public List<Entity> insertAll(Collection<Entity> entities, long contextId) throws NotFoundException {
        Context context = getContext(contextId);
        entities.forEach(e -> e.setContext(context));
        return entityRepository.saveAll(entities);
    }
    
    protected abstract void update(Entity old, Entity updated);

    @Transactional(noRollbackFor = NotFoundException.class)
    public Entity update(Long id, long contextId, Entity updated) throws NotFoundException {
        Entity e = findById(id, contextId);
        update(e, updated);
        entityRepository.flush();
        return e;
    }

    @Transactional(noRollbackFor = NotFoundException.class)
    public Entity delete(Long id, long contextId) throws NotFoundException {
		Entity e = findById(id, contextId);
		e.setDeleted(true);
		//entityRepository.flush();
		return e;
	}
    
    @Transactional(readOnly = true)
    public List<Entity> recycleBin(long contextId) {
    	return entityRepository.findAllRecycleBin(contextId);
    }
}
