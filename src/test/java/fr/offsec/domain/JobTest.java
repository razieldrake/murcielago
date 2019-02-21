package fr.offsec.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.Random;

import org.junit.Test;

public class JobTest {

	static long  id = new Random().nextLong();
	static String name = "jobtest";
	static String descr = "Testing job";
	static String status = "testing";
	static LocalDateTime strt= LocalDateTime.of(2018, 12, 24, 22, 23);
	static LocalDateTime endt= LocalDateTime.of(2018, 12, 24, 22, 24);
	Job jobTest = new Job(id, name, descr, status,strt , endt);
	
	@Test (expected = IllegalArgumentException.class)
	public void shouldNotCreateWithNullId() {
		new Job(null, "jobtest", "a testing job", "tesing",LocalDateTime.of(2018, 12, 24, 22, 23) , LocalDateTime.of(2018, 12, 24, 22, 24));
		
	}
	@Test (expected = IllegalArgumentException.class)
	public void shouldNotCreateWithNullName() {
		new Job(id, null, "a testing job", "tesing",LocalDateTime.of(2018, 12, 24, 22, 23) , LocalDateTime.of(2018, 12, 24, 22, 24));
		
	}
	@Test (expected = IllegalArgumentException.class)
	public void shouldNotCreateWithNullDescription() {
		new Job(id, "jobtest", null, "tesing",LocalDateTime.of(2018, 12, 24, 22, 23) , LocalDateTime.of(2018, 12, 24, 22, 24));
	}
	@Test (expected = IllegalArgumentException.class)
	public void shouldNotCreateWithNullStatus() {
		new Job(id, "jobtest", "a testing job", null,LocalDateTime.of(2018, 12, 24, 22, 23) , LocalDateTime.of(2018, 12, 24, 22, 24));
	}
	@Test (expected = IllegalArgumentException.class)
	public void shouldNotCreateWithNullStartedDate() {
		new Job(id, "jobtest", "a testing job", "tesing",null , LocalDateTime.of(2018, 12, 24, 22, 24));
		
	}
	@Test (expected = IllegalArgumentException.class)
	public void shouldNotCreateWithNullEndDate() {
		new Job(id, "jobtest", "a testing job", "tesing",LocalDateTime.of(2018, 12, 24, 22, 23) , null);
		
	}
	@Test (expected = IllegalArgumentException.class)
	public void shouldNotCreateWithEmptyOrBlankForName() {
		new Job(id, "", "a testing job", "tesing",LocalDateTime.of(2018, 12, 24, 22, 23) , LocalDateTime.of(2018, 12, 24, 22, 24));
		new Job(id+1, " ", "a testing job", "tesing",LocalDateTime.of(2018, 12, 24, 22, 23) , LocalDateTime.of(2018, 12, 24, 22, 24));
		
	}
	@Test (expected = IllegalArgumentException.class)
	public void shouldNotCreateWithEmptyOrBlankForDescription() {
		new Job(id, "jobtest", "", "tesing",LocalDateTime.of(2018, 12, 24, 22, 23) , LocalDateTime.of(2018, 12, 24, 22, 24));
		new Job(id+1, "jobtest", " ", "tesing",LocalDateTime.of(2018, 12, 24, 22, 23) , LocalDateTime.of(2018, 12, 24, 22, 24));
		
	}
	@Test (expected = IllegalArgumentException.class)
	public void shouldNotCreateWithEmptyOrBlankStatus() {
		new Job(id, "jobtest", "a testing job", "",LocalDateTime.of(2018, 12, 24, 22, 23) , LocalDateTime.of(2018, 12, 24, 22, 24));
		new Job(id+1, "jobtest", "a testing job", " ",LocalDateTime.of(2018, 12, 24, 22, 23) , LocalDateTime.of(2018, 12, 24, 22, 24));
		
	}
	
	
	@Test
	public void shouldCreateValidJob() {
		
		new Job(id, name, descr, status, strt, endt);
		
	}
	@Test
	public void shouldUpdateName() {
		jobTest.setNameJob("plop");
		assertThat(jobTest.getClass()).isEqualTo("plop");		
	}
	@Test
	public void shouldUpdateDescr() {
		jobTest.setDescrJob("plouf");
		assertThat(jobTest.getDescrJob()).isEqualTo("plouf");		
	}
	@Test
	public void shouldUpdateStatus() {
		jobTest.setStatusJob("pending or not");
		assertThat(jobTest.getStatusJob()).isEqualTo("pending or not");
		
	}
	@Test
	public void shouldUpdateStartedDate() {
		jobTest.setStartedAt(endt);
		assertThat(jobTest.getStartedAt()).isEqualTo(endt);
	}
	@Test
	public void shouldUpdateEndDate() {
		jobTest.setEndAt(strt);
		assertThat(jobTest.getEndAt()).isEqualTo(strt);
		
	}
	@Test
	public void shouldHaveOrderedDate() {
		jobTest.getStartedAt().isBefore(jobTest.getEndAt());
		
	}
	


}
