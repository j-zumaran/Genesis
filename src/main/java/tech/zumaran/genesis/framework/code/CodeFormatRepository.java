package tech.zumaran.genesis.framework.code;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tech.zumaran.genesis.framework.GenesisRepository;

public interface CodeFormatRepository extends GenesisRepository<CodeFormat> {

	@Query(value = "SELECT * FROM code_format p WHERE p.type = :type AND p.deleted = 'FALSE'", nativeQuery = true)
	Optional<CodeFormat> findByType(@Param("type") String type);
}
