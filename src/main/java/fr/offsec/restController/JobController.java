package fr.offsec.restController;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
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
import fr.offsec.domain.Job;
import fr.offsec.domain.Port;
import fr.offsec.domain.Service;
import fr.offsec.dto.CVEDTO;
import fr.offsec.dto.HostDTO;
import fr.offsec.dto.JobDTO;
import fr.offsec.dto.PortDTO;
import fr.offsec.dto.ServiceDTO;
import fr.offsec.service.HostService;
import fr.offsec.service.JobService;

@RestController
@RequestMapping(path="/jobs")
public class JobController {
	
	@Autowired
	JobService jobService;
	
	@Autowired
	HostService hostService;
	
	
	@GetMapping()
	public ResponseEntity<Iterable<Job>> getAll(){		
		return ResponseEntity.ok(jobService.getAll());
	}
	
	@GetMapping(params="{idJob}")
	public ResponseEntity<Job> getOneByID(@RequestParam(name = "idJob")Long idJob){
		return ResponseEntity.ok(jobService.findByID(idJob));
	}
	@GetMapping("/{idJob}/ports")
	public ResponseEntity<Collection<Host>> getHostForJob(@PathVariable("idJob")Long idJob){
		Job job = jobService.findByID(idJob);
		return ResponseEntity.ok(job.getHost());
	}
	
	
	
	@PostMapping("/scan/order")
	public ResponseEntity<Void> runJob(@RequestParam(name="target")String target,@RequestParam(name="ports")String port){
		
		
		String PARAMJSON = "{\n"+"\"ip\":\"" +target+ "\",\"port\":\""+port+"\",\"parstype\":\"2\"}";
		String PARAMURL = "?ip="+target+"&port="+port+"&rate=5&parstype=2";
		System.out.println(PARAMJSON);
		System.out.println(PARAMURL);
		
			URL obj = null;
			try {
				obj = new URL("http://localhost:8000/scan"+PARAMURL);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("failed");
			}
			HttpURLConnection postConnection = null;
			try {
				postConnection = (HttpURLConnection) obj.openConnection();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("failed");
			}
		
		
	
			
		    try {
				postConnection.setRequestMethod("GET");
			} catch (ProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("failed");
			}
		    postConnection.setRequestProperty("User-Agent", "JAVA client");
		    postConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		    postConnection.setDoOutput(true);
			try{DataOutputStream output = null;
			try {
				output = new DataOutputStream(postConnection.getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("failed");
			}
		    try {
				output.writeBytes(PARAMURL);
				output.flush();
				output.close();
				int responseCode = postConnection.getResponseCode();
				System.out.println("\nSending 'POST' request to URL : " + obj);
				System.out.println("Post parameters : " + PARAMURL);
				System.out.println("Response Code : " + responseCode);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("failed");
			}finally{}
			
			}finally{
			postConnection.disconnect();
		}
		return ResponseEntity.accepted().build();
	}

	//PostMapping to get the result of an ordered job
	//It's not the Postmapping for add a new job
	@PostMapping()
	public ResponseEntity<Void> createFinal(@RequestBody JobDTO dto, UriComponentsBuilder ucb, Principal principal){
		
		Assert.notNull(dto,"A dto cannot be null");
		Job job = new Job(dto.getIdJob(), dto.getNameJob(), dto.getDescrJob(), dto.getStatusJob(), dto.getStartedAt(), dto.getEndAt());
		if (dto.getHostList()!=null) {
			for (HostDTO h : dto.getHostList()) {
				
				Host host = new Host(h.getIdHost(), h.getIpHost(), h.getIpHost(), h.isNew());
				job.getHost().add(host);
				host.setJob(job);
				if(h.getPorts()!=null) {
					for (PortDTO p : h.getPorts()) {
						
						Port port = new Port(p.getIdPort(),p.getProtocol(),p.getStatus());
						host.getPorts().add(port);
						port.setHost(host);
						if(p.getServiceRunningOnPort()!=null) {
							System.out.println("there's services running on the port");
							for (ServiceDTO s : p.getServiceRunningOnPort()) {
								
								Service service = new Service(new Random().nextLong(), s.getNameService(), s.getVersionService(), s.getOsService());
								port.getServiceRunningOnPort().add(service);
								service.setPort(port);
								if (s.getCvesService()!=null) {
									System.out.println("there's cves on the services running on the port");
									for (CVEDTO c : s.getCvesService()) {
										
										CVE cve = new CVE(c.getIdCVE(), c.getBaseScoreV2(), c.getBaseScoreV3(), c.getImpactScoreV2(), c.getImpactScoreV3(), c.getVectorV2(), c.getVectorV3(), c.getAttackVectorV2(), c.getAttackVectorV3(), c.getDescription(), service);
										service.getCVEForService().add(cve);
									}
								}
							}
						}
					}
				}
			}
		}
		Job savedJob = jobService.save(job);
		URI location = ucb.path("/jobs/{idJob}").buildAndExpand(savedJob.getIdJob()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	
	

}
