package tech.zumaran.genesis.context;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import tech.zumaran.genesis.GenesisEntity;

@ToString
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public abstract class ContextEntity<Context extends GenesisContext> extends GenesisEntity {
	
	private static final long serialVersionUID = 6275836790674590500L;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@Setter private Context context;
	
	@JsonIgnore
	public Context getContext() {
		return context;
	}

}
