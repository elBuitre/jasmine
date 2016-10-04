package com.bui3;

import static org.assertj.core.api.Assertions.assertThat;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTests {

	@Autowired
	private UserRepository repository;
	
	private User user;
	
	private final String USERNAME = "elbuitre";
	private final String FIRST_NAME = "Stefano";
	private final String LAST_NAME = "Carniel";
	private final String PASSWORD = "pwd";
	private final String FAKE_USER = "fakeuser";
	private final int ROLE = 1;
	private final int TOTAL_USERS = 10;
	
	@Test
	public void addUserTest() {
		user = new User();
		user.setFirstname(FIRST_NAME);
		user.setLastname(LAST_NAME);
		user.setUsername(USERNAME);
		user.setPassword(PASSWORD);
		user.setRole(ROLE);
		
		// Add the user
		repository.save(user);
		
		// Try to retrieve user information
		user = repository.findOne("elbuitre");
		
		// Check user information
		assertThat(user.getFirstname()).isEqualTo(FIRST_NAME);
		assertThat(user.getLastname()).isEqualTo(LAST_NAME);
		assertThat(user.getRole()).isEqualTo(ROLE);
		assertThat(user.getPassword()).isEqualTo(PASSWORD);
		
		// Try to delete the user
		repository.delete(USERNAME);
		
		user = repository.findOne(USERNAME);
		assertThat(user).isNull();
	}
	
	@Test
	public void nonExistentUserTest() {
		user = repository.findOne(FAKE_USER);
		assertThat(user).isNull();
	}
	
	@Test
	public void userListTest() {
		for (int i=0; i<TOTAL_USERS; i++)
		{
			user = new User();
			user.setUsername("user" + i);
			user.setFirstname("Firstname_" + i);
			user.setLastname("Lastname_" + i);
			user.setPassword("Password_" + i);
			user.setRole(i);
			
			repository.save(user);
		}
		
		int addedUsers = repository.findAll().size();
		assertThat(addedUsers).isEqualTo(TOTAL_USERS);
	}
	
	@Test
	public void userExpired() {
		LocalDate expireon = new LocalDate();
		expireon = LocalDate.now().minusDays(1);
		user = new User();
		user.setFirstname(FIRST_NAME);
		user.setLastname(LAST_NAME);
		user.setUsername(USERNAME);
		user.setPassword(PASSWORD);
		user.enable(true);
		user.setExpireon(expireon);
		
		assertThat(user.isEnabled()).isEqualTo(false);
	}
}
