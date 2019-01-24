package fr.offsec.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name="Log")
@JsonInclude(value = Include.NON_DEFAULT)
public class Log {
	
	@JsonProperty
	@Id
	private int idLog;

	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_job", nullable = false)
	@JsonIgnore
	private Job job;
	
	@JsonCreator
	public Log(@JsonProperty("id_log")int idLog) {
		
		this.idLog = idLog;
	}
	
	public Job getJob() {
		return this.job;
	}
	
	public void setJob(Job job) {
		if (job != null) {
			this.job = job;
		}
	}
	public int getIdLog() {
		return this.idLog;
	}
	public void setIdLog(int id) {
		this.idLog = id;
	}
	

}
