package tech.zumaran.genesis.constraint;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;

import tech.zumaran.genesis.GenesisEntity;
import tech.zumaran.genesis.exception.GenesisException;
import tech.zumaran.genesis.exception.UniqueConstraintException;
import tech.zumaran.genesis.function.InsertAllFunction;
import tech.zumaran.genesis.function.InsertFunction;

public interface UniqueConstraint<Entity extends GenesisEntity> {
	
	void flushRepository();

	Optional<Entity> findDuplicateEntry(Entity entity);
	
	default Entity uniqueInsert(InsertFunction<Entity> insertFunction, Entity entity) throws GenesisException {
		try {
			return insertFunction.apply(entity);
		} catch (DataIntegrityViolationException e) {
    		final var cause = e.getMostSpecificCause();
    		
    		if (cause instanceof SQLIntegrityConstraintViolationException) {
    			final var sqlViolation = (SQLIntegrityConstraintViolationException) cause;
    			
    			if (sqlViolation.getErrorCode() == 1062) {
    				Optional<Entity> maybeDuplicate = findDuplicateEntry(entity); 
    				if (maybeDuplicate.isPresent()) {
    					Entity duplicate = maybeDuplicate.get();
    					if (duplicate.isDeleted()) {
        					duplicate.setDeleted(false);
        					flushRepository();
        					return duplicate;
        				}
    				}
    			}
    			throw new UniqueConstraintException(sqlViolation.getErrorCode() + "", sqlViolation.getMessage());
    		}
			throw new UniqueConstraintException(e.getMessage());
		}
	}

	default List<Entity> uniqueInsertAll(InsertAllFunction<Entity> insertFunction, Collection<Entity> entities) throws GenesisException {
		try {
			return new ArrayList<Entity>(insertFunction.apply(entities));
		} catch (DataIntegrityViolationException e) {
    		final var cause = e.getMostSpecificCause();
    		
    		if (cause instanceof SQLIntegrityConstraintViolationException) {
    			final var sqlViolation = (SQLIntegrityConstraintViolationException) cause;
    			
    			if (sqlViolation.getErrorCode() == 1062) {
    				EntryWithDuplicates<Entity> entry = findDuplicateEntries(entities); 
    				
    				if (entry.newOnes.isEmpty()) 
    					throw new UniqueConstraintException("All entities are duplicates-");
    				
    				List<Entity> inserted = new ArrayList<Entity>(insertFunction.apply(entry.newOnes));
    				
    				entry.duplicates.stream()
    					.filter(d -> d.isDeleted())
    					.forEach(d -> d.setDeleted(false));
					flushRepository();
					
    				inserted.addAll(entry.duplicates);
					return inserted;
    			}
    			throw new UniqueConstraintException(sqlViolation.getErrorCode() + "", sqlViolation.getMessage());
    		}
			throw new UniqueConstraintException(e.getMessage());
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
	
	static class EntryWithDuplicates<E extends GenesisEntity> {
		final  List<E> newOnes;
		final List<E> duplicates;

		public EntryWithDuplicates(List<E> newOnes, List<E> duplicates) {
			this.newOnes = newOnes;
			this.duplicates = duplicates;
		}
	}
}
