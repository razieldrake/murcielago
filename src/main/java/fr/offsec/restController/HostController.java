package fr.offsec.restController;

import java.net.Inet4Address;
import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
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

@RestController
@RequestMapping(path="/hosts")
public class HostController {
	
	
	@Autowired
	HostService service;
	
	@GetMapping()
	public ResponseEntity<Iterable<Host>> getAll(){
				
		return ResponseEntity.ok(service.getAll());
	}
	
	@GetMapping(params= {"ipaddr"})
	public ResponseEntity<Host> getOneByID(@RequestParam(name = "ipaddr")Inet4Address ipaddr){
		return ResponseEntity.ok(service.findAllByIP(ipaddr));
	}
	
	@GetMapping("/{ipaddr}/ports")
	public ResponseEntity<Collection<Port>> getCvesForService(@PathVariable("ipaddr")Inet4Address ipaddr){
		Host bla = service.findAllByIP(ipaddr);
		Collection<Port> ports = new ArrayList<Port>();
		for (Port pt : bla.getPortsOnHost()) {
			ports.add(pt);
		}
		return ResponseEntity.ok(ports);
	}
	
	@PostMapping()
	public ResponseEntity<Void> create(@RequestBody HostDTO dto, UriComponentsBuilder ucb, Principal principal){
		Assert.notNull(dto,"A dto cannot be null");
		Host host = new Host(dto.getIpHostDTO(),dto.getOsHostDTO(),dto.isNewDTO());
		if (dto.getPortsDTO()!=null) 
		{
			for (PortDTO portsdto : dto.getPortsDTO()) 
			{
				Port temp = new Port(portsdto.getIdPort(),portsdto.getProtocolPort(),portsdto.getStatusPort());
				host.getPortsOnHost().add(temp);
				if (temp.getServiceRunningOnPort()!=null)
				{
					for (ServiceDTO ser : portsdto.getServicesPort())
					{
						Service serv = new Service(ser.getIdService(), ser.getNameService(), ser.getVersionService(), ser.getOsService(),temp);
						temp.getServiceRunningOnPort().add(serv);
						if (serv.getCVEForService()!=null) 
						{
							for(CVE cve: serv.getCVEForService())
							{
								serv.getCVEForService().add(cve);
							}
						}
					}
				}
			}
			
		}
		
		Host savedHost = service.save(host);
		URI location = ucb.path("/services/{id}").buildAndExpand(savedHost.getIpHost()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	

}
