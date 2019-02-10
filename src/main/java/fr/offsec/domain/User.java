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
public class User {
	
	@JsonProperty
	@Id
	private Long idUser;
	
	@JsonProperty
	private String username;
	
	@JsonProperty
	private String password;
	
	@OneToMany(mappedBy= "user", cascade=CascadeType.ALL)
	@JsonIgnore
	private Collection<Job> jobs = new ArrayList<Job>();
	
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "îd_group", nullable = false)
	@JsonIgnore
	private Group group;
	
	@JsonCreator
	public User(@JsonProperty("user_id")Long id,
				@JsonProperty("username")String user,
				@JsonProperty("password")String pwd) {
		
		this.idUser = id;
		this.username = user;
		this.password = pwd;
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
		this.idUser = idUser;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Group getGroup() {
		return this.group;
	}
	
	public void setGroup(Group group) {
		this.group = group;
	}
	

}
