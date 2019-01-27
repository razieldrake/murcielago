package fr.offsec.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.offsec.domain.CVE;

@Repository
public interface CVERepository extends CrudRepository<CVE, String> {
	
	//CVE findAllByI(String idCVE);
	
	
	

}
