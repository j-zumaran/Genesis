package tech.zumaran.genesis.constraint;

import java.util.Collection;
import java.util.List;

import tech.zumaran.genesis.GenesisEntity;
import tech.zumaran.genesis.GenesisService;
import tech.zumaran.genesis.exception.GenesisException;
import tech.zumaran.genesis.exception.ParentConstraintException;
import tech.zumaran.genesis.function.InsertAllFunction;
import tech.zumaran.genesis.function.InsertFunction;

public interface ParentConstraint<Parent extends GenesisEntity, Child extends GenesisEntity> {
	
	Parent getParent(Child child);
	
	GenesisService<Parent> parentService();

	default Child verifyParentAndInsert(Child child, InsertFunction<Child> insertFunction) throws GenesisException {
		if (getParent(child) != null) {
			parentService().findById(getParent(child).getId());
			return insertFunction.apply(child);
		} else {
			throw new ParentConstraintException("Parent must not be null.");
		}
	}
	
	default List<Child> verifyParentAndInsertAll(Collection<Child> children, InsertAllFunction<Child> insertFunction) throws GenesisException {
		for (Child child: children) {
			if (getParent(child) != null) {
				parentService().findById(getParent(child).getId());
			} else {
				throw new ParentConstraintException("Parent must not be null.");
			}
		}
		return insertFunction.apply(children);
	}
}
