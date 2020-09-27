package tech.zumaran.genesis.context;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import tech.zumaran.genesis.exception.NotFoundException;

public abstract class GenesisContextService<Context extends GenesisContext> {

	@Autowired
	protected GenesisContextRepository<Context> repository;
	
	protected abstract Context newContext(long userId);
	
	@Transactional(readOnly = true)
	public Context findByContextId(long contextId) throws NotFoundException {
		Optional<Context> maybeFound = repository.findByContextId(contextId);
		if (maybeFound.isPresent()) {
			return maybeFound.get();
		} else 
			throw new NotFoundException(GenesisContext.class, contextId);
	}
	
	@Transactional
	public Context registerContext(long userId) {
		return repository.saveAndFlush(newContext(userId));
	}
}
