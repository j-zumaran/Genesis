package tech.zumaran.genesis.framework.preference;

import tech.zumaran.genesis.framework.GenesisRepository;
import tech.zumaran.genesis.framework.query.FindByNameQuery;

public interface PreferenceKeyRepository extends GenesisRepository<PreferenceKeyEntity>, FindByNameQuery<PreferenceKeyEntity> {

}
