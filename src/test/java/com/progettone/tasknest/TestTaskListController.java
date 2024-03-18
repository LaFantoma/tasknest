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
import com.progettone.tasknest.model.dto.tasklist.TasklistInstRqs;
import com.progettone.tasknest.model.dto.tasklist.TasklistMoveRqs;
import com.progettone.tasknest.model.dto.tasklist.TasklistPutRqs;
import com.progettone.tasknest.model.repositories.TasklistRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = TasknestApplication.class)
@AutoConfigureMockMvc
public class TestTaskListController {
    @Autowired
    private MockMvc mock;

    @Autowired
    private ObjectMapper om;

    @Test
    void newListTest() throws Exception {

        TasklistInstRqs body = TasklistInstRqs.builder()
                .idBoard(1)
                .title("test list")
                .build();

        TasklistInstRqs badBody = TasklistInstRqs.builder()
                .idBoard(1)
                .title("")
                .build();

        mock.perform(
                MockMvcRequestBuilders
                        .post("/list")
                        .content(om.writeValueAsString(body))
                        .contentType(MediaType.APPLICATION_JSON)

        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("New list succesfuly created!"));

        mock.perform(
                MockMvcRequestBuilders
                        .post("/list")
                        .content(om.writeValueAsString(badBody))
                        .contentType(MediaType.APPLICATION_JSON)

        )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content()
                        .string("Invalid body request!"));
    }

    @Test
    void putListTest() throws Exception {

        TasklistPutRqs body = TasklistPutRqs.builder()
                .idList(1)
                .title("test put title")
                .build();

        TasklistPutRqs badBody = TasklistPutRqs.builder()
                .idList(1)
                .title("")
                .build();

        mock.perform(
                MockMvcRequestBuilders
                        .put("/list/title")
                        .content(om.writeValueAsString(body))
                        .contentType(MediaType.APPLICATION_JSON)

        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("List hase been changed!"));

        mock.perform(
                MockMvcRequestBuilders
                        .put("/list/title")
                        .content(om.writeValueAsString(badBody))
                        .contentType(MediaType.APPLICATION_JSON)

        )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content()
                        .string("Invalid body request!"));
    }

    @Test
    void moveList() throws Exception {

        TasklistMoveRqs body = TasklistMoveRqs.builder()
                .idList(1)
                .newPosition(0)
                .build();

                TasklistMoveRqs badBody = TasklistMoveRqs.builder()
                .idList(1)
                .newPosition(null)
                .build();

        mock.perform(
                MockMvcRequestBuilders
                        .put("/list/position")
                        .content(om.writeValueAsString(body))
                        .contentType(MediaType.APPLICATION_JSON)

        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("New position setted!"));

        mock.perform(
                MockMvcRequestBuilders
                        .put("/list/position")
                        .content(om.writeValueAsString(badBody))
                        .contentType(MediaType.APPLICATION_JSON)

        )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content()
                        .string("Invalid body request!"));
    }
}
