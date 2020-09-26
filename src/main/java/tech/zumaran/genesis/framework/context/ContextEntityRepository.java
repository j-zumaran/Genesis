package tech.zumaran.genesis.framework.context;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tech.zumaran.genesis.framework.GenesisRepository;

public interface ContextEntityRepository<Entity extends ContextEntity> extends GenesisRepository<Entity> {
	
	@Query(value = "SELECT * FROM #{#entityName} p WHERE p.context_id = :contextId AND p.deleted = '0'", nativeQuery = true)
	List<Entity> findAllByContextId(@Param("contextId") long contextId);
	
	@Query(value = "SELECT * FROM #{#entityName} p WHERE p.id = :id AND p.context_id = :contextId AND p.deleted = '0'", nativeQuery = true)
	Optional<Entity> findByIdAndContext(@Param("id") long id, @Param("contextId") long contextId);
}
