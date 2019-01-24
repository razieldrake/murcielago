package fr.offsec.model;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.offsec.domain.CVE;
import fr.offsec.domain.Log;
import fr.offsec.domain.Service;

@Repository
public interface LogRepository extends CrudRepository<Log, Integer> {
	
	Iterable<Log> findAllByIdLog(int idLOg);
	
	
}
