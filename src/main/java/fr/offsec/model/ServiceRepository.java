package fr.offsec.model;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.offsec.domain.CVE;
import fr.offsec.domain.Service;

@Repository
public interface ServiceRepository extends CrudRepository<Service, UUID> {
	
	//Iterable<Service> findAllByID(UUID idService);
	Iterable<Service> findAllServiceByNameService(String name);
	
	

}