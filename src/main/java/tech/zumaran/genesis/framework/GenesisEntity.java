package tech.zumaran.genesis.framework;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@MappedSuperclass
public abstract class GenesisEntity implements Serializable {
	
	private static final long serialVersionUID = -2687522230964778557L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter private Long id;
	
	@ColumnDefault("false")
	@Column(nullable = false)
	@Setter private boolean deleted = false;
	
	@JsonIgnore
	public boolean isDeleted() {
		return deleted;
	}

	@JsonIgnore
	public String getType() {
		return getClass().getSimpleName();
	}
}
