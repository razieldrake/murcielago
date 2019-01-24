package fr.offsec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.offsec.domain.CVE;
import fr.offsec.model.CVERepository;

@Service
public class CVEService {

	@Autowired
	private CVERepository repo;
	
	public Iterable<CVE> getAll(){
		
		return repo.findAll();
	}
	
	public CVE getAllByIDCVE(String nameCVE){
		if (nameCVE == null) {
			return null;
		}
		
		CVE cve = repo.findOne(nameCVE);
		if (cve == null) {
			return null;
		}
		return cve;
		
		
	}
	
	public CVE save(CVE cve) {
		
		if (cve == null) {
			return null;
		}
	
		return repo.save(cve);
	}
	
	public void deleteByID(String cve) {
		if(cve!=null) {
			repo.delete(cve);
		}
		
	}
	public void deleteByObject(CVE cve) {
		if (cve != null) {
			repo.delete(cve);
		}
	}
}
