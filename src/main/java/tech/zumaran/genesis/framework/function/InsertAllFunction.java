package tech.zumaran.genesis.framework.function;

import java.util.Collection;
import java.util.List;

import tech.zumaran.genesis.exception.GenesisException;
import tech.zumaran.genesis.framework.GenesisEntity;

public interface InsertAllFunction<E extends GenesisEntity> {
	List<E> apply(Collection<E> all) throws GenesisException;
}
