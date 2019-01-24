package fr.offsec.model;

import java.net.Inet4Address;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.offsec.domain.CVE;
import fr.offsec.domain.Host;
import fr.offsec.domain.Service;

@Repository
public interface HostRepository extends CrudRepository<Host, Inet4Address> {
	
	Iterable<Host> findAllByIpHost(Inet4Address ipaddr);
	//Host findOneByIP(Inet4Address ipaddr);
	Iterable<Host> findAllByOsHost(String os);
	
	
	
}
