package fr.offsec.model;

import java.net.Inet4Address;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.offsec.domain.Host;
import fr.offsec.domain.Port;

@Repository
public interface PortRepository extends CrudRepository<Port, Integer> {
	
	//Iterable<Port> findPortBYNumber(int portNumber);
	Iterable<Port> findPortByProtocol(String protocol);
 	Iterable<Port> findPortByStatus(String status);
}
