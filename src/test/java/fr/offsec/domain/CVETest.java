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

/*	@Test(expected = IllegalArgumentException.class)
	public void shouldNotCreateWithNullID() {
		
		new CVE(null, 9.8f, "CVE test for Junit test");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldNOtCreateWithEmptyID()
	{
		new CVE("",9.8f,"CVE test for Junit test");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldNotCreateWithBlankID() {
		new CVE(" ",9.8f,"CVE test for Junit Test");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouoldNOtCreateWithNullNote() {
		new CVE("CVE_2015_98",0,"CVE test for Junit test");
	}
	@Test(expected = IllegalArgumentException.class)
	public void shouldNotCreateWithNullDescription() {
		new CVE("CVE_2015_98",9.8f,null);
		
	}
	@Test(expected = IllegalArgumentException.class)
	public void shouldNOtCreateWithEmptyDescription() {
		new CVE("CVE_2015_ID25",9.8f,"");
	}
	@Test(expected = IllegalArgumentException.class)
	public void shouldNotCreateWithBlankDescription() {
		new CVE("CVE_2015_ID25",9.8f," ");
	}
	
	@Test
	public void shouldUpdateAis() {
		
		CVE cve = new CVE("CVE_2015_98",9.8f,"CVE test for Junit test");
		cve.setAis(4.5f);
		assertThat(cve.getAis()).isEqualTo(4.5f);
		
	}
	@Test
	public void shouldSetIDCVE() {
		CVE cve = new CVE("CVE_2015_98",9.8f,"CVE test for Junit test");
		cve.setIDCVE("CVE_2015_ID8");
		assertThat(cve.getIdCVE()).isEqualTo("CVE_2015_ID8");
		
	}
	@Test
	public void shoudSetDescription() {
		CVE cve = new CVE("CVE_2015_98",9.8f,"CVE test for Junit test");
		cve.setDescr("A new descritpnio text for this cve test");
		assertThat(cve.getDescr()).isEqualTo("A new descritpnio text for this cve test");
	}
	
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
