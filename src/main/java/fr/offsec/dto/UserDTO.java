package fr.offsec.dto;



import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


import fr.offsec.domain.Job;

@JsonInclude(value=Include.NON_EMPTY)
public class UserDTO {
	
	private Long idUser;
	private String username;
	private String password;
	private String role;

	private Job[] jobs;
	public Long getIdUser() {
		return idUser;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	
	public Job[] getJobsDTO() {
		return jobs;
	}
	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}
	public void setUsernameDTO(String username) {
		this.username = username;
	}
	public void setPasswordDTO(String password) {
		this.password= password;
	}
	
	public void setJobsDTO(Job[] jobs) {
		this.jobs = jobs;
	}
	public String getRole() {
		
		return this.role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}

}
