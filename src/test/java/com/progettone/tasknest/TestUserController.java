package com.progettone.tasknest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.progettone.tasknest.model.dto.UserDtoRspId;
import com.progettone.tasknest.model.dto.relog.LoginRqs;
import com.progettone.tasknest.model.entities.User;
import com.progettone.tasknest.model.repositories.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = TasknestApplication.class)
@AutoConfigureMockMvc
public class TestUserController {

    @Autowired
    private MockMvc mock;

    @Autowired
    private UserRepository uRepo;

    @Autowired
    private ObjectMapper om;

    @BeforeEach
    void refreshDb()
    {
        uRepo.refresh();
    }

    @Test
    void contextLoads() throws Exception {
        // User u = om.readValue("json", User.class); json to Object

        LoginRqs body = LoginRqs.builder()
                .email("gastonecanessa@gmail.com")
                .password("714212835#@")
                .build();

        LoginRqs badBody = LoginRqs.builder()
                .email("gastonecanessa@gmail.com")
                .password("714112835#@")
                .build();

        UserDtoRspId response = UserDtoRspId.builder()
                .email("gastonecanessa@gmail.com")
                .name("Gastone")
                .id(1)
                .build();

        mock.perform // FAI LA REQUEST
        (
                MockMvcRequestBuilders
                        .post("/user/login")
                        .content(om.writeValueAsString(body))
                        .contentType(MediaType.APPLICATION_JSON)
                        

        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(om.writeValueAsString(response)));

        mock.perform // FAI LA REQUEST
        (
                MockMvcRequestBuilders
                        .post("/user/login")
                        .content(om.writeValueAsString(badBody))
                        .contentType(MediaType.APPLICATION_JSON)

        )
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.content().string("Invalid credentials"));
    }

}
