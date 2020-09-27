package tech.zumaran.genesis.function;

import tech.zumaran.genesis.GenesisEntity;
import tech.zumaran.genesis.exception.GenesisException;

public interface InsertFunction<E extends GenesisEntity> {
	E apply(E entity) throws GenesisException;
}
