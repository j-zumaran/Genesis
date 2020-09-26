package tech.zumaran.genesis.framework.preference;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tech.zumaran.genesis.framework.GenesisService;

@Service
public class PreferenceKeyService extends GenesisService<PreferenceKeyEntity> {
	
	@Autowired
	private PreferenceKeyRepository repository;
	
	public PreferenceKeyEntity findByKey(PreferenceKey key) {
		Optional<PreferenceKeyEntity> pref = repository.findByName(key.getKeyName());
		if (pref.isPresent()) 
			return pref.get();
		else {
			return insert(new PreferenceKeyEntity(key));
		}
	}

	@Override
	protected Class<PreferenceKeyEntity> entityType() {
		return PreferenceKeyEntity.class;
	}

	@Override
	protected void update(PreferenceKeyEntity old, PreferenceKeyEntity updated) {
		// TODO Auto-generated method stub
		
	}

}
