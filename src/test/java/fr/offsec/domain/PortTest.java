package fr.offsec.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class PortTest {
	
	Port porttest = new Port(99, "TCProut", "is statufied");

	@Test (expected = IllegalArgumentException.class)
	public void shouldNotCreate

}
