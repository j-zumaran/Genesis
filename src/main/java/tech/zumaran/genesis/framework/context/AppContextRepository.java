package tech.zumaran.genesis.framework.context;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tech.zumaran.genesis.framework.GenesisRepository;

public interface AppContextRepository<Context extends AppContext> extends GenesisRepository<Context> {

    @Query(value = "SELECT * FROM context p WHERE p.context_id = :contextId", nativeQuery = true)
    Optional<Context> findByContextId(@Param("contextId") long contextId);

}
