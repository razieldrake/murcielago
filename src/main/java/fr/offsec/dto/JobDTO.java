package fr.offsec.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import fr.offsec.domain.Host;

import fr.offsec.domain.User;

@JsonInclude(value=Include.NON_EMPTY)
public class JobDTO {

	private Long idJob;
	
	private String nameJob;
	
	private String descrJob;
	
	private String statusJob;
	
	@JsonFormat(pattern = "yyyy-MM-dd@HH:mm")
	private LocalDateTime startedAt;
	
	@JsonFormat(pattern = "yyyy-MM-dd@HH:mm")
	private LocalDateTime endAt;
	
	private HostDTO[] hostList;
	
	private User user;

	public Long getIdJob() {
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

	public LocalDateTime getStartedAt() {
		return startedAt;
	}

	public LocalDateTime getEndAt() {
		return endAt;
	}

	public HostDTO[] getHostList() {
		return hostList;
	}



	public User getUser() {
		return user;
	}

	public void setIdJob(Long idJob) {
		this.idJob = idJob;
	}

	public void setNameJob(String nameJob) {
		this.nameJob = nameJob;
	}

	public void setDescrJob(String descrJob) {
		this.descrJob = descrJob;
	}

	public void setStatusJob(String statusJob) {
		this.statusJob = statusJob;
	}

	public void setStartedAt(LocalDateTime startedAt) {
		this.startedAt = startedAt;
	}

	public void setEndAt(LocalDateTime endAt) {
		this.endAt = endAt;
	}

	public void setHostList(HostDTO[] hostList) {
		this.hostList = hostList;
	}



	public void setUser(User user) {
		this.user = user;
	}
}
