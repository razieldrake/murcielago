package fr.offsec.domain;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.Collection;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Session is a class that represent a Session of work, not an authenficate session
 * @author user
 *
 */
@Entity
@Table(name="User")
@JsonInclude(value = Include.NON_DEFAULT)
public class User implements UserDetails {
	
	@JsonProperty
	@Id
	private Long idUser;
	
	@JsonProperty
	private String username;
	
	@JsonProperty
	private String password;
	
	@JsonProperty
	private String role;
	
	@OneToMany(mappedBy= "user", cascade=CascadeType.ALL)
	@JsonIgnore
	private Collection<Job> jobs = new ArrayList<Job>();
	

	
	
	protected User() {
		
	}
	@JsonCreator
	public User(@JsonProperty("user_id")Long id,
				@JsonProperty("username")String user,
				@JsonProperty("password")String pwd,
				@JsonProperty("role")String role) {
		Assert.notNull(id,"id cannot be null");
		Assert.notNull(user,"username cannot be null");
		Assert.notNull(pwd,"password cannot be null");
		Assert.notNull(role,"roles cannot be null");
		Assert.hasText(user,"username cannot be empty or blank");
		Assert.hasText(pwd,"password cannot be empty or blank");
		Assert.hasText(role,"role cannot be empty or blank");
		
		this.idUser = id;
		this.username = user;
		this.password = pwd;
		this.role = role;
	}
	
	
	public Collection<Job> getJob(){
		return this.jobs;
	}
	public void setJobs(Collection<Job> jobs) {
		if (jobs != null) {
			this.jobs = jobs;
		}
	}
	public Long getIdUser() {
		return idUser;
	}
	public void setIdUser(Long idUser) {
		Assert.notNull(idUser,"idUser cannot be null");
		this.idUser = idUser;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		Assert.notNull(username,"username cannot be null");
		Assert.hasText(username, "username cannot be empty or blank");
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		Assert.notNull(password,"password cannot be null");
		Assert.hasText(password, "password cannot be empty or blank");
		this.password = password;
	}
	
	


	public String getRole() {
		return this.role;
	}


	public void setRole(String role) {
		Assert.notNull(role,"role cannot be null");
		Assert.hasText(role, "role cannot be empty or blank");
		this.role=role;
	}


	public Collection<Job> getJobs() {
		return jobs;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		
		
			authorities.add(new SimpleGrantedAuthority(this.getRole()));
		
		return authorities;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	

}
