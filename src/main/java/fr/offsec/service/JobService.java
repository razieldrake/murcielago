package fr.offsec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.offsec.domain.Host;
import fr.offsec.domain.Job;
import fr.offsec.domain.Log;
import fr.offsec.model.JobRepository;

@Service
public class JobService {
	
	@Autowired
	private JobRepository repo;
	
	public Iterable<Job> getAll() {
		return repo.findAll();
	}
	
	public Iterable<Job> findAllByID(int idJob){
		if(idJob == 0) {
			return null;
		}
		
		Iterable<Job> jobs = repo.findAllByIdJob(idJob);
		if (jobs==null) {
			return null;
		}
		return jobs;
		
		
	}
	public Iterable<Job> findAllByJobName(String name){
		if (name==null) {
			return null;
		}
		Iterable<Job> jobs =  repo.findAllByNameJob(name);
		if (jobs==null) {
			return null;
		}
		return jobs;
	}
	public Iterable<Job> findAllJobByStatus(String status){
		if (status == null) {
			return null;
		}
		Iterable<Job> jobs = repo.findAllJobByStatusJob(status);
		if (jobs == null) {
			return null;
		}
		return jobs;
	}
	
	public Job save (Job jobNew) {
		if (jobNew == null) {
			return null;
		}
		for(Host host : jobNew.getHost()) {
			host.setJob(jobNew);
		}
		for (Log log : jobNew.getLog()) {
			log.setJob(jobNew);
		}
		
		return repo.save(jobNew);
		
	}
	
	public void deleteByID(int id) {
		if (id != 0) {
			repo.delete(id);
		}
	}
	

}
