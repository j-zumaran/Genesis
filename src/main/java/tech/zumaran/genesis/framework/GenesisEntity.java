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
	
	//public static final String SOFT_DELETE = "deleted = 'FALSE'";
	
	/**
	 * 
	 */
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
	
	/*private String stringFormat() {
		StringBuilder sb = new StringBuilder(getClass().getSimpleName() + " -> { ");

	    try {
	        Field[] fields = getClass().getDeclaredFields();
	        
	        for(Field f :fields){
	        	String fName = f.getName();
	        	if (fName.equals("serialVersionUID") || fName.equals("LOG"))
	        		continue;
	        	
	        	f.setAccessible(true);
        		Object value = f.get(this);
        		
        		if (value == null) 
        			continue;
        		
        		sb.append(fName + ": " + value + ", ");
	        }
	        
	        sb.replace(sb.length() - 2, sb.length(), " }");
	    } catch (Exception e) {
	    	LOG.warn(e.getMessage());
	    }
	    return sb.toString();
	}*/
	
	/*protected static<E extends BaseEntity> List<E> isDeletedGetChildren(BaseEntity entity, List<E> children) {
		if (children == null) return List.of();
		if (children.isEmpty()) return children;
		if (entity.isDeleted()) {
			return children.stream().filter(e -> e.isDeleted()).collect(Collectors.toList()); 
		} else {
			return children.stream().filter(e -> !e.isDeleted()).collect(Collectors.toList()); 
		}
	}*/
	
	//private static final Logger LOG = LoggerFactory.getLogger(BaseEntity.class);
}
