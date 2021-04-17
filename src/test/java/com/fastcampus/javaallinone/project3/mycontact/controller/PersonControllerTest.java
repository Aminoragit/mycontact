package com.fastcampus.javaallinone.project3.mycontact.controller;

import com.fastcampus.javaallinone.project3.mycontact.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonControllerTest {

    @Autowired
    private PersonController personController;
    @Autowired
    private PersonRepository personRepository;


    private MockMvc mockMvc;

    @BeforeEach
    void beforeEach(){
        mockMvc = MockMvcBuilders.standaloneSetup(personController).build();

    }
    @Test
    @Order(1)
    void postPerson() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/person")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{   \"name\": \"martin2\",\n" +
                                "    \"age\": 20,\n" +
                                "    \"bloodType\": \"A\"}"))
                .andDo(print())
                .andExpect(status().isCreated());
    }


    @Test
    @Order(2)
    void getPerson() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/person/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }




    @Test
    @Order(3)
    void modifyPerson() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/person/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        //수정하지 않는 항목도 들어있어야 결과값이 null로 바뀌지 않음.
                        .content("{   \"name\": \"martin\",\n" +
                                "    \"age\": 20,\n" +
                                "    \"bloodType\": \"A\"}"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(4)
    void modifyName() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/person/1")
                        .param("name","martin22"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(5)
    void deletePerson() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/person/1"))
                .andDo(print())
                .andExpect(status().isOk());

        log.info("people deleted : {}",personRepository.findPeopleDeleted());
    }
}