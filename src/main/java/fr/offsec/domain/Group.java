package fr.offsec.domain;

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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name="Group")
@JsonInclude(value = Include.NON_DEFAULT)
public class Group {
	
	
	@JsonProperty
	@Id
	private int idGroup;
	
	
	@JsonProperty
	private String nameGroup;
	
	@JsonProperty
	private String descrGroup;
	
	@OneToMany(mappedBy= "group", cascade=CascadeType.ALL)
	private Collection<User> users = new ArrayList<User>();
	
/*	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "îd_access_right", nullable = false)
	@JsonIgnore
	private AccessRight accessRight;*/
	
	@JsonCreator
	public Group(@JsonProperty("id_group")int id,
				 @JsonProperty("name_group")String name,
				 @JsonProperty("descr_group")String descr) {
		
		this.idGroup = id;
		this.nameGroup = name;
		this.descrGroup = descr;
	}
	
	public String getNameGroup() {
		return this.nameGroup;
	}
	public String getescrGroup() {
		return this.descrGroup;
	}
	public void setNameGroup(String name) {
		this.nameGroup = name;
	}
	public void setDescrGroup(String descr) {
		this.descrGroup = descr;
	}
	

}
