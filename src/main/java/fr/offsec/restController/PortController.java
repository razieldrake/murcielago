package fr.offsec.restController;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.offsec.domain.CVE;
import fr.offsec.domain.Port;
import fr.offsec.domain.Service;
import fr.offsec.service.PortService;

@RestController
@RequestMapping(path="/ports")
public class PortController {
	@Autowired
	PortService service;
	
	@GetMapping()
	public ResponseEntity<Iterable<Port>> getAll(){
		
		
		return ResponseEntity.ok(service.getAll());
	}

	@GetMapping(params= {"idPort"})
	public ResponseEntity<Port> getOneByID(@RequestParam(name = "idPort")int idPort){
		
		
		return ResponseEntity.ok(service.findPortById(idPort));
			
		
	}
	@GetMapping("/{idPort}/services")
	public ResponseEntity<Collection<Service>> getCvesForService(@PathVariable("idPort")int idPort){
		Port bla = service.findPortById(idPort);
		Collection<Service> services = new ArrayList<Service>();
		for (Service serv : bla.getServiceRunningOnPort()) {
			services.add(serv);
		}
		return ResponseEntity.ok(services);
	}


}
