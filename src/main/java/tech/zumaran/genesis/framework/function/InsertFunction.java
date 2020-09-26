package tech.zumaran.genesis.framework.function;

import tech.zumaran.genesis.exception.GenesisException;
import tech.zumaran.genesis.framework.GenesisEntity;

public interface InsertFunction<E extends GenesisEntity> {
	E apply(E entity) throws GenesisException;
}
