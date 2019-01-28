package fr.offsec.service;
import org.springframework.stereotype.Service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import fr.offsec.domain.CVE;
import fr.offsec.model.ServiceRepository;


@Service
public class ServiceService {
	
	@Autowired
	private ServiceRepository repo;
	
	public Iterable<fr.offsec.domain.Service> getAll() {
		return repo.findAll();
	}
	
	public fr.offsec.domain.Service getAllByID(Long id) {
		if (id == null) {return null;}
		fr.offsec.domain.Service service = repo.findOne(id);
		if (service == null) {return null;}
		return service;
	}
	
	public Iterable<fr.offsec.domain.Service> getAllServiceByName(String name){
		if (name==null) {return null;}
		Iterable<fr.offsec.domain.Service> tmp = repo.findAllServiceByNameService(name);
		if (tmp==null) { return null;}
		return tmp;
	}
	
	public fr.offsec.domain.Service save(fr.offsec.domain.Service service) {
		
		if (service==null) {return null;}
		for (CVE cve : service.getCVEForService()) {
			cve.setService(service);
		}
		return repo.save(service);
	}
	
	public void deleteByID(Long id) {
		if (id!=null) {
			repo.delete(id);
		}
	}
	
	
}
