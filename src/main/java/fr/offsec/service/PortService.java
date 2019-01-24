package fr.offsec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.offsec.domain.CVE;
import fr.offsec.domain.Port;
import fr.offsec.model.PortRepository;

@Service
public class PortService {
	
	@Autowired
	private PortRepository repo;
	
	public Iterable<Port> getAll(){
		return repo.findAll();
	}
	
	public Port findPortBYNumber(int portNumber){
		if (portNumber == 0) { return null;}
		Port ports = repo.findOne(portNumber);
		if (ports==null) { return null;}
		return ports;
		
	}
	
	Iterable<Port> findPortByPrtotocol(String protocol){
		if (protocol == null) {return null;}
		Iterable<Port> ports  =repo.findPortByProtocol(protocol);
		if (ports==null) {return null;}
		return ports;
	}
 	Iterable<Port> findPortByStatus(String status){
 		if (status==null) {return null;}
 		Iterable<Port> ports = repo.findPortByStatus(status);
 		if (ports==null) {return null;}
 		return ports;
 	}
 	
 	public Port save(Port port) {
		
		if (port==null) {return null;}
		for (fr.offsec.domain.Service service : port.getServiceRunningOnPort()) {
			port.setPortForService(service);
		}
		return repo.save(port);
	}
 	
 	public void deleteByID(int port) {
 		if (port!=0) {
 			repo.delete(port);
 		}
 		
}

}
