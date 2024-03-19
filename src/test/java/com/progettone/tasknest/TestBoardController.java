package com.progettone.tasknest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.progettone.tasknest.model.dto.board.BoardDtoRspSimple;
import com.progettone.tasknest.model.dto.board.BoardDtoRspTaskname;
import com.progettone.tasknest.model.dtoservices.TasklistConverter;
import com.progettone.tasknest.model.repositories.BoardsRepository;
import com.progettone.tasknest.model.repositories.TasklistRepository;
import com.progettone.tasknest.model.repositories.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = TasknestApplication.class)
@AutoConfigureMockMvc
public class TestBoardController {

        @Autowired
        private MockMvc mock;

        @Autowired
        private ObjectMapper om;

        @Autowired
        BoardsRepository bRepo;

        @Autowired
        UserRepository uRepo;

        @Autowired
        TasklistRepository tlRepo;

        @Autowired
        TasklistConverter tlConv;

        @Test
        void BoardUserTest() throws Exception {

                List<BoardDtoRspSimple> res = new ArrayList<>();
                res.add(BoardDtoRspSimple.builder()
                                .title("titolo")
                                .description("board di prova")
                                .id(1)
                                .build());
                res.add(BoardDtoRspSimple.builder()
                                .title("totolo2")
                                .description("seconda board di prova")
                                .id(2)
                                .build());

                mock.perform // utente con due board
                (
                                MockMvcRequestBuilders
                                                .get("/boards/user/1")

                                                .contentType(MediaType.APPLICATION_JSON)

                )
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.content().json(om.writeValueAsString(res)));

                mock.perform // utente senza board
                (
                                MockMvcRequestBuilders
                                                .get("/boards/user/2")

                                                .contentType(MediaType.APPLICATION_JSON)

                )
                                .andExpect(MockMvcResultMatchers.status().isNotFound())
                                .andExpect(MockMvcResultMatchers.content().string("Nessuna board presente"));
        }

        @Test
        void BoardTest() throws Exception {

                BoardDtoRspTaskname response = BoardDtoRspTaskname.builder()
                                .title("titolo")
                                .description("board di prova")
                                .date_of_creation(LocalDate.parse("2024-03-18"))
                                .visible(true)
                                .my_users(uRepo.findAll().stream().filter(i -> i.getId() == 1).toList())
                                .id(1)
                                .my_tasklists(bRepo.findById(1).get().getMy_tasklists().stream()
                                                .map(i -> tlConv.tasklistToDtoRspName(i)).collect(Collectors.toSet()))
                                .build();

                mock.perform // board esistente
                (
                                MockMvcRequestBuilders
                                                .get("/boards/1")

                                                .contentType(MediaType.APPLICATION_JSON)

                )
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.content().json(om.writeValueAsString(response)));

                mock.perform // board inesistente
                (
                                MockMvcRequestBuilders
                                                .get("/boards/999")

                                                .contentType(MediaType.APPLICATION_JSON)

                )
                                .andExpect(MockMvcResultMatchers.status().isNotFound())
                                .andExpect(MockMvcResultMatchers.content().string("board inesistente"));
        }

        @Test
        void PutBoardTest() throws Exception {

        }
}
