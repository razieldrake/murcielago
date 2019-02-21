package fr.offsec.domain;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;



public class CVETest {
	
	CVE cvetest  = new CVE("cvetest", 2.0f, 3.0f, 4.0f, 5.0f, "vector 2", "vector 3", "attackvector2", "attackVector3", "a cve test description", new Service());

	@Test(expected = IllegalArgumentException.class)
	public void shouldNotCreateWithNullId() {
		new CVE(null, 3f, 4f, 3f, 4f, "prout", "pourt", "prouti", "prouta","proutu", new Service());
	}
	@Test(expected = IllegalArgumentException.class)
	public void shouldNotCreateWithEmptyId() {
		new CVE("", 3f, 4f, 3f, 4f, "prout", "pourt", "prouti", "prouta","proutu", new Service());
	}
	@Test(expected = IllegalArgumentException.class)
	public void shouldNotCreateWithBlankID() {
		new CVE(" ", 3f, 4f, 3f, 4f, "prout", "pourt", "prouti", "prouta","proutu", new Service());
		
	}
	@Test(expected = IllegalArgumentException.class)
	public void shouldNotCreateWIthNullBScore1() {
		new CVE("coucou_test_cve", 0f, 4f, 3f, 4f, "prout", "pourt", "prouti", "prouta","proutu", new Service());
	}
	@Test(expected = IllegalArgumentException.class)
	public void shouldNotCreateWIthNullBScore2() {
		new CVE("coucou_test_cve", 3f, 0f, 3f, 4f, "prout", "pourt", "prouti", "prouta","proutu", new Service());
	}
	@Test(expected = IllegalArgumentException.class)
	public void shouldNotCreateWIthNullIScore1() {
		float c = 0f;
		new CVE("coucou_test_cve", 4f, 4f, c , 4f, "prout", "pourt", "prouti", "prouta","proutu", new Service());
	}
	@Test(expected = IllegalArgumentException.class)
	public void shouldNotCreateWIthNullIScore2() {
		new CVE("coucou_test_cve", 3f, 4f, 3f, 0f, "prout", "pourt", "prouti", "prouta","proutu", new Service());
	}
	@Test(expected = IllegalArgumentException.class)
	public void shouldNotCreateWIthNullDescription() {
		new CVE("coucou_test_cve", 3f, 4f, 3f, 4f, "prout", "pourt", "prouti", "prouta",null, new Service());
	}
	@Test(expected = IllegalArgumentException.class)
	public void shouldNotCreateWithBlankDescription() {
		new CVE("coucou_test_cve", 3f, 4f, 3f, 4f, "prout", "pourt", "prouti", "prouta"," ", new Service());
	}
	@Test(expected = IllegalArgumentException.class)
	public void shouldNotCreateWithEmptyDescription() {
		new CVE("coucou_test_cve", 3f, 4f, 3f, 4f, "prout", "pourt", "prouti", "prouta","", new Service());
	}
	@Test
	public void shouldReturnValidCve() {
		new CVE("coucou_test_cve", 3f, 4f, 3f, 4f, "prout", "pourt", "prouti", "prouta","descr", new Service());
	}
	@Test
	public void shouldUpdateBScoreV2() {
		cvetest.setBaseScoreV2(4.5f);
		assertThat(cvetest.getBaseScoreV2()).isEqualTo(4.5f);
		
	}
	@Test
	public void shouldUpdateBScoreV3() {
		cvetest.setBaseScoreV3(4.5f);
		assertThat(cvetest.getBaseScoreV3()).isEqualTo(4.5f);
		
	}
	@Test
	public void shouldUpdateIScoreV2() {
		cvetest.setImpactScoreV2(4.5f);
		assertThat(cvetest.getImpactScoreV2()).isEqualTo(4.5f);
		
	}
	@Test
	public void shouldUpdateIScoreV3() {
		cvetest.setImpactScoreV3(4.5f);
		assertThat(cvetest.getImpactScoreV3()).isEqualTo(4.5f);		
	}
	@Test
	public void shouldUpdatevector() {
		cvetest.setVectorv2("testvector2");
		cvetest.setVectorV3("testvector3");
		cvetest.setAttackvectorV2("testAttackVector2");
		cvetest.setAttackVectorV3("attackVector3");
		cvetest.setDescCVE("prout prout descritpnio");
		assertThat(cvetest.getVectorv2()).isEqualTo("testvector2");
		assertThat(cvetest.getVectorV3()).isEqualTo("testvector3");
		assertThat(cvetest.getAttackvectorV2()).isEqualTo("testAttackVector2");
		assertThat(cvetest.getAttackVectorV3()).isEqualTo("attackVector3");
		assertThat(cvetest.getDescCVE()).isEqualTo("prout prout descritpnio");
		
	}
	

	/*
	

	

	
	@Test
	public void shouldSerialize() throws JsonProcessingException {
		String id_cve = "CVE_2018_ID8";
		CVE cve = new CVE("CVE_2015_98",9.8f,"CVE test for Junit test");
		

		String json = "{\"id_cve\":\"" + id_cve.toString()
				+ "\",\"ais_cve\":9.8,\"descr_cve\":\"CVE test for Junit test\"}";
		ObjectMapper mapper = defaultMapper();
		String generatedJson = mapper.writeValueAsString(cve);
		System.out.println(generatedJson);
		assertThat(generatedJson).isEqualTo(json);
	}
	
	@Test
	public void shouldDeserialize() throws IOException {
		String id_cve = "CVE_2018_ID8";
		CVE cve = new CVE("CVE_2015_98",9.8f,"CVE test for Junit test");
		String json = "{\"id_cve\":\"" + id_cve.toString()
		+ "\",\"ais_cve\":9.8,\"descr_cve\":\"CVE test for Junit test\"}";

		ObjectMapper mapper = defaultMapper();
		assertThat(mapper.readValue(json, CVE.class)).isEqualToComparingFieldByField(cve);
	}
	
	private ObjectMapper defaultMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		return mapper;
	}
	
	*/

}
