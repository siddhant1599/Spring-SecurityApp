package com.springsecurity.SecurityApp.SecurityApp;

import com.springsecurity.SecurityApp.SecurityApp.entities.User;
import com.springsecurity.SecurityApp.SecurityApp.services.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SecurityAppApplicationTests {

	@Autowired
	private JwtService jwtService;

	@Test
	void contextLoads() {

		User testuser = new User(4L, "sid", "pass", "test@gmail.com");

		String token = jwtService.createToken(testuser);
		System.out.println(token);

		System.out.println( jwtService.getUserIdFromToken(token) );
	}

}
