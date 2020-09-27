package tech.zumaran.genesis.function;

import tech.zumaran.genesis.GenesisEntity;
import tech.zumaran.genesis.exception.GenesisException;

public interface IDFunction<Entity extends GenesisEntity> {
	Entity apply(long id) throws GenesisException;
}
