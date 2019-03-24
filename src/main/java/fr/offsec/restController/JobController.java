package fr.offsec.restController;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.util.JSONPObject;

import fr.offsec.domain.CVE;
import fr.offsec.domain.Host;
import fr.offsec.domain.Job;
import fr.offsec.domain.Port;
import fr.offsec.domain.Service;
import fr.offsec.domain.User;
import fr.offsec.dto.CVEDTO;
import fr.offsec.dto.HostDTO;
import fr.offsec.dto.HostsDTO;
import fr.offsec.dto.JobDTO;
import fr.offsec.dto.PortDTO;
import fr.offsec.dto.ServiceDTO;
import fr.offsec.service.HostService;
import fr.offsec.service.JobService;
import fr.offsec.service.UserService;
import springfox.documentation.spring.web.json.Json;

@RestController
@RequestMapping(path="/jobs")
public class JobController {
	
	@Autowired
	JobService jobService;
	
	@Autowired
	HostService hostService;
	
	@Autowired
	UserService userService;
	
	
	@GetMapping()
	public ResponseEntity<Iterable<Job>> getAll(){		
		return ResponseEntity.ok(jobService.getAll());
	}
	
	@GetMapping(params="{idJob}")
	public ResponseEntity<Job> getOneByID(@RequestParam(name = "idJob")Long idJob){
		return ResponseEntity.ok(jobService.findByID(idJob));
	}
	
	@GetMapping("/{idJob}/hosts")
	public ResponseEntity<Collection<Host>> getHostForJob(@PathVariable("idJob")Long idJob){
		Job job = jobService.findByID(idJob);
		return ResponseEntity.ok(job.getHost());
	}
	
	
	@PostMapping("/order")
	public ResponseEntity<Void> jobi(@RequestParam(name="target")String target,@RequestParam(name="ports")String port,UriComponentsBuilder ucb){
		
		/**
		 * INstanviation of a new Job
		 */
		
		Job job = new Job(new Random().nextLong(), "scan", "scan a list of port in a ip list", "sending", LocalDateTime.now(),LocalDateTime.now());
		/**
		 * Put the IP of the PYTHON API  in ipAPI
		 */
		String ipAPI = "192.168.0.25:8000";
		String PARAMJSON = "{\n"+"\"ip\":\"" +target+ "\",\"port\":\""+port+"\",\"parstype\":\"2\"}";
		String PARAMURL = "?ip="+target+"&port="+port+"&rate=5&parstype=2";
		System.out.println(PARAMJSON);
		System.out.println(PARAMURL);
		
		
		try {
			URL anotherurl = new URL("http://"+ipAPI+"/scan"+PARAMURL);
			RestTemplate rest =  new RestTemplate();
			HostsDTO results = rest.getForObject("http://192.168.0.25:8000/scan"+PARAMURL, HostsDTO.class);
			for (HostDTO result : results.getHost() ) {
				
				Host host = new Host(new Random().nextLong(), result.getIp(), result.getOperationSystem(), true);
				job.getHost().add(host);
				host.setJob(job);
				System.out.println(result.getIp()+" has been found with "+result.getOperationSystem());
				if (result.getPorts()!=null) {
					System.out.println("The host has ports informations");
					for (PortDTO portdto : result.getPorts() ) {					
						Port po = new Port(portdto.getIdPort(), portdto.getStatus(),portdto.getProtocol());
						host.getPorts().add(po);
						po.setHost(host);
						System.out.println(portdto.getIdPort() + "on protocol : "+ portdto.getProtocol());
						if (portdto.getServices()!=null) {
							System.out.println("there s services running on port");
							for (ServiceDTO sdto : portdto.getServices()) {
								Service serv = new Service(new Random().nextLong(), sdto.getNameService(), sdto.getVersionService(), "testFieldOsInService");
								po.getServices().add(serv);
								serv.setPort(po);
								if(sdto.getCve()!=null) {
									System.out.println("there's cve working on service");
									for (CVEDTO cvedto : sdto.getCve()) {
										CVE cve = new CVE(cvedto.getIdCVE(),
														  cvedto.getBaseScore(),
														  cvedto.getImpactScore(),
														  cvedto.getVector(),
														  cvedto.getAttackVector(),
														  cvedto.getDescription(),
														  serv);
										serv.getCVE().add(cve);
										cve.setService(serv);
									}
								}
							}
						}
					}
				}
			}
			
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

		Job savedJob = jobService.save(job);
		URI location = ucb.path("/jobs/{idJob}").buildAndExpand(savedJob.getIdJob()).toUri();
		return ResponseEntity.created(location).build();
		// return ResponseEntity.accepted().build();

		
	}
	/**
	 * scan ip from the Yegor version, fonctionnal but i don' t want to implemented as mine
	 * @param ip
	 * @param port
	 * @param rate
	 * @param parsetype
	 * @param jobName
	 * @param jobDescribe
	 * @param principal
	 * @throws IOException
	 * @throws AuthenticationException
	 */
//	@GetMapping("/scan")
//    public void ScanPyScript(
//            @RequestParam String ip, @RequestParam String port,
//            @RequestParam int rate, @RequestParam int parsetype,
//            @RequestParam String jobName, @RequestParam String jobDescribe,Principal principal) throws IOException, AuthenticationException {
//            String ip_py="192.168.1.64";
//            principal = new Principal() {
//				
//				@Override
//				public String getName() {
//					// TODO Auto-generated method stub
//					return "ann";
//				}
//			};
//            System.out.println(principal.getName());
//        String url = "http://"+ ip_py +":8000/scan?ip="+ URLEncoder.encode(ip, "UTF-8") +"&port=" + port.replaceAll("\\s+","%2C") + "&rate=" + rate + "&parsetype=" + parsetype;
//
//        User user = userService.findUserByUsername(principal.getName());
//        Job job = new Job(new Random().nextLong(), "scan", "scan a list of port in a ip list", "sending", LocalDateTime.now(),LocalDateTime.parse("running"));
//        job.setUser(user);
//        user.getJobs().add(job);
//        jobService.save(job);
//        
//
//        HttpClient client = HttpClientBuilder.create().build();
//        HttpGet request = new HttpGet(url);
//        request.addHeader("User-Agent", "USER_AGENT");
//
//        HttpResponse response = client.execute(request);
//
//
//        //if ( response == null ) {
//        job.setStatusJob("ERROR");
//        job.setEndAt(LocalDateTime.now());
//        jobService.save(job);
//        //}
//        // else {
//        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
//
//        StringBuffer result = new StringBuffer();
//        String line = "";
//        while ((line = rd.readLine()) != null) {
//            result.append(line);
//            //}
//
//            job.setStatusJob("FINISHED");
//            job.setEndAt(LocalDateTime.now());
//            jobService.save(job);
//            StringEntity entity = new StringEntity(result.toString(), ContentType.APPLICATION_JSON);
//            HttpClient instance = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
//            HttpPost requestToHost = new HttpPost("http://localhost:8080/jobs/" + job.getIdJob());
//            requestToHost.setEntity(entity);
//
//            UsernamePasswordCredentials creds = new UsernamePasswordCredentials(user.getUsername(), user.getPassword());
//            requestToHost.addHeader(new BasicScheme().authenticate(creds, requestToHost, null));
//            HttpResponse responseToHost = instance.execute(requestToHost);
//        }
//    }
//
//
//
//	//PostMapping to get the result of an ordered job
//	//It's not the Postmapping for add a new job
//	@PostMapping("/debug")
//	public ResponseEntity<Void> savecreateResult(@RequestBody JobDTO dto, UriComponentsBuilder ucb, Principal principal){
//		
//		Assert.notNull(dto,"A dto cannot be null");
//		Job job = new Job(dto.getIdJob(), dto.getNameJob(), dto.getDescrJob(), dto.getStatusJob(), dto.getStartedAt(), dto.getEndAt());
//		if (dto.getHostList()!=null) {
//			for (HostDTO h : dto.getHostList()) {
//				
//				Host host = new Host(h.getIdHost(), h.getIpHost(), h.getIpHost(), h.isNew());
//				job.getHost().add(host);
//				host.setJob(job);
//				if(h.getPorts()!=null) {
//					for (PortDTO p : h.getPorts()) {
//						
//						Port port = new Port(p.getIdPort(),p.getProtocol(),p.getStatus());
//						host.getPorts().add(port);
//						port.setHost(host);
//						if(p.getServiceRunningOnPort()!=null) {
//							System.out.println("there's services running on the port");
//							for (ServiceDTO s : p.getServiceRunningOnPort()) {
//								
//								Service service = new Service(new Random().nextLong(), s.getNameService(), s.getVersionService(), s.getOsService());
//								port.getServiceRunningOnPort().add(service);
//								service.setPort(port);
//								if (s.getCvesService()!=null) {
//									System.out.println("there's cves on the services running on the port");
//									for (CVEDTO c : s.getCvesService()) {
//										
//										CVE cve = new CVE(c.getIdCVE(), c.getBaseScoreV2(), c.getBaseScoreV3(), c.getImpactScoreV2(), c.getImpactScoreV3(), c.getVectorV2(), c.getVectorV3(), c.getAttackVectorV2(), c.getAttackVectorV3(), c.getDescription(), service);
//										service.getCVEForService().add(cve);
//									}
//								}
//							}
//						}
//					}
//				}
//			}
//		}
//		Job savedJob = jobService.save(job);
//		URI location = ucb.path("/jobs/{idJob}").buildAndExpand(savedJob.getIdJob()).toUri();
//		return ResponseEntity.created(location).build();
//	}
//	
	
	

}
