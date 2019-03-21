package fr.offsec.restController;

import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import fr.offsec.domain.Job;
import fr.offsec.domain.User;
import fr.offsec.dto.JobDTO;
import fr.offsec.dto.UserDTO;
import fr.offsec.security.TokenProvider;
import fr.offsec.service.JobService;
import fr.offsec.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping(path="/")
public class HomeRestController {
	
	@Autowired
	JobService jobService;
	
	@Autowired
	UserService userService;
	
	
	//private AuthenticationManager authMng;
	
	@Autowired
	private TokenProvider tokenProvider;
	
	BCryptPasswordEncoder pwdEncoder = new 	BCryptPasswordEncoder();	
	
	
	@GetMapping()
	public ResponseEntity<Iterable<Job>> getAllForUser(Principal principal){
		
		
		System.out.println ("hello"+principal.getName());
		User user = userService.findUserByUsername(principal.getName());
		
		return ResponseEntity.ok(user.getJobs());
	}
	
	@GetMapping("/generatePDF/{idJob}")
	public ResponseEntity<Job> getPDF(@PathVariable("idJob")Long idJob, Principal principal){
		System.out.println(idJob);
		if (idJob == null) {System.out.println("not an identifiers");return new ResponseEntity<Job>(HttpStatus.BAD_REQUEST);}
		Job job = jobService.findByID(idJob);
		if (job ==null) {System.out.println("didn't find the job");return new ResponseEntity<Job>(HttpStatus.BAD_REQUEST);}
				
	
		return ResponseEntity.ok(job);
	}
	
	@GetMapping("/admin")
	public ResponseEntity<Iterable<User>> getAllUser(){
		return ResponseEntity.ok(userService.getAll());
	}
	
	@PostMapping("admin/adduser")
	public ResponseEntity<Void> addUser(@RequestBody UserDTO dto, UriComponentsBuilder ucb, Principal principal){
		
		Assert.notNull(dto,"A dto cannot be null");
	
		User user = new User(new Random().nextLong(), dto.getUsername(), pwdEncoder.encode(dto.getPassword()), dto.getRole());
		User savedUser = userService.save(user);
		URI location = ucb.path("/user/{idUser}").buildAndExpand(savedUser.getIdUser()).toUri();
		return ResponseEntity.created(location).build();
		
	}
	
	@GetMapping("/user")
	public ResponseEntity<User> user(Principal principal){
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String loggedUsername = auth.getName();
		return ResponseEntity.ok(userService.findUserByUsername(loggedUsername));
	}
	
	@PostMapping("login")
	public ResponseEntity<Void> login(@RequestParam(name ="username") String username, @RequestParam(name="password") String password, HttpServletResponse response) throws IOException{
		
		System.out.println("e,nter in controller");
		String tokenJson = "{\"token\":\"";
		String token = null;
		UserDetails user = userService.loadUserByUsername(username);
		
		System.out.println(user.getUsername());
		System.out.println(user.getPassword());
		System.out.println(pwdEncoder.encode(password));
		System.out.println(user.getAuthorities());
		if (user != null && pwdEncoder.matches(password, user.getPassword()))/*user.getPassword().equals(password))*/ {
			System.out.println("entering condition");
			//userService.
			//final Authentication authentication  = authMng.authenticate(new UsernamePasswordAuthenticationToken(username, pwdEncoder.encode(password)));
			UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(),user.getAuthorities());
			System.out.println(authReq.getName()+" have connected with authority"+authReq.getAuthorities().toString());
			final String generatedToken = this.tokenProvider.createToken(authReq);
			System.out.println("Finally what up?           "+this.tokenProvider.getAuthentication(generatedToken).getName());
			
			response.addHeader("Authorization", generatedToken);
			System.out.println("generated token : "+generatedToken);
			
		
			
			return null;
		} else {
			System.out.println("fazilure!!!!!!!!!!!!!!!!!!!");
			return null;
		}
	
	}

}
