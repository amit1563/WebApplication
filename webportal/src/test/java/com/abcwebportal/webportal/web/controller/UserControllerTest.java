package com.abcwebportal.webportal.web.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.abcwebportal.webportal.model.user.User;
import com.abcwebportal.webportal.serviceimpl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Test end to end User controller endpoints
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

	@Autowired
	private MockMvc mvc;
	@MockBean
	UserServiceImpl UserServiceImpl;
	@MockBean
	com.abcwebportal.webportal.base.error.validationerrorhandler.FieldErrorHandler FieldErrorHandler;
	@MockBean
	com.abcwebportal.webportal.validator.UserValidator UserValidator;

	@Test
	public void testSignUp_WithValidUserObject() throws Exception {

		User user = new User();
		user.setConfirmPassword("password");
		user.setPassword("password");
		user.setEmailId("abc@gmail.com");
		user.setFullName("ABC");
		user.setUsername("AAAA");

		mvc.perform(MockMvcRequestBuilders.post("/api/users/register").content(convertObjectToJsonString(user))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isCreated());
	}

	public static String convertObjectToJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonContent = mapper.writeValueAsString(obj);
			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
