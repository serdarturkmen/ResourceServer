package gov.diski.ResourceServer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Resource_Role")
public class Role extends BaseModel implements GrantedAuthority {

	private static final long serialVersionUID = 1L;

	@Setter
	@Getter
	@Column(name = "Name", unique = true, nullable = false, length=100)
	private String name;

	public Role() {
	}
	
	public Role(Long id){
		setId(id);
	}

	@JsonIgnore
	@Override
	public String getAuthority() {
		return getName();
	}

}
