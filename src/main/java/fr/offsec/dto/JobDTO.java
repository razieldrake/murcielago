package fr.offsec.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import fr.offsec.domain.Host;
import fr.offsec.domain.Log;
import fr.offsec.domain.User;

@JsonInclude(value=Include.NON_EMPTY)
public class JobDTO {

	private int idJobDTO;
	
	private String nameJobDTO;
	
	private String descrJobDTO;
	
	private String statusJobDTO;
	
	@JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss")
	private LocalDate startedAtDTO;
	
	@JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss")
	private LocalDate endAtDTO;
	
	private Host[] hostListDTO;
	
	private Log[] logListDTO;
	
	private User userDTO;

	public int getIdJobDTO() {
		return idJobDTO;
	}

	public String getNameJobDTO() {
		return nameJobDTO;
	}

	public String getDescrJobDTO() {
		return descrJobDTO;
	}

	public String getStatusJobDTO() {
		return statusJobDTO;
	}

	public LocalDate getStartedAtDTO() {
		return startedAtDTO;
	}

	public LocalDate getEndAtDTO() {
		return endAtDTO;
	}

	public Host[] getHostListDTO() {
		return hostListDTO;
	}

	public Log[] getLogListDTO() {
		return logListDTO;
	}

	public User getUserDTO() {
		return userDTO;
	}

	public void setIdJobDTO(int idJobDTO) {
		this.idJobDTO = idJobDTO;
	}

	public void setNameJobDTO(String nameJobDTO) {
		this.nameJobDTO = nameJobDTO;
	}

	public void setDescrJobDTO(String descrJobDTO) {
		this.descrJobDTO = descrJobDTO;
	}

	public void setStatusJobDTO(String statusJobDTO) {
		this.statusJobDTO = statusJobDTO;
	}

	public void setStartedAtDTO(LocalDate startedAtDTO) {
		this.startedAtDTO = startedAtDTO;
	}

	public void setEndAtDTO(LocalDate endAtDTO) {
		this.endAtDTO = endAtDTO;
	}

	public void setHostListDTO(Host[] hostListDTO) {
		this.hostListDTO = hostListDTO;
	}

	public void setLogListDTO(Log[] logListDTO) {
		this.logListDTO = logListDTO;
	}

	public void setUserDTO(User userDTO) {
		this.userDTO = userDTO;
	}
}
