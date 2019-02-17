package fr.offsec.model;

import java.net.Inet4Address;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.offsec.domain.Host;
import fr.offsec.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	
	User findUserByIdUser(Long user);
	User findUserByUsername(String username);
	
	
}
