package tech.zumaran.genesis.constraint;

import java.util.List;

import javax.transaction.Transactional;

import tech.zumaran.genesis.GenesisEntity;
import tech.zumaran.genesis.GenesisService;
import tech.zumaran.genesis.exception.GenesisException;
import tech.zumaran.genesis.function.IDFunction;

public interface ChildConstraint<Parent extends GenesisEntity, Child extends GenesisEntity> {

	List<Child> getChildren(Parent parent);
	
	GenesisService<Child> childrenService();
	
	GenesisService<Parent> parentService();
	
	@Transactional
	default Parent deleteWithChildren(long id, IDFunction<Parent> deleteFunction) throws GenesisException {
		Parent deleted = deleteFunction.apply(id);
		childrenService().deleteAll(getChildren(deleted));
		return deleted;
	}
	
	@Transactional
	default Parent purgeWithChildren(long id, IDFunction<Parent> purgeFunction) throws GenesisException {
		Parent deleted = parentService().findInRecycleBinById(id);
		childrenService().purgeAll(getChildren(deleted));
		return purgeFunction.apply(id);
	}
}
