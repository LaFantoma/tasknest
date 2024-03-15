package com.progettone.tasknest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
(
  webEnvironment = SpringBootTest.WebEnvironment.MOCK,
  classes = TasknestApplicationTests.class
)
@AutoConfigureMockMvc
class TasknestApplicationTests {

	//un MockMvc è un oggetto usato nelle classi di test
	//per simulare REQUEST verso il controller
	@Autowired
	MockMvc miniPostman;

	@Test
	void contextLoads() throws Exception
	{

		miniPostman.
		perform   //FAI LA REQUEST
		(
			MockMvcRequestBuilders.post("/user/login") //CHE TI IMPOSTO QUI
		)
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().string("Paghi 0"));
	}

}
