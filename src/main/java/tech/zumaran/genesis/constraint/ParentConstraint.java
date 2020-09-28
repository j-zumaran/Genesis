package tech.zumaran.genesis.constraint;

import java.util.Collection;
import java.util.List;

import tech.zumaran.genesis.GenesisEntity;
import tech.zumaran.genesis.GenesisRepository;
import tech.zumaran.genesis.GenesisService;
import tech.zumaran.genesis.exception.GenesisException;
import tech.zumaran.genesis.exception.ParentConstraintException;

public interface ParentConstraint
			<Parent extends GenesisEntity, 
			ParentRepository extends GenesisRepository<Parent>,
			ParentService extends GenesisService<Parent, ParentRepository>,
			Child extends GenesisEntity,
			ChildRepository extends GenesisRepository<Child>,
			ChildService extends GenesisService<Child, ChildRepository>> {
	
	Parent getParent(Child child);
	
	ParentService parentService();
	
	ChildService childService();

	default Child verifyParentAndInsert(Child child) throws GenesisException {
		if (getParent(child) != null) {
			parentService().findById(getParent(child).getId());
			return childService().insert(child);
		} else {
			throw new ParentConstraintException("Parent must not be null.");
		}
	}
	
	default List<Child> verifyParentAndInsertAll(Collection<Child> children) throws GenesisException {
		for (Child child: children) {
			if (getParent(child) != null) {
				parentService().findById(getParent(child).getId());
			} else {
				throw new ParentConstraintException("Parent must not be null.");
			}
		}
		return childService().insertAll(children);
	}
}
