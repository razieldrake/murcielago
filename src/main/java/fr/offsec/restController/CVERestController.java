package fr.offsec.restController;

import java.security.Principal;
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

import java.net.URI;

import fr.offsec.domain.CVE;
import fr.offsec.domain.Service;
import fr.offsec.dto.CVEDTO;
import fr.offsec.service.CVEService;

@RestController
@RequestMapping(path="/cves")
public class CVERestController {
	
	@Autowired
	private CVEService service;
	
	
	@GetMapping()
	public ResponseEntity<Iterable<CVE>> getAll(){
		
		return ResponseEntity.ok(service.getAll());
	}

	@GetMapping(params= {"IDCve"})
	public ResponseEntity<CVE> getOneByID(@RequestParam(name = "IDCve")String cve){
		
		return ResponseEntity.ok(service.getAllByIDCVE(cve));
		
	}
	
	@DeleteMapping("/{IDCve}")
	public ResponseEntity<Void> deleteOneByIDCve(@PathVariable("IDCve")String cve){
		service.deleteByID(cve);
		return ResponseEntity.ok().build();
	}
/*
 * POSTMAPPING FOR ONE CVE	
*/
	@PostMapping()
	public ResponseEntity<Void> create(@RequestBody CVEDTO dto, UriComponentsBuilder ucb, Principal principal){
		System.out.println("prout");
		Assert.notNull(dto, "dto cannot be null");
		if (principal == null) {
			principal=()->"annonymous";
		}
		
		CVE cve = new CVE(dto.getIdCVE(),dto.getBaseScoreV2(),dto.getBaseScoreV3(),dto.getImpactScoreV2(),dto.getImpactScoreV3(),dto.getVectorV2(),dto.getVectorV3(),dto.getAttackVectorV2(),dto.getAttackVectorV3(),dto.getDescription(),dto.getServiceId());
		CVE savedCve = service.save(cve);
		/* ANONYMOUS VERSDION
		 * REPLACE WITH THIS ONE FOR USERNAME : CVE saveCve = service.save(cve,, principal.getName());
		 */
		URI location = ucb.path("/cves/{IDCve}").buildAndExpand(savedCve.getIdCVE()).toUri();
		return ResponseEntity.created(location).build();
		
	}

}
