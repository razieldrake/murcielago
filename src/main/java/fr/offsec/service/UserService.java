package fr.offsec.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.offsec.domain.Job;
import fr.offsec.domain.User;
import fr.offsec.model.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repo;
	
	public Iterable<User> getAll(){
		return repo.findAll();
	}
	public Iterable<User> findUserByID(UUID user){
		if (user == null) {
			return null;
		}
		Iterable<User> users = repo.findUserByIdUser(user);
		if (users == null) {
			return null;
		}
		return users;
				
	}
	public Iterable<User> findUserByUsername(String username){
		if (username == null) {
			return null;
		}
		Iterable<User> users = repo.findUserByUsername(username);
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
	
	public void deleteByID(UUID id) {
		if (id != null ) {
			repo.delete(id);
		}
	}
	

}
