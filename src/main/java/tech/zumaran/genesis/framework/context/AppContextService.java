package tech.zumaran.genesis.framework.context;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import tech.zumaran.genesis.exception.NotFoundException;
import tech.zumaran.genesis.framework.preference.PreferenceKey;
import tech.zumaran.genesis.framework.preference.PreferenceKeyEntity;
import tech.zumaran.genesis.framework.preference.PreferenceKeyService;

public abstract class AppContextService<Context extends AppContext> {
	
	@Autowired
	private PreferenceKeyService preferenceKeyService;
	
	@Autowired 
	private ContextPreferenceRepository contextPreferencesRepo;

	@Autowired
	private AppContextRepository<Context> repository;
	
	protected abstract Context newAppContext(long userId);
	
	public Context findByContextId(long contextId) throws NotFoundException {
		Optional<Context> maybeFound = repository.findByContextId(contextId);
		if (maybeFound.isPresent()) {
			return maybeFound.get();
		} else 
			throw new NotFoundException(AppContext.class, contextId);
	}

	public ContextPreference findPreferenceByKey(PreferenceKey key, long contextId) throws NotFoundException {
		Context context = findByContextId(contextId);
		Map<PreferenceKey, ContextPreference> prefMap = context.getPreferences().stream()
				.collect(Collectors.toMap(p -> p.getKey(), p -> p));
		return prefMap.get(key);
	}
	
	@Transactional
	public Context registerAppContext(long userId) {
		Context newContext = newAppContext(userId);
		Context saved = repository.saveAndFlush(newContext);
		saved.setPreferences(generatePreferences(saved));
		contextPreferencesRepo.saveAll(saved.getPreferences());
		return saved;
	}

	protected ContextPreference createPreference(PreferenceKey key, Context context) {
		PreferenceKeyEntity prefKey = preferenceKeyService.findByKey(key);
		ContextPreference userPref = new ContextPreference(prefKey);
		userPref.setContext(context);
		userPref.setValue(prefKey.getDefaultValue());
		return userPref;
	}
	
	
	protected abstract Set<ContextPreference> generatePreferences(Context context);

}
