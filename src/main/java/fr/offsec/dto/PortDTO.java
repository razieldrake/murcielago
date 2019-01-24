package fr.offsec.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import fr.offsec.domain.Host;
import fr.offsec.domain.Service;

@JsonInclude(value=Include.NON_EMPTY)
public class PortDTO {
	
	private int idPortDTO;
	
	private String protocolPortDTO;
	
	private String statusPortDTO;
	
	private Host hostPortDTO;
	
	private Service[] servicesPortDTO;

	public int getIdPortDTO() {
		return idPortDTO;
	}

	public String getProtocolPortDTO() {
		return protocolPortDTO;
	}

	public String getStatusPortDTO() {
		return statusPortDTO;
	}

	public Host getHostPortDTO() {
		return hostPortDTO;
	}

	public Service[] getServicesPortDTO() {
		return servicesPortDTO;
	}

	public void setIdPortDTO(int idPortDTO) {
		this.idPortDTO = idPortDTO;
	}

	public void setProtocolPortDTO(String protocolPortDTO) {
		this.protocolPortDTO = protocolPortDTO;
	}

	public void setStatusPortDTO(String statusPortDTO) {
		this.statusPortDTO = statusPortDTO;
	}

	public void setHostPortDTO(Host hostPortDTO) {
		this.hostPortDTO = hostPortDTO;
	}

	public void setServicesPortDTO(Service[] servicesPortDTO) {
		this.servicesPortDTO = servicesPortDTO;
	}
	
	

}
