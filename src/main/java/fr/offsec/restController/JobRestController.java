package fr.offsec.restController;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.offsec.domain.Host;
import fr.offsec.domain.Job;
import fr.offsec.domain.Port;
import fr.offsec.service.HostService;
import fr.offsec.service.JobService;

@RestController
@RequestMapping(path="/hosts")
public class JobRestController {
	
	@Autowired
	JobService service;
	
	/*@GetMapping()
	public ResponseEntity<Iterable<Job>> getAll(){
		Iterable <Job> job = 	service.getAll();
		job.forEach(h->h.getHost().clear());
		return ResponseEntity.ok(service.getAll());
	}
	
	@GetMapping(params= {"idJob"})
	public ResponseEntity<Iterable<Job>> getOneByID(@RequestParam(name = "idJob")int idJob){
		Iterable<Job> job = service.getAll();
		job.forEach(h->h.getHost().clear());
		return ResponseEntity.ok(service.findAllByID(idJob));
	}
	@GetMapping("/{idJob}/host")
	public ResponseEntity<Iterable<Host>> getHostOnJob(@PathVariable("idJob")int idJob){
		
		Job job = service.findOneById(idJob);
		
		return ResponseEntity.ok(job.getHost());
	}*/

}
