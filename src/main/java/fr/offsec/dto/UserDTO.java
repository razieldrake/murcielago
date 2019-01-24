package fr.offsec.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import fr.offsec.domain.Group;
import fr.offsec.domain.Job;

@JsonInclude(value=Include.NON_EMPTY)
public class UserDTO {
	
	private UUID idUserDTO;
	private String usernameDTO;
	private String passwordDTO;
	private Group groupDTO;
	private Job[] jobsDTO;
	public UUID getIdUserDTO() {
		return idUserDTO;
	}
	public String getUsernameDTO() {
		return usernameDTO;
	}
	public String getPasswordDTO() {
		return passwordDTO;
	}
	public Group getGroupDTO() {
		return groupDTO;
	}
	public Job[] getJobsDTO() {
		return jobsDTO;
	}
	public void setIdUserDTO(UUID idUserDTO) {
		this.idUserDTO = idUserDTO;
	}
	public void setUsernameDTO(String usernameDTO) {
		this.usernameDTO = usernameDTO;
	}
	public void setPasswordDTO(String passwordDTO) {
		this.passwordDTO = passwordDTO;
	}
	public void setGroupDTO(Group groupDTO) {
		this.groupDTO = groupDTO;
	}
	public void setJobsDTO(Job[] jobsDTO) {
		this.jobsDTO = jobsDTO;
	}

}
