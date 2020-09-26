package tech.zumaran.genesis.framework.function;

import tech.zumaran.genesis.exception.GenesisException;
import tech.zumaran.genesis.framework.GenesisEntity;

public interface IDFunction<Entity extends GenesisEntity> {
	Entity apply(long id) throws GenesisException;
}
