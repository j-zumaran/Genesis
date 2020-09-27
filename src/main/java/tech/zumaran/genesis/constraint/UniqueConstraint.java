package tech.zumaran.genesis.constraint;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;

import lombok.AllArgsConstructor;
import tech.zumaran.genesis.GenesisEntity;
import tech.zumaran.genesis.exception.GenesisException;
import tech.zumaran.genesis.exception.UniqueConstraintException;
import tech.zumaran.genesis.function.InsertAllFunction;
import tech.zumaran.genesis.function.InsertFunction;

public interface UniqueConstraint<Entity extends GenesisEntity> {
	
	void flushRepository();

	Optional<Entity> findDuplicateEntry(Entity entity);
	
	default Entity uniqueInsert(Entity entity, InsertFunction<Entity> insertFunction) throws GenesisException {
		try {
			return insertFunction.apply(entity);
		} catch (DataIntegrityViolationException e) {
    		final var cause = e.getMostSpecificCause();
    		
    		if (cause instanceof SQLIntegrityConstraintViolationException) {
    			final var sqlViolation = (SQLIntegrityConstraintViolationException) cause;
    			
    			if (sqlViolation.getErrorCode() != 1062)
    				throw new UniqueConstraintException(sqlViolation.getErrorCode() + "", sqlViolation.getMessage());
    			
    			Optional<Entity> maybeDuplicate = findDuplicateEntry(entity); 
    			
    			if (maybeDuplicate.isEmpty())
    				throw new UniqueConstraintException(sqlViolation.getErrorCode() + "", sqlViolation.getMessage());
				
    			Entity duplicate = maybeDuplicate.get();
				
				if (duplicate.isDeleted()) {
					duplicate.setDeleted(false);
					flushRepository();
					return duplicate;
				} else {
					return duplicate;
				}
    		} else {
    			throw new UniqueConstraintException(e.getMessage());
    		}
		}
	}

	default List<Entity> uniqueInsertAll(Collection<Entity> entities, InsertAllFunction<Entity> insertFunction) throws GenesisException {
		if (entities.isEmpty()) return List.of();
		
		try {
			return new ArrayList<Entity>(insertFunction.apply(entities));
		} catch (DataIntegrityViolationException e) {
    		final var cause = e.getMostSpecificCause();
    		
    		if (cause instanceof SQLIntegrityConstraintViolationException) {
    			final var sqlViolation = (SQLIntegrityConstraintViolationException) cause;
    			
    			if (sqlViolation.getErrorCode() != 1062) 
    				throw new UniqueConstraintException(sqlViolation.getErrorCode() + "", sqlViolation.getMessage());
    			
    			EntryWithDuplicates<Entity> entry = findDuplicateEntries(entities); 
				
				if (entry.newOnes.isEmpty()) 
					throw new UniqueConstraintException("All entities are duplicates");
				
				List<Entity> inserted = new ArrayList<Entity>(insertFunction.apply(entry.newOnes));
				
				entry.duplicates.stream()
					.filter(d -> d.isDeleted())
					.forEach(d -> d.setDeleted(false));
				flushRepository();
				
				inserted.addAll(entry.duplicates);
				return inserted;
    		} else {
    			throw new UniqueConstraintException(e.getMessage());
    		}
		}
	}

	default EntryWithDuplicates<Entity> findDuplicateEntries(Collection<Entity> entities) {
		ArrayList<Entity> duplicates = new ArrayList<Entity>();
		ArrayList<Entity> newOnes = new ArrayList<Entity>();

		for (Entity entity: entities) {
			Optional<Entity> maybeDuplicate = findDuplicateEntry(entity);
			if (maybeDuplicate.isPresent()) {
				duplicates.add(maybeDuplicate.get());
			} else {
				newOnes.add(entity);
			}
		}
		return new EntryWithDuplicates<Entity>(newOnes, duplicates);
	}
	
	@AllArgsConstructor
	static class EntryWithDuplicates<E extends GenesisEntity> {
		final List<E> newOnes;
		final List<E> duplicates;
	}
}
