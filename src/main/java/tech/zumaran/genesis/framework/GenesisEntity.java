package tech.zumaran.genesis.framework;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
public abstract class GenesisEntity implements Serializable {
	
	private static final long serialVersionUID = 176952152819273250L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ColumnDefault("false")
	@Column(nullable = false)
	private boolean deleted = false;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@JsonIgnore
	public boolean isDeleted() {
		return deleted;
	}
	
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	@Override
	@JsonIgnore
	public String toString() {
		return getClass().getSimpleName() + id;
	}
	
	@JsonIgnore
	public String getType() {
		return getClass().getSimpleName();
	}
}
