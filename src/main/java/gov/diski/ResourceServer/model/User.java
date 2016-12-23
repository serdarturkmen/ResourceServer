package gov.diski.ResourceServer.model;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "Resource_User")
public class User extends BaseModel implements UserDetails {

	private static final long serialVersionUID = 1L;

	public User() {
	}

	@Column(name = "Password")
	private String password;
	
	@Size(max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;

	@Getter
	@Setter
//	@Email
	@NotEmpty(message = "email cannot be empty")
	@Column(name = "Email", unique = true, nullable = false, length = 100)
	private String email;
	
	@Getter
	@Setter
	@Column(name = "Enabled")
	private boolean enabled;
	
	@Getter
	@Setter
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "UserRoles", joinColumns = @JoinColumn(name = "UserId"), inverseJoinColumns = @JoinColumn(name = "RoleId"), uniqueConstraints = @UniqueConstraint(columnNames = {
			"RoleId", "UserId" }))
	private Set<Role> roles = new HashSet<>();
	
    @Column(name = "provider_id", length = 255)
    private String providerId;
	
    @Column(name = "access_token", length = 255)
    @Getter
    @Setter
    private String accessToken;

    @Column(length = 255)
    private String secret;

    @Column(name = "refresh_token", length = 255)
    private String refreshToken;

    @Column(name = "expire_time")
    private Long expireTime;

//	@Getter
//	@Setter
//	@ManyToMany(fetch = FetchType.EAGER)
//	@JoinTable(name = "AuthUserRoles", joinColumns = @JoinColumn(name = "UserId"), inverseJoinColumns = @JoinColumn(name = "RoleId"), uniqueConstraints = @UniqueConstraint(columnNames = {
//			"RoleId", "UserId" }))
//	private Set<Role> roles = new HashSet<>();
	

//	@JsonIgnore
//	public String getPassword() {
//		return this.password;
//	}
//
//	@JsonProperty("password")
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//	@Getter
//	@Setter
//	@Column(name = "Enabled")
//	private boolean enabled;

//	@Getter
//	@Setter
//	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	@JoinColumn(name = "UserProfile")
//	private UserProfile userProfile;

	@Getter
	@Setter
	@Column(name = "LastLogin")
	private Date lastLogin;

	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public String getUsername() {
		return getEmail();
	}

	public boolean hasRole(String role) {
		for (Role r : getRoles()) {
			if (role.equals(r.getName())) {
				return true;
			}
		}
		return false;
	}

	@JsonIgnore
	public String getPassword() {
		return this.password;
	}

}
