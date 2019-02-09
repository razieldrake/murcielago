package fr.offsec.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import fr.offsec.domain.Host;
import fr.offsec.domain.Service;

@JsonInclude(value=Include.NON_EMPTY)
public class PortDTO {
	
	private int idPort;
	
	private String protocol;
	
	private String status;
	
	private Host host;
	
	private ServiceDTO[] servicesPort;

	public int getIdPort() {
		return idPort;
	}

	public String getProtocol() {
		return protocol;
	}

	public String getStatus() {
		return status;
	}

	public Host getHost() {
		return host;
	}

	public ServiceDTO[] getServiceRunningOnPort() {
		return servicesPort;
	}

	public void setIdPortDTO(int idPort) {
		this.idPort = idPort;
	}

	public void setProtocolPort(String protocolPort) {
		this.protocol = protocolPort;
	}

	public void setStatusPort(String statusPort) {
		this.status = statusPort;
	}

	public void setHostPort(Host hostPort) {
		this.host = hostPort;
	}

	public void setServicesPort(ServiceDTO[] servicesPort) {
		this.servicesPort = servicesPort;
	}
	
	

}
