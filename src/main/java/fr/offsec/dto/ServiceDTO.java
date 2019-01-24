package fr.offsec.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import fr.offsec.domain.CVE;
import fr.offsec.domain.Port;

@JsonInclude(value=Include.NON_EMPTY)
public class ServiceDTO {

	private UUID idServiceDTO;
	
	private String nameServiceDTO;
	
	private String versionServiceDTO;
	
	private Port portServiceDTO;
	
	private CVE[] cvesServiceDTO;

	public UUID getIdServiceDTO() {
		return idServiceDTO;
	}

	public String getNameServiceDTO() {
		return nameServiceDTO;
	}

	public String getVersionServiceDTO() {
		return versionServiceDTO;
	}

	public Port getPortServiceDTO() {
		return portServiceDTO;
	}

	public CVE[] getCvesServiceDTO() {
		return cvesServiceDTO;
	}

	public void setIdServiceDTO(UUID idServiceDTO) {
		this.idServiceDTO = idServiceDTO;
	}

	public void setNameServiceDTO(String nameServiceDTO) {
		this.nameServiceDTO = nameServiceDTO;
	}

	public void setVersionServiceDTO(String versionServiceDTO) {
		this.versionServiceDTO = versionServiceDTO;
	}

	public void setPortServiceDTO(Port portServiceDTO) {
		this.portServiceDTO = portServiceDTO;
	}

	public void setCvesServiceDTO(CVE[] cvesServiceDTO) {
		this.cvesServiceDTO = cvesServiceDTO;
	}
	
}
