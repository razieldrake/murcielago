package fr.offsec.domain;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.Collection;

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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="Host")
@JsonInclude(value = Include.NON_DEFAULT)
public class Host {

	@Id
	@JsonProperty
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	public Long getId() {
		return id;
	}

	public Collection<Port> getPorts() {
		return ports;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPorts(Collection<Port> ports) {
		this.ports = ports;
	}
	@JsonProperty
	private String ipHost;
	
	@JsonProperty
	private String osHost;
	
	@JsonProperty
	private boolean isNew; // Triggered is the IP has never been discovered by an anterior scan

	@OneToMany(mappedBy= "host", cascade=CascadeType.ALL)
	private Collection<Port> ports = new ArrayList<Port>();
	

	
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
   // @JoinColumn(name = "id_job", nullable = false)
	@JsonIgnore
	private Job job;
	
	@JsonCreator
	public Host(@JsonProperty("host_id")Long id,
				@JsonProperty("ip_host")String ip,
				@JsonProperty("os_host")String os,
				@JsonProperty("new_host")boolean isNewly) {
		
		this.id = id;
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
	public String getIpHost() {
		return ipHost;
	}
	public void setIpHost(String ipHost) {
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
