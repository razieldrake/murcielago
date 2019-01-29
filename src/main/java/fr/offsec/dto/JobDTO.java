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

	private int idJob;
	
	private String nameJob;
	
	private String descrJob;
	
	private String statusJob;
	
	@JsonFormat(pattern = "yyyy-MM-dd@HH:mm")
	private LocalDate startedAt;
	
	@JsonFormat(pattern = "yyyy-MM-dd@HH:mm")
	private LocalDate endAt;
	
	private Host[] hostList;
	
	private Log[] logList;
	
	private User user;

	public int getIdJob() {
		return idJob;
	}

	public String getNameJob() {
		return nameJob;
	}

	public String getDescrJob() {
		return descrJob;
	}

	public String getStatusJob() {
		return statusJob;
	}

	public LocalDate getStartedAt() {
		return startedAt;
	}

	public LocalDate getEndAt() {
		return endAt;
	}

	public Host[] getHostList() {
		return hostList;
	}

	public Log[] getLogList() {
		return logList;
	}

	public User getUser() {
		return user;
	}

	public void setIdJob(int idJobDTO) {
		this.idJob = idJobDTO;
	}

	public void setNameJob(String nameJobDTO) {
		this.nameJob = nameJobDTO;
	}

	public void setDescrJob(String descrJobDTO) {
		this.descrJob = descrJobDTO;
	}

	public void setStatusJob(String statusJobDTO) {
		this.statusJob = statusJobDTO;
	}

	public void setStartedAt(LocalDate startedAtDTO) {
		this.startedAt = startedAtDTO;
	}

	public void setEndAt(LocalDate endAtDTO) {
		this.endAt = endAtDTO;
	}

	public void setHostList(Host[] hostListDTO) {
		this.hostList = hostListDTO;
	}

	public void setLogList(Log[] logListDTO) {
		this.logList = logListDTO;
	}

	public void setUser(User userDTO) {
		this.user = userDTO;
	}
}
