package tech.zumaran.genesis.framework.code;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import tech.zumaran.genesis.exception.NotFoundException;
import tech.zumaran.genesis.framework.context.AppContext;
import tech.zumaran.genesis.framework.context.AppContextService;
import tech.zumaran.genesis.framework.context.ContextPreference;
import tech.zumaran.genesis.framework.preference.DefaultPreference;

public abstract class CodeEntityService<C extends CodeEntity> {
	
	@Autowired
	private CodeFormatRepository formatRepository;
	
	@Autowired
	private AppContextService<? extends AppContext> appContextService;
	
	/*@Override
	public C insert(C entity) throws Transaction_Exception {
		entity.setCodeFormat(findFormat(entity.getClass(), contextId));
		return super.insert(entity);
	}
	
	@Override
	public List<C> insertAll(Collection<C> entities) throws Transaction_Exception {
		if (!entities.isEmpty()) {
			for (C entity: entities) {
				entity.setCodeFormat(findFormat(entity.getClass(), contextId));
			}
			return super.insertAll(entities);
		} else {
			return new ArrayList<C>();
		}
	}*/
	
	private CodeFormat findFormat(Class<?> type, long contextId) throws NotFoundException {
		Optional<CodeFormat> maybeFormat = formatRepository.findByType(type.getSimpleName());
		if (maybeFormat.isEmpty()) {
			ContextPreference format = appContextService.findPreferenceByKey(DefaultPreference.CODE_FORMAT, contextId);
			return formatRepository.saveAndFlush(CodeFormat.from(type.getSimpleName(), format.getValue()));
		} else {
			return maybeFormat.get();
		}
	}
}
