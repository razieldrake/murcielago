package fr.offsec.dto;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import fr.offsec.domain.Job;
import fr.offsec.domain.Port;

@JsonInclude(value=Include.NON_EMPTY)
public class HostDTO {
	

	private String ip;
	
	@JsonProperty("operation_system")
	private String operationSystem;
	

	private PortDTO[] ports;
	

	public String getIpHost() {
		return ip;
	}

	public String getOperationSystem() {
		return operationSystem;
	}


	public PortDTO[] getPorts() {
		return ports;
	}


	public void setIp(String ipHostDTO) {
		this.ip = ipHostDTO;
	}

	public void setOperationSystem(String osHostDTO) {
		this.operationSystem = osHostDTO;
	}


	
	public void setPorts(PortDTO[] portsDTO) {
		this.ports = portsDTO;
	}


}
