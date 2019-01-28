package fr.offsec.dto;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import fr.offsec.domain.Job;
import fr.offsec.domain.Port;

@JsonInclude(value=Include.NON_EMPTY)
public class HostDTO {
	
	private Long idHost;
	
	public Long getIdHost() {
		return idHost;
	}



	public void setIdHost(Long idHost) {
		this.idHost = idHost;
	}

	private String ipHost;
	
	private String osHost;
	
	private boolean isNew;

	private PortDTO[] ports;
	
	private Job jobHost;

	public String getIpHost() {
		return ipHost;
	}

	public String getOsHost() {
		return osHost;
	}

	public boolean isNew() {
		return isNew;
	}

	public PortDTO[] getPorts() {
		return ports;
	}

	public Job getJobHost() {
		return jobHost;
	}

	public void setIpHost(String ipHostDTO) {
		this.ipHost = ipHostDTO;
	}

	public void setOsHost(String osHostDTO) {
		this.osHost = osHostDTO;
	}

	public void setNew(boolean isNewDTO) {
		this.isNew = isNewDTO;
	}

	public void setPorts(PortDTO[] portsDTO) {
		this.ports = portsDTO;
	}

	public void setJobHost(Job jobHostDTO) {
		this.jobHost = jobHostDTO;
	}
}
