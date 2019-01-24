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

@Entity
@Table(name="Host")
@JsonInclude(value = Include.NON_DEFAULT)
public class Host {

	@JsonProperty
	@Id
	private Inet4Address ipHost;
	
	@JsonProperty
	private String osHost;
	
	@JsonProperty
	private boolean isNew; // Triggered is the IP has never been discovered by an anterior scan

	@OneToMany(mappedBy= "host", cascade=CascadeType.ALL)
	private Collection<Port> ports = new ArrayList<Port>();
	

	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_job", nullable = false)
	@JsonIgnore
	private Job job;
	
	@JsonCreator
	public Host(@JsonProperty("id_host")Inet4Address ip,
				@JsonProperty("os_host")String os,
				@JsonProperty("new_host")boolean isNewly) {
		
		
		this.ipHost = ip;
		this.osHost = os;
		this.isNew = isNewly;
	}
	
	public Job getJob() {
		return this.job;
	}
	public Collection<Port> getPortsOnHost(){
		return this.ports;
	}
	public void setPortsOnHost(Collection<Port>ports) {
		if (ports!=null) {
			this.ports = ports;
		}
	}
	
	public void setJob(Job job) {
		if (job != null) {
			this.job = job;
		}
	}
	public Inet4Address getIpHost() {
		return ipHost;
	}
	public void setIpHost(Inet4Address ipHost) {
		this.ipHost = ipHost;
	}
	public String getOsHost() {
		return osHost;
	}
	public void setOsHost(String osHost) {
		this.osHost = osHost;
	}
	public boolean isNew() {
		return isNew;
	}
	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}
}
