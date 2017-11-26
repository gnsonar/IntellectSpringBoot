package com.intellect.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;
import com.intellect.user.controller.UserController;
import com.intellect.user.controller.UserControllerImpl;
import com.intellect.user.model.User;


@RunWith(SpringRunner.class)
@WebMvcTest(UserControllerImpl.class)
public class IntellectUserMgmtSystemApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserController userController;
	
	
	@Test
	public void testSucessfullInsert() throws Exception {
		Gson gson = new Gson();
		User user = new User("","Gautam","Sonar","gautam.sonar89@gmail.com",123456,"17-Aug-1989");
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/IntellectUserMgmt/v1/user",user).contentType(MediaType.APPLICATION_JSON_UTF8)
								  .accept(MediaType.APPLICATION_JSON_UTF8).content(gson.toJson(user))).andReturn();
		
		int sts = result.getResponse().getStatus();
		
		assertEquals(HttpStatus.OK.value(), sts);
	}

}
