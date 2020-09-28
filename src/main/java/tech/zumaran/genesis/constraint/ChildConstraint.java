package tech.zumaran.genesis.constraint;

import java.util.List;

import javax.transaction.Transactional;

import tech.zumaran.genesis.GenesisEntity;
import tech.zumaran.genesis.GenesisRepository;
import tech.zumaran.genesis.GenesisService;
import tech.zumaran.genesis.exception.GenesisException;

public interface ChildConstraint
			<Parent extends GenesisEntity, 
			ParentRepository extends GenesisRepository<Parent>,
			ParentService extends GenesisService<Parent, ParentRepository>,
			Child extends GenesisEntity,
			ChildRepository extends GenesisRepository<Child>,
			ChildService extends GenesisService<Child, ChildRepository>> {

	List<Child> getChildren(Parent parent);
	
	ChildService childrenService();
	
	ParentService parentService();
	
	@Transactional
	default Parent deleteWithChildren(long id) throws GenesisException {
		Parent deleted = parentService().delete(id);
		childrenService().deleteAll(getChildren(deleted));
		return deleted;
	}
	
	@Transactional
	default Parent purgeWithChildren(long id) throws GenesisException {
		Parent deleted = parentService().findInRecycleBinById(id);
		childrenService().purgeAll(getChildren(deleted));
		return parentService().purge(id);
	}
}
