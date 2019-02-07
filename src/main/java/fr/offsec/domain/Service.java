package fr.offsec.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

//import org.hibernate.mapping.Array;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import antlr.collections.List;

@Entity
@Table(name="Service")
@JsonInclude(value = Include.NON_DEFAULT)
public class Service {
	
	@JsonProperty
	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	private Long idService;
	
	@JsonProperty
	private String nameService;
	
	@JsonProperty 
	private String versionService;
	
	@JsonProperty
	private String OsService;

	//@JsonManagedReference
	//@JsonIgnoreProperties("service")
	//@JsonIgnore
	//@JoinColumn(name = "id_Port", nullable = false)
	//@JsonIgnoreProperties("port")
	
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name="idPort", nullable= false )
	@JsonIgnore
 	private Port port;
	

	//@JoinColumn(name= "idService")
	//@JsonIgnore
	//@JsonManagedReference
	//@JsonBackReference
	@OneToMany(mappedBy= "service", cascade=CascadeType.ALL)
	private Collection<CVE> cves = new ArrayList<CVE>();
	
	protected Service() {
		
	}
	
	@JsonCreator
	public Service(@JsonProperty("id_service") Long id,
				   @JsonProperty("name_service")String name,
				   @JsonProperty("version_service")String version,
				   @JsonProperty("guessed_os_service")String os) {
				  
		this.idService = id;
		this.nameService = name;
		this.versionService = version;
		this.OsService = os;
		
		
	
	}
	
	public Long getIdService() {
		return this.idService;
	}
	public String getNameService() {
		return this.nameService;
	}
	public String getVersionService() {
		return this.versionService;
	}
	public String getGuessedOSService() {
		return this.OsService;
	}
	
	public Port getPortForService() {
		return this.port;
	}
	public Collection<CVE> getCVEForService(){
		return this.cves;
	}

	public void setCVECollection(Collection<CVE> newCollection) {
		this.cves = newCollection;
	}
	public void setPort(Port newPort) {
		this.port = newPort;
	}
	public void setIdService(Long newID) {
		this.idService = newID;
	}
	public void setNameService(String name) {
		this.nameService = name;
	}
	public void setVersionService(String version) {
		this.versionService = version;
	}
	public void setGuessedOSService(String os) {
		this.OsService = os;
	}
	
	
	
}
