package fr.offsec.service;



import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.offsec.domain.Job;
import fr.offsec.domain.User;
import fr.offsec.model.UserRepository;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserRepository repo;
	
	public Iterable<User> getAll(){
		return repo.findAll();
	}
	public User findUserByID(Long user){
		if (user == null) {
			return null;
		}
		User users = repo.findUserByIdUser(user);
		if (users == null) {
			return null;
		}
		return users;
				
	}
	public User findUserByUsername(String username){
		if (username == null) {
			return null;
		}
		User users = repo.findUserByUsername(username);
		if (users == null) {
			return null;
		}
		return users;
		
	}
	
	public User save (User user) {
		
		if (user == null) {
			return null;
		}
		for (Job job : user.getJob()) {
			job.setUser(user);
		}
		
		return repo.save(user);
	}
	
	public void deleteByID(Long id) {
		if (id != null ) {
			repo.delete(id);
		}
		
	
	}
	
	//TODO
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repo.findUserByUsername(username);
		if(user==null) {
			
			throw new UsernameNotFoundException("invalid username or password");
		}
		GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
		// return the user details
		return  new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Arrays.asList(authority));
		
	}
	

}
