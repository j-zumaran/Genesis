package tech.zumaran.genesis.framework.context;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import tech.zumaran.genesis.exception.NotFoundException;

public abstract class ContextEntityService<Context extends AppContext, Entity extends ContextEntity> {

	@Autowired
	private AppContextService<? extends Context> appContextService;
	 
    @Autowired
    private ContextEntityRepository<Entity> entityRepository;
    
    protected abstract Class<Entity> entityType();
    
    private Context getContext(long contextId) throws NotFoundException {
    	return appContextService.findByContextId(contextId);
    }

    public List<Entity> findAll(long contextId) {
        return entityRepository.findAllByContextId(contextId);
    }

    public Entity findById(long id, long contextId) throws NotFoundException {
        Optional<Entity> entityOpt = entityRepository.findByIdAndContext(id, contextId);
        if (entityOpt.isPresent()) 
        	return entityOpt.get();
        else 
        	throw new NotFoundException(entityType(), id);
    }

    @Transactional
    public Entity insert(Entity entity, long contextId) throws NotFoundException {
        entity.setContext(getContext(contextId));
        return entityRepository.save(entity);
    }

    @Transactional
    public List<Entity> insertAll(Collection<Entity> entities, long contextId) throws NotFoundException {
        Context context = getContext(contextId);
        entities.forEach(e -> e.setContext(context));
        return entityRepository.saveAll(entities);
    }

    @Transactional
    public Entity update(Long id, long contextId, Entity updated) throws NotFoundException {
        Entity e = findById(id, contextId);
        update(e, updated);
        entityRepository.flush();
        return e;
    }
    
    protected abstract void update(Entity old, Entity updated);

    @Transactional
    public Entity delete(Long id, long contextId) throws NotFoundException {
		Entity e = findById(id, contextId);
		e.setDeleted(true);
		entityRepository.flush();
		return e;
	}
}
