package fr.offsec.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import fr.offsec.domain.Host;
import fr.offsec.domain.Service;

@JsonInclude(value=Include.NON_EMPTY)
public class PortDTO {
	
	@JsonProperty("portnumber")
	private int idPort;
	
	@JsonProperty("reason")
	private String protocol;
	
	@JsonProperty("state")
	private String status;
	
	private Host host;
	
	@JsonProperty("services")
	private ServiceDTO[] services;

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

	public ServiceDTO[] getServices() {
		return services;
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
		this.services = servicesPort;
	}
	
	

}
