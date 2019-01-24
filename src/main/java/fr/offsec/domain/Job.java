package fr.offsec.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import javax.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table(name="Job")
@JsonInclude(value = Include.NON_DEFAULT)
public class Job {
	
	@JsonProperty
	@Id
	private int idJob;

	@JsonProperty
	private String nameJob;
	
	@JsonProperty
	private String descrJob;
	
	@JsonProperty
	private String statusJob;
	
	@JsonProperty
	@JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss")
	private LocalDate startedAt;
	
	@JsonProperty
	@JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss")
	private LocalDate endAt;
	
	@OneToMany(mappedBy= "job", cascade=CascadeType.ALL)
	private Collection<Host> hostList = new ArrayList<Host>();
	
	@OneToMany(mappedBy="job",cascade=CascadeType.ALL)
	private Collection<Log> logList = new ArrayList<Log>();
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
	@JsonIgnore
	private User user;
	
	@JsonCreator
	public Job(@JsonProperty("id_job")int id,
				@JsonProperty("name_job")String name,
				@JsonProperty("descr_job")String descr,
				@JsonProperty("status_job")String status,
				@JsonProperty("started_at_job")LocalDate startDate,
				@JsonProperty("end_at_job")LocalDate endDate) {
		
		this.idJob = id;
		this.nameJob = name;
		this.descrJob = getDescrJob();
		this.statusJob = status;
		this.startedAt = startDate;
		this.endAt = endDate;
	}
	
	public int getIdJob() {
		return idJob;
	}
	public void setIdJob(int idJob) {
		this.idJob = idJob;
	}
	
	public Collection<Host> getHost(){
		return this.hostList;
	}
	public Collection<Log> getLog(){
		return this.logList;
	}
	public User getUser() {
		return this.user;
	}
	public void setLogList(Collection<Log> list) {
		if (list != null) {
			this.logList = list;
		}
	}
	public void setHost(Collection<Host> list) {
		if (list!=null) {
			this.hostList = list;
		}
	}
	public void setUser (User user) {
		if (user != null) {
			this.user = user;
		}
	}
	public String getNameJob() {
		return nameJob;
	}
	public void setNameJob(String nameJob) {
		this.nameJob = nameJob;
	}
	public String getDescrJob() {
		return descrJob;
	}
	public void setDescrJob(String descrJob) {
		this.descrJob = descrJob;
	}
	public String getStatusJob() {
		return statusJob;
	}
	public void setStatusJob(String statusJob) {
		this.statusJob = statusJob;
	}
	public LocalDate getStartedAt() {
		return startedAt;
	}
	public void setStartedAt(LocalDate startedAt) {
		this.startedAt = startedAt;
	}
	public LocalDate getEndAt() {
		return endAt;
	}
	public void setEndAt(LocalDate endAt) {
		this.endAt = endAt;
	}
	

}
