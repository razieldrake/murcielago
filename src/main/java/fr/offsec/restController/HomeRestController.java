package fr.offsec.restController;

import java.net.URI;
import java.security.Principal;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import fr.offsec.domain.Job;
import fr.offsec.domain.User;
import fr.offsec.dto.JobDTO;
import fr.offsec.dto.UserDTO;
import fr.offsec.service.JobService;
import fr.offsec.service.UserService;

@RestController
@RequestMapping(path="/")
public class HomeRestController {
	
	@Autowired
	JobService jobService;
	
	@Autowired
	UserService userService;
	
	@GetMapping()
	public ResponseEntity<Iterable<Job>> getAllForUser(Principal principal){
		
		System.out.println ("hello"+principal.getName());
		User user = userService.findUserByUsername(principal.getName());
		return ResponseEntity.ok(user.getJobs());
	}
	
	@GetMapping("/admin")
	public ResponseEntity<Iterable<User>> getAllUser(){
		return ResponseEntity.ok(userService.getAll());
	}
	
	@PostMapping("/admin/adduser")
	public ResponseEntity<Void> addUser(@RequestBody UserDTO dto, UriComponentsBuilder ucb, Principal principal){
		
		Assert.notNull(dto,"A dto cannot be null");
		if (userService.findUserByUsername(principal.getName()).getRole()!="ADMIN") {
			
			return ResponseEntity.status(403).build();
		}
		User user = new User(new Random().nextLong(), dto.getUsername(), new BCryptPasswordEncoder().encode(dto.getPassword()), dto.getRole());
		User savedUser = userService.save(user);
		URI location = ucb.path("/user/{idUser}").buildAndExpand(savedUser.getIdUser()).toUri();
		return ResponseEntity.created(location).build();
		
	}

}
