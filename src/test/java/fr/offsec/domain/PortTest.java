package fr.offsec.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import org.junit.Test;

public class PortTest {
	
	Port porttest = new Port(99, "TCProut", "is statufied");

	@Test (expected = IllegalArgumentException.class)
	public void shouldNotCreateWithNullNumber() {
		
		new Port(0, "TestCP", "occupy");
		
	}
	@Test (expected = IllegalArgumentException.class)
	public void shouldNotCreateWithNullProtocol() {
		new Port(42,null, "occupy");
	}
	@Test (expected = IllegalArgumentException.class)
	public void shouldNotCreateWithEmptyString() {
		new Port(42, "", "occupy");
	}
	@Test (expected = IllegalArgumentException.class)
	public void shouldNotCreateWithBlankString()
	{
		new Port(42, " ", "occupy");
	}
	@Test (expected = IllegalArgumentException.class)
	public void shouldNotCreateWithNullStatus() {
		new Port(42,"TCP", null);
	}
	@Test (expected = IllegalArgumentException.class)
	public void shouldNotCreateWithEmptyStringForStatus() {
		new Port(42, "TCP", "");
	}
	@Test (expected = IllegalArgumentException.class)
	public void shouldNotCreateWithBlankStringForStatus()
	{
		new Port(42, "TCP", " ");
	}
	@Test()
	public void shouldreturnValidPort() {
		new Port(42, "TCP", "statufied");
	}
	
	@Test()
	public void shouldUpdateIdPort() {
		porttest.setIdPort(24);
		assertThat(porttest.getIdPort()).isEqualTo(24);
	}
	@Test()
	public void shouldUpdateprotocolPort() {
		porttest.setProtocol("UDP");
		assertThat(porttest.getProtocol()).isEqualTo("UDP");
	}
	@Test()
	public void shouldUpdateStatusPort() {
		porttest.setStatus("Pretending sleeping");
		assertThat(porttest.getStatus()).isEqualTo("Pretending sleeping");
	}
	

}
