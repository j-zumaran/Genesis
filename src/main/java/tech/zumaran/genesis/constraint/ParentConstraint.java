package tech.zumaran.genesis.constraint;

import java.util.Collection;
import java.util.List;

import tech.zumaran.genesis.GenesisEntity;
import tech.zumaran.genesis.GenesisService;
import tech.zumaran.genesis.exception.GenesisException;
import tech.zumaran.genesis.exception.ParentConstraintException;
import tech.zumaran.genesis.function.InsertAllFunction;
import tech.zumaran.genesis.function.InsertFunction;

public interface ParentConstraint<P extends GenesisEntity, C extends GenesisEntity> {

	P getParent(C child);
	
	default C verifyParentAndInsert(GenesisService<P> parentService, InsertFunction<C> insertFunction, C child) throws GenesisException {
		if (getParent(child) != null) {
			parentService.findById(getParent(child).getId());
			return insertFunction.apply(child);
		}
		else throw new ParentConstraintException("Parent must not be null.");
	}
	
	default List<C> verifyParentAndInsertAll(GenesisService<P> parentService, InsertAllFunction<C> insertFunction, Collection<C> children) throws GenesisException {
		for (C child: children) {
			if (getParent(child) != null) {
				parentService.findById(getParent(child).getId());
			}
			else throw new ParentConstraintException("Parent must not be null.");
		}
		return insertFunction.apply(children);
	}
}
