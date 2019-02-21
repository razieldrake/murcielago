package fr.offsec.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.offsec.domain.Job;
import fr.offsec.domain.User;
import fr.offsec.service.JobService;
import fr.offsec.service.UserService;

@RestController
@RequestMapping(path="/users")
public class UserRestController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	JobService jobService;
	
	@GetMapping("/{idUser}")
	public ResponseEntity<Iterable<Job>> getAllJob(@RequestParam(name = "idUser")Long idUser){
		
		return ResponseEntity.ok(userService.findUserByID(idUser).getJobs());
	}
	
	@GetMapping("/admin/getall")
	public ResponseEntity<Iterable<User>> getAll(){		
		return ResponseEntity.ok(userService.getAll());
	}
	
	@GetMapping(params="{idUser}")
	public ResponseEntity<Job> getOneByID(@RequestParam(name = "idJob")Long idJob){
		return ResponseEntity.ok(jobService.findByID(idJob));
	}

}
