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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
@Entity
@Table(name="Port")
@JsonInclude(value = Include.NON_DEFAULT)
public class Port {
	
	@JsonProperty
	@Id
	private int idPort;
	
	@JsonProperty
	private String protocol;
	
	@JsonProperty
	private String status;
	
	//@JsonIgnore
	//@JsonIgnoreProperties("Host")
	//@JsonBackReference
	//@JsonIgnore
	//@JsonManagedReference
	@OneToMany(mappedBy= "port", cascade=CascadeType.ALL)
	private Collection<Service> services = new ArrayList<Service>();
	
	//@JsonIgnoreProperties("Port")
	//@JsonManagedReference
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id", nullable = false)
	@JsonIgnore
	private Host host;
	
	protected Port() {
		
	}
	
	@JsonCreator
	public Port(@JsonProperty("id_port") int idport,
				@JsonProperty("protocol_port")String protocol,
				@JsonProperty("status_protocol")String status) {
		
		this.idPort = idport;
		this.protocol = protocol;
		this.status = status;
	}
	
	public Host getHost() {
		return this.host;
	}
	public Collection<Service> getServiceRunningOnPort(){
		return this.services;
	}
	
	
	public void setPortForService(fr.offsec.domain.Service service) {
		this.services.add(service);
	}

	public void setHost(Host host) {
		this.host = host;
		
	}
	
	public int getIdPort() {
		return idPort;
	}
	public void setIdPort(int idPort) {
		this.idPort = idPort;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
