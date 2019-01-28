package fr.offsec.service;

import java.net.Inet4Address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.offsec.domain.Host;
import fr.offsec.domain.Port;
import fr.offsec.model.HostRepository;

@Service
public class HostService {
	
	@Autowired
	private HostRepository repo;
	
	public Iterable<Host> getAll() {
		return repo.findAll();
	}
	
	public Host findAllByIP(Long ipaddr){
		
		if (ipaddr == null) {
			return null;
		}
		Host host = repo.findOne(ipaddr);
		if (host == null) {
			return null;
		}
		return host;
		
		
	}
	public Iterable<Host> findOneByIP(String ipaddr){
		if (ipaddr== null) {
			return null;
		}
		Iterable<Host> hosts = repo.findAllByIpHost(ipaddr);
		
		if (hosts==null) {
			return null;
		}
		return hosts;
		
	}
	Iterable<Host> findAllByOsHost(String os)
	{
		if (os == null) {
			return null;
		}
		Iterable<Host> hosts = repo.findAllByOsHost(os);
		if(hosts==null) {
			return null;
		}
		return hosts;
	}
	
	public Host save(Host host) {
		if (host==null) {return null;}
		for (Port port : host.getPortsOnHost()) {
			port.setHost(host);
		}
		
		return repo.save(host);
	}
	
	public void deleteByIP(Long ipaddr) {
		if (ipaddr != null) {
			repo.delete(ipaddr);
		}
	}
	
	

}
