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

import org.springframework.util.Assert;

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
	private Long idService;
	
	@JsonProperty
	private String nameService;
	
	@JsonProperty 
	private String versionService;
	
	@JsonProperty
	private String OsService;

	
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "id_Port", nullable = false)
	@JsonIgnore
	private Port port;
	

	@JsonIgnore
	@OneToMany(mappedBy= "service", cascade=CascadeType.ALL)
	private Collection<CVE> cves = new ArrayList<CVE>();
	
	protected Service() {
		
	}
	
	@JsonCreator
	public Service(@JsonProperty("id_service") Long id,
				   @JsonProperty("name_service")String name,
				   @JsonProperty("version_service")String version,
				   @JsonProperty("guessed_os_service")String os) {
		
		/*Assert.notNull(id,"id cannot be null");
		Assert.notNull(name,"name cannot be null");
		Assert.hasText(name, "name cannot be empty or blanck");
		Assert.notNull(version,"version cannot be null");
		Assert.hasText(version,"version cannot be empty or blanck");
		Assert.notNull(os, "os cannot be null");
		Assert.hasText(os,"os cannot be empty or blanck");*/
		
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
	
	public Port getPort() {
		return this.port;
	}
	public Collection<CVE> getCVE(){
		return this.cves;
	}

	public void setCVECollection(Collection<CVE> newCollection) {
		
		this.cves = newCollection;
	}
	public void setPort(Port newPort) {
		Assert.notNull(newPort,"a port cannot be null");
		this.port = newPort;
	}
	public void setIdService(Long newID) {
		Assert.notNull(newID,"an id cannot be null");
		Assert.isTrue(newID!=0, "an id cannot be equal to 0");
		this.idService = newID;
	}
	public void setNameService(String name) {
		Assert.notNull(name,"a name cannot be null");
		Assert.hasText(name, "cannot be empty or blacnk");
		this.nameService = name;
	}
	public void setVersionService(String version) {
		Assert.notNull(version, "version cannot be null");
		Assert.hasText(version,"version cannot be empty or blanck");
		this.versionService = version;
	}
	public void setGuessedOSService(String os) {
		Assert.notNull(os,"os cannot be null");
		Assert.hasText(os, "os cannot be empty or blacnk");
		this.OsService = os;
	}
	
	
	
}
