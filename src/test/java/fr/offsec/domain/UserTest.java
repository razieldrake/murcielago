package fr.offsec.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

public class UserTest {

	static final long id = new Random().nextLong();
	static final String user = "Usertest";
	static final String pwd = "lolilol";
	static final String role = "ADMIN";
	User userTest = new User(id, user, pwd, role);
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldNotCreateWithNullId() {
		new User(null, user, pwd, role);
	}
	@Test(expected = IllegalArgumentException.class)
	public void shouldNotCreateWithNullName() {
		new User(id, null, pwd, role);
		
	}
	@Test(expected = IllegalArgumentException.class)
	public void shouldNotCreateWithNullPasssword() {
		new User(id, user, null, role);
	}
	@Test(expected = IllegalArgumentException.class)
	public void shouldNotCreateWithNullRole() {
		new User(id, user, pwd, null);
	}
	@Test(expected = IllegalArgumentException.class)
	public void shouldNotCreateWithBlankEmptyName() {
		new User(id, "", pwd, role);
		new User(id+1, " ", pwd, role);
		
	}
	@Test(expected = IllegalArgumentException.class)
	public void shouldNotCreateWithEmptyBlankPassword() {
		new User(id, user, "", role);
		new User(id+1, user, " ", role);
	}
	@Test(expected = IllegalArgumentException.class)
	public void shouldNotCreateWithEmptyBlankrole() {
		new User(id, user, pwd, "");
		new User(id+1, user, pwd, " ");
		
	}
	@Test
	public void shouldReturnValiduser() {
		new User(id, user, pwd, role);
	}
	@Test
	public void shouldUpdateUsername() {
		userTest.setUsername("testing username");
		assertThat(userTest.getUsername()).isEqualTo("testing username");
	}
	@Test
	public void shouldUpdatePassword() {
		userTest.setPassword("a new password");
		assertThat(userTest.getPassword()).isEqualTo("a new password");
		
	}
	@Test
	public void shouldUpdateRole() {
		userTest.setRole("USER");
		assertThat(userTest.getRole()).isEqualTo("USER");
	}
	

}
