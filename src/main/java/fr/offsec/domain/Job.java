package fr.offsec.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import javax.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


@Entity
@Table(name="Job")
@JsonInclude(value = Include.NON_DEFAULT)
public class Job {
	
	@JsonProperty
	@Id
	private Long idJob;

	@JsonProperty
	private String nameJob;
	
	@JsonProperty
	private String descrJob;
	
	@JsonProperty
	private String statusJob;
	
	@JsonProperty
	@JsonFormat(pattern = "yyyy-MM-dd@HH:mm")
	private LocalDateTime startedAt;
	
	@JsonProperty
	@JsonFormat(pattern = "yyyy-MM-dd@HH:mm")
	private LocalDateTime endAt;
	
	@OneToMany(mappedBy= "job", cascade=CascadeType.ALL)
	private Collection<Host> hostList = new ArrayList<Host>();
	
	
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
//    @JoinColumn(name ="user_id", nullable = false)
	@JsonIgnore
	private User user;
	
	protected Job() {
		
	}
	@JsonCreator
	public Job(@JsonProperty("id_job")Long id,
				@JsonProperty("name_job")String name,
				@JsonProperty("descr_job")String descr,
				@JsonProperty("status_job")String status,
				@JsonProperty("started_at_job")LocalDateTime startDate,
				@JsonProperty("end_at_job")LocalDateTime endDate) {
		Assert.notNull(id,"id cannot be null");
		Assert.notNull(name,"name cannot be null");
		Assert.notNull(descr,"description cannot be null");
		Assert.notNull(status,"status cannot be null");
		Assert.notNull(startDate,"starting date canniot be null");
		Assert.notNull(endDate,"Ending date cannot be null");
		Assert.hasText(name, "name cannot be blank or empty");;
		Assert.hasText(descr, "description cannot be blank or empty");
		Assert.hasText(status, "status cannoty be blank or empty");
		
		this.idJob = id;
		this.nameJob = name;
		this.descrJob = descr;
		this.statusJob = status;
		this.startedAt = startDate;
		this.endAt = endDate;
	}
	
	public Long getIdJob() {
		return idJob;
	}
	public void setIdJob(Long idJob) {
		Assert.notNull(idJob, "idJob cannot be null");
		if (idJob != 0) {
			this.idJob = idJob;
		}
		
	}
	
	public Collection<Host> getHost(){
		return this.hostList;
	}

	public User getUser() {
		return this.user;
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
		Assert.notNull(nameJob,"nameJob cannot be null");
		Assert.hasText(nameJob, "NameJob cannot be empty or blank");
		this.nameJob = nameJob;
	}
	public String getDescrJob() {
		return descrJob;
	}
	public void setDescrJob(String descrJob) {
		Assert.notNull(descrJob,"desvcription cannot be null");
		Assert.hasText(descrJob, "descrioption cannot be empty or blank");
		this.descrJob = descrJob;
	}
	public String getStatusJob() {
		return statusJob;
	}
	public void setStatusJob(String statusJob) {
		Assert.notNull(statusJob,"status cannot be null");
		Assert.hasText(statusJob, "status cannot be empty or blank");
		this.statusJob = statusJob;
	}
	public LocalDateTime getStartedAt() {
		return startedAt;
	}
	public void setStartedAt(LocalDateTime startedAt) {
		Assert.notNull(startedAt,"starting date cannot be null");
		
		this.startedAt = startedAt;
	}
	public LocalDateTime getEndAt() {
		return endAt;
	}
	public void setEndAt(LocalDateTime endAt) {
		Assert.notNull(endAt,"ending Date cannot be null");
		this.endAt = endAt;
	}
	

}
