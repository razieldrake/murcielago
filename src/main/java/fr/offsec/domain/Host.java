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

import org.springframework.util.Assert;

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
	
	@JsonProperty
	private String ip;
	
	@JsonProperty
	private String operationSystem;
	
	@JsonProperty
	private boolean isNew; // Triggered is the IP has never been discovered by an anterior scan

	//@JsonIgnore
	@OneToMany(mappedBy= "host", cascade=CascadeType.ALL)
	private Collection<Port> ports = new ArrayList<Port>();
	
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JsonIgnore
	private Job job;
	
	protected Host() {
		
	}
	
	@JsonCreator
	public Host(@JsonProperty("host_id")Long id,
				@JsonProperty("ip_host")String ip,
				@JsonProperty("os_host")String os,
				@JsonProperty("new_host")boolean isNewly) {
	/*	Assert.notNull(id,"id cannot be null");
		Assert.notNull(ip,"ip cannot be null");
		Assert.notNull(os,"os cannot be null");
		Assert.notNull(isNewly,"isNeworNot cannot be null");
		Assert.hasText(ip, "ip canot be blanck or empty");
		Assert.hasText(os, "os canot be blanck or empty");*/
		
		
		this.id = id;
		this.ip = ip;
		this.operationSystem = os;
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
		return ip;
	}
	public void setIpHost(String ipHost) {
		Assert.notNull(ipHost,"id cannot be null");
		Assert.hasText(ipHost,"ipHost cannot be null");
		this.ip = ipHost;
	}
	public String getOperationSystem() {
		return operationSystem;
	}
	public void setOperationSystem(String osHost) {
		Assert.notNull(osHost,"osHost cannot be null");
		Assert.hasText(osHost,"osHost cannot be null");
		this.operationSystem = osHost;
	}
	public boolean isNew() {
		return isNew;
	}
	public void setNew(boolean isNew) {
		Assert.notNull(isNew, "Isnew or not cannot be null");
		this.isNew = isNew;
	}
	public Long getId() {
		return id;
	}

	public Collection<Port> getPorts() {
		return ports;
	}

	public void setId(Long id) {
		Assert.notNull(id, "id cannot be null");
		this.id = id;
	}

	public void setPorts(Collection<Port> ports) {
		Assert.notNull(ports, "ports collection cannot be null");
		this.ports = ports;
	}
}
