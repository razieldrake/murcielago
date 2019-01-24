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
	
	private Inet4Address ipHostDTO;
	
	private String osHostDTO;
	
	private boolean isNewDTO;

	private Port[] portsDTO;
	
	private Job jobHostDTO;

	public Inet4Address getIpHostDTO() {
		return ipHostDTO;
	}

	public String getOsHostDTO() {
		return osHostDTO;
	}

	public boolean isNewDTO() {
		return isNewDTO;
	}

	public Port[] getPortsDTO() {
		return portsDTO;
	}

	public Job getJobHostDTO() {
		return jobHostDTO;
	}

	public void setIpHostDTO(Inet4Address ipHostDTO) {
		this.ipHostDTO = ipHostDTO;
	}

	public void setOsHostDTO(String osHostDTO) {
		this.osHostDTO = osHostDTO;
	}

	public void setNewDTO(boolean isNewDTO) {
		this.isNewDTO = isNewDTO;
	}

	public void setPortsDTO(Port[] portsDTO) {
		this.portsDTO = portsDTO;
	}

	public void setJobHostDTO(Job jobHostDTO) {
		this.jobHostDTO = jobHostDTO;
	}
}
