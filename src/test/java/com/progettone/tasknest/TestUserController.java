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
import com.progettone.tasknest.model.dto.relog.LoginRqs;
import com.progettone.tasknest.model.dto.relog.RegisterRqs;
import com.progettone.tasknest.model.dto.user.UserDtoRspId;
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
        void refreshDb() {
                uRepo.refresh();
        }

        @Test
        void loginTest() throws Exception {
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

                mock.perform // tutti i campi validi
                (
                                MockMvcRequestBuilders
                                                .post("/user/login")
                                                .content(om.writeValueAsString(body))
                                                .contentType(MediaType.APPLICATION_JSON)

                )
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.content().json(om.writeValueAsString(response)));

                mock.perform // campi sbagliati
                (
                                MockMvcRequestBuilders
                                                .post("/user/login")
                                                .content(om.writeValueAsString(badBody))
                                                .contentType(MediaType.APPLICATION_JSON)

                )
                                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                                .andExpect(MockMvcResultMatchers.content().string("Invalid credentials"));
        }

        @Test
        void registerTest() throws Exception {

                RegisterRqs body = RegisterRqs.builder()
                                .email("chiarabelfiore@gmail.com")
                                .password("714212835#@")
                                .name("Chiara")
                                .build();

                RegisterRqs bodyMailSbagliata = RegisterRqs.builder()
                                .email("chiarabelfioregmail.com")
                                .password("714212835#@")
                                .name("Chiara")
                                .build();

                RegisterRqs bodyPasswordSbagliata = RegisterRqs.builder()
                                .email("chiarabelfiore@gmail.com")
                                .password("7142128")
                                .name("Chiara")
                                .build();

                mock.perform // tutti i campi validi
                (
                                MockMvcRequestBuilders
                                                .post("/user/register")
                                                .content(om.writeValueAsString(body))
                                                .contentType(MediaType.APPLICATION_JSON)

                )
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.content()
                                                .string("Registrazione avvenuta con successo"));

                mock.perform // mail non valida
                (
                                MockMvcRequestBuilders
                                                .post("/user/register")
                                                .content(om.writeValueAsString(bodyMailSbagliata))
                                                .contentType(MediaType.APPLICATION_JSON)

                )
                                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                                .andExpect(MockMvcResultMatchers.content()
                                                .string("La email non è valida"));

                mock.perform // password non valida
                (
                                MockMvcRequestBuilders
                                                .post("/user/register")
                                                .content(om.writeValueAsString(bodyPasswordSbagliata))
                                                .contentType(MediaType.APPLICATION_JSON)

                )
                                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                                .andExpect(MockMvcResultMatchers.content()
                                                .string("La password deve essere lunga almeno 8 caratteri e contenere almeno un carattere speciale (@, #, $, %, &, , !)."));
        }
}
