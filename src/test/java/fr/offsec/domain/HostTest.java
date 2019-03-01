package fr.offsec.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

public class HostTest {
	long id = new Random().nextLong();
	Host hostTest = new Host(id, "192.168.123.123", "Windaube", true);
	
	@Test (expected = IllegalArgumentException.class)
	public void shouldNotCreateWithNullId() {
		new Host(null, "192.168.123.123", "Windaube", true);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void shouldNotCreateWithNullIp() {
		new Host(id, null, "Windaube", true);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void shouldNotCreateWithEmptyorBlankIp() {
		new Host(id, "", "Windaube", true);
		new Host(new Random().nextLong(), " ", "Windaube", true);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void shouldNotCreateWithNullOs() {
		new Host(id, "192.168.123.123", null, true);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void shouldNotCreateWithEmptyorblankOs() {
		new Host(id, "192.168.123.123", "", true);
		new Host(new Random().nextLong(), "192.168.123.123", " ", true);
	}
	
	
	@Test ()
	public void shouldreturnvalidHost() {
		new Host(id, "192.168.123.123", "Windaube", true);
	}
	
	@Test()
	public void shouldUpdateIp() {
		hostTest.setIpHost("10.0.0.5");
		assertThat(hostTest.getIpHost()).isEqualTo("10.0.0.5");
	}
	
	@Test()
	public void shouldUpdateOs() {
		hostTest.setOperationSystem("WindaubeX");
		assertThat(hostTest.getOperationSystem()).isEqualTo("WindaubeX");
		
	}
	
	@Test()
	public void shouldUpdateIsNew() {
		hostTest.setNew(false);
		assertThat(hostTest.isNew()).isEqualTo(false);
		
	}

}
