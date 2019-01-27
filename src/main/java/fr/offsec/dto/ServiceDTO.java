package fr.offsec.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import fr.offsec.domain.CVE;
import fr.offsec.domain.Port;

@JsonInclude(value=Include.NON_EMPTY)
public class ServiceDTO {

	private UUID idService;
	
	private String nameService;
	
	private String versionService;
	
	private Port portService;
	
	private String osService;
	
	private CVEDTO[] cvesService;

	public UUID getIdService() {
		return idService;
	}

	public String getNameService() {
		return nameService;
	}

	public String getVersionService() {
		return versionService;
	}

	public Port getPortService() {
		return portService;
	}

	public CVEDTO[] getCvesService() {
		return cvesService;
	}

	public void setIdService(UUID idServiceDTO) {
		this.idService = idServiceDTO;
	}

	public void setNameService(String nameServiceDTO) {
		this.nameService = nameServiceDTO;
	}

	public void setVersionService(String versionServiceDTO) {
		this.versionService = versionServiceDTO;
	}

	public void setPortService(Port portServiceDTO) {
		this.portService= portServiceDTO;
	}

	public void setCvesService(CVEDTO[] cvesServiceDTO) {
		this.cvesService = cvesServiceDTO;
	}

	public String getOsService() {
		return osService;
	}

	public void setOsService(String osServiceDTO) {
		this.osService = osServiceDTO;
	}
	
}
