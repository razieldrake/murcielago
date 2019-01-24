package fr.offsec.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import fr.offsec.domain.Job;

@JsonInclude(value=Include.NON_EMPTY)
public class LogDTO {

	private int idLogDTO;
	private Job job;
	public int getIdLogDTO() {
		return idLogDTO;
	}
	public Job getJob() {
		return job;
	}
	public void setIdLogDTO(int idLogDTO) {
		this.idLogDTO = idLogDTO;
	}
	public void setJob(Job job) {
		this.job = job;
	}
}
