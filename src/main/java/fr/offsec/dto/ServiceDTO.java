package fr.offsec.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import fr.offsec.domain.CVE;
import fr.offsec.domain.Port;

@JsonInclude(value=Include.NON_EMPTY)
public class ServiceDTO {

	private Long idService;
	
	@JsonProperty("product")
	private String nameService;
	
	@JsonProperty("version")
	private String versionService;
	
	private Port portService;
	
	private String osService;
	
	@JsonProperty("CVE")
	private CVEDTO[] cve;

	public Long getIdService() {
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

	public CVEDTO[] getCve() {
		return cve;
	}

	public void setIdService(Long idServiceDTO) {
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
		this.cve = cvesServiceDTO;
	}

	public String getOsService() {
		return osService;
	}

	public void setOsService(String osServiceDTO) {
		this.osService = osServiceDTO;
	}
	
}
