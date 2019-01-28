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
		
		
		return ResponseEntity.ok(service.getAll());
	}
	
	@GetMapping(params= {"idService"})
	public ResponseEntity<Service> getOneByID(@RequestParam(name = "idService")Long idService){
		
		
		return ResponseEntity.ok(service.getAllByID(idService));
		
		
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
	
	@PostMapping()
	public ResponseEntity<Void> create(@RequestBody ServiceDTO dto, UriComponentsBuilder ucb, Principal principal){
		
		Assert.notNull(dto,"dto cannot be null");
		
		Service serviceent = new Service(new Random().nextLong(), dto.getNameService(),dto.getVersionService(),dto.getOsService(),dto.getPortService());
		if (dto.getCvesService()!= null) {
			
			System.out.println("prout");
			
			for (CVEDTO cvedtos : dto.getCvesService()) {
				serviceent.getCVEForService().add(new CVE(cvedtos.getIdCVE(), cvedtos.getBaseScoreV2(), cvedtos.getBaseScoreV3(), cvedtos.getImpactScoreV2(), cvedtos.getImpactScoreV3(), cvedtos.getVectorV2(), cvedtos.getVectorV3(), cvedtos.getAttackVectorV2(), cvedtos.getAttackVectorV3(), cvedtos.getDescription(),serviceent));
			}
		}
		
		Service savedService = service.save(serviceent);
		URI location = ucb.path("/services/{id}").buildAndExpand(savedService.getIdService()).toUri();
		return ResponseEntity.created(location).build();
		
	}
}
