package fr.offsec.restController;

import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import fr.offsec.domain.CVE;
import fr.offsec.domain.Host;
import fr.offsec.domain.Service;
import fr.offsec.dto.CVEDTO;
import fr.offsec.dto.ServiceDTO;
import fr.offsec.service.ServiceService;

@RestController
@RequestMapping(path="/services")
public class ServiceRestController {

	@Autowired
	ServiceService service;
	@GetMapping()
	public ResponseEntity<Iterable<Service>> getAll(){
		
		//Iterable <Service> ser = service.getAll();
		//ser.forEach(s->s.getCVEForService().clear());
		return ResponseEntity.ok(service.getAll());
	}
	
	@GetMapping(params= {"idService"})
	public ResponseEntity<Service> getOneByID(@RequestParam(name = "idService")Long idService){
		
		
		return ResponseEntity.ok(service.getAllByID(idService));
	
	}
	
	@GetMapping("/{idService}/cves")
	public ResponseEntity<Collection<CVE>> getCvesForService(@PathVariable("idService")Long idService){
		Service bla = service.getAllByID(idService);
		Collection<CVE> cves = new ArrayList<CVE>();
		for (CVE cve : bla.getCVEForService()) {
			cves.add(cve);
		}
		return ResponseEntity.ok(cves);
	}
	
	
	@GetMapping(params = {"nameService"})
	public ResponseEntity<Iterable<Service>> getAllServiceByName(@RequestParam(name="nameService")String nameService){
		return ResponseEntity.ok(service.getAllServiceByName(nameService));
	}
	
	@DeleteMapping("/{idService}")
	public ResponseEntity<Void> deleteOneByIDCve(@PathVariable("idService")Long idService){
		service.deleteByID(idService);
		return ResponseEntity.ok().build();
	}
	
}
