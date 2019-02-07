package fr.offsec.restController;

import java.net.Inet4Address;
import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
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
import fr.offsec.domain.Port;
import fr.offsec.domain.Service;
import fr.offsec.dto.CVEDTO;
import fr.offsec.dto.HostDTO;
import fr.offsec.dto.PortDTO;
import fr.offsec.dto.ServiceDTO;
import fr.offsec.service.HostService;
import fr.offsec.service.PortService;
import fr.offsec.service.ServiceService;

@RestController
@RequestMapping(path="/hosts")
public class HostController {
	
	
	@Autowired
	HostService service;
	
	//Ju
	@Autowired
	PortService portService;
	@Autowired
	ServiceService serviceService;
	//
	
	
	@GetMapping()
	public ResponseEntity<Iterable<Host>> getAll(){
		Iterable <Host> host = 	service.getAll();
		host.forEach(h->h.getPortsOnHost().clear());
		return ResponseEntity.ok(service.getAll());
	}
	
	@GetMapping(params= {"ipaddr"})
	public ResponseEntity<Host> getOneByID(@RequestParam(name = "ipaddr")Long ipaddr){
		return ResponseEntity.ok(service.findAllByIP(ipaddr));
	}
	
	@GetMapping("/{ipaddr}/ports")
	public ResponseEntity<Collection<Port>> getPortsforHost(@PathVariable("ipaddr")Long ipaddr){
		Host bla = service.findAllByIP(ipaddr);
		bla.getPorts().forEach(p->p.getServiceRunningOnPort().clear());
		return ResponseEntity.ok(bla.getPorts());
		
	}
	
	@GetMapping("/ports/{idPort}/services")
	public ResponseEntity<Collection<Service>> getServicePort(@PathVariable("idPort")int idPort){
		Port p = portService.findPortById(idPort);
		p.getServiceRunningOnPort().forEach(s->s.getPortForService().getServiceRunningOnPort().clear());
		return ResponseEntity.ok(p.getServiceRunningOnPort());
	}
	
	@GetMapping("/{ipaddr}/ports/{idPort}/services")
	public ResponseEntity<Collection<Service>> getServiceOnPort(@PathVariable("ipaddr")Long ipaddr,@PathVariable("idPort")int idPort){

		Host h = service.findAllByIP(ipaddr);
		Port p = portService.findPortById(idPort);
		return ResponseEntity.ok(p.getServiceRunningOnPort());
	
	}

	

	@PostMapping()
	public ResponseEntity<Void> create(@RequestBody HostDTO dto, UriComponentsBuilder ucb, Principal principal){
		Assert.notNull(dto,"A dto cannot be null");
		Host host = new Host(dto.getIdHost(),dto.getIpHost(),dto.getOsHost(),dto.isNew());
		System.out.println("prout prout");
		if (dto.getPorts()!=null) 
		{
			System.out.println("prout prout prout");
			for (PortDTO portsdto : dto.getPorts()) 
			{
				System.out.println("pouet");
				Port temp = new Port(portsdto.getIdPort(),portsdto.getProtocol(),portsdto.getStatus());
				host.getPortsOnHost().add(temp);
				if (portsdto.getServicesPort()!=null)
				{
					System.out.println("pouet");System.out.println("pouet");
					for (ServiceDTO ser : portsdto.getServicesPort())
					{
						Service serv = new Service(new Random().nextLong(), ser.getNameService(), ser.getVersionService(), ser.getOsService());
						serv.setPort(temp);
						temp.getServiceRunningOnPort().add(serv);
						if (serv.getCVEForService()!=null) 
						{
							System.out.println("bataclan was here");System.out.println("bataclan was here");
							for(CVEDTO cve: ser.getCvesService())
							{
								CVE tmcve = new CVE(cve.getIdCVE(), cve.getBaseScoreV2(), cve.getBaseScoreV3(), cve.getImpactScoreV2(), cve.getImpactScoreV3(), cve.getVectorV2(), cve.getVectorV3(), cve.getAttackVectorV2(), cve.getAttackVectorV3(), cve.getDescription());
								serv.getCVEForService().add(tmcve);
								tmcve.setService(serv);
							}
						}
					}
				}
			}
		}
		
		Host savedHost = service.save(host);
		URI location = ucb.path("/services/{id}").buildAndExpand(savedHost.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
}
