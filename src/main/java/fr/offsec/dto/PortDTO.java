package fr.offsec.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import fr.offsec.domain.Host;
import fr.offsec.domain.Service;

@JsonInclude(value=Include.NON_EMPTY)
public class PortDTO {
	
	private int idPort;
	
	private String protocolPort;
	
	private String statusPort;
	
	private Host hostPort;
	
	private ServiceDTO[] servicesPort;

	public int getIdPort() {
		return idPort;
	}

	public String getProtocolPort() {
		return protocolPort;
	}

	public String getStatusPort() {
		return statusPort;
	}

	public Host getHostPort() {
		return hostPort;
	}

	public ServiceDTO[] getServicesPort() {
		return servicesPort;
	}

	public void setIdPortDTO(int idPort) {
		this.idPort = idPort;
	}

	public void setProtocolPort(String protocolPort) {
		this.protocolPort = protocolPort;
	}

	public void setStatusPort(String statusPort) {
		this.statusPort = statusPort;
	}

	public void setHostPort(Host hostPort) {
		this.hostPort = hostPort;
	}

	public void setServicesPort(ServiceDTO[] servicesPort) {
		this.servicesPort = servicesPort;
	}
	
	

}
