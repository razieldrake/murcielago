package fr.offsec.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Random;

import org.junit.Test;

public class ServiceTest {
	long idtest = new Random().nextLong();
	Service serviceTest = new Service(idtest, "service test", "8.8.8", "TesTOS");

	@Test (expected = IllegalArgumentException.class)
	public void shouldNotCreateWithNullId() {
		new Service(null,"service test","8.8.8","testos");
	}
	@Test(expected = IllegalArgumentException.class)
	public void shouldNotCreateWithZeroID() {
		new Service ((long) (0),"service test","8.8.8","testOs");
	}
	@Test(expected = IllegalArgumentException.class)
	public void shouldNotCreateWithNoName() {
		new Service(new Random().nextLong(),null,"8.8.8","testOS");
	}
	@Test(expected = IllegalArgumentException.class)
	public void shouldNotCreateWithBlacnkName() {
		
		new Service(new Random().nextLong(),"","8.8.8","testOS");
	}
	@Test(expected = IllegalArgumentException.class)
	public void shouldNotCreateWithEmptyName() {
		
		new Service(new Random().nextLong()," ", "8.8.8","testOS");
	}
	@Test(expected = IllegalArgumentException.class)
	public void shouldNotCreateWithNIncompatibleVersion() {
		new Service(new Random().nextLong(),"name service test", null, "testOS");
		new Service(new Random().nextLong(),"name service test", "","testOS");
		new Service(new Random().nextLong(),"name service test"," ","testOS");
	}
	@Test(expected = IllegalArgumentException.class)
	public void shouldNotCreateWithIcompatibleOs() {
		new Service(new Random().nextLong(),"name service test","8.8.8",null);
		new Service(new Random().nextLong(),"name service test","8.8.8","");
		new Service(new Random().nextLong(),"name service test","8.8.8"," ");
	}
	
	@Test()
	public void shouldReturnValidService() {
		new Service(new Random().nextLong(),"name service test","8.8.8","plop");
	}
	@Test()
	public void shouldUpdateNameService() {
		serviceTest.setNameService("coucou name service");
		assertThat(serviceTest.getNameService()).isEqualTo("coucou name service");
	}
	@Test()
	public void shouldUpdateVersionService() {
		serviceTest.setVersionService("new version service test");
		assertThat(serviceTest.getVersionService()).isEqualTo("new version service test");
	}
	@Test()
	public void shouldUpdateOsService() {
		serviceTest.setGuessedOSService("new os for test");
		assertThat(serviceTest.getGuessedOSService()).isEqualTo("new os for test");
	}


}
