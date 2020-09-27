package tech.zumaran.genesis.constraint;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import tech.zumaran.genesis.GenesisEntity;
import tech.zumaran.genesis.GenesisRepository;
import tech.zumaran.genesis.GenesisService;
import tech.zumaran.genesis.exception.ChildConstraintException;
import tech.zumaran.genesis.exception.GenesisException;
import tech.zumaran.genesis.function.IDFunction;

public interface ChildConstraint<E extends GenesisEntity, C extends GenesisEntity> {

	List<C> getChildren(E parent);
	
	@Transactional
	default E deleteWithChildren(IDFunction<E> deleteFunction, long id, GenesisService<C> childrenService) throws GenesisException {
		E deleted = deleteFunction.apply(id);
		childrenService.deleteAll(getChildren(deleted));
		return deleted;
	}
	
	@Transactional
	default E purgeWithChildren(long id, GenesisRepository<E> parentRepository, 
			GenesisService<C> childrenService) throws GenesisException {
		
		Optional<E> maybeDeleted = parentRepository.findInRecycleBinById(id);
		if (maybeDeleted.isPresent()) {
			E deleted = maybeDeleted.get();
			childrenService.purgeAll(getChildren(deleted));
			parentRepository.delete(deleted);
			return deleted;
		} else {
			throw new ChildConstraintException("Cannot purge entity ID", id + ". Not found or not marked as deleted.");
		}
	}
}
