package tech.zumaran.genesis.function;

import java.util.Collection;
import java.util.List;

import tech.zumaran.genesis.GenesisEntity;
import tech.zumaran.genesis.exception.GenesisException;

public interface InsertAllFunction<E extends GenesisEntity> {
	List<E> apply(Collection<E> all) throws GenesisException;
}
