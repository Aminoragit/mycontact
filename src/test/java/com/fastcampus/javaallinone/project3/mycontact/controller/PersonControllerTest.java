package com.fastcampus.javaallinone.project3.mycontact.controller;

import com.fastcampus.javaallinone.project3.mycontact.controller.dto.PersonDto;
import com.fastcampus.javaallinone.project3.mycontact.domain.Person;
import com.fastcampus.javaallinone.project3.mycontact.domain.dto.Birthday;
import com.fastcampus.javaallinone.project3.mycontact.repository.PersonRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonControllerTest {

//    @Autowired
//    private PersonController personController;
//    @Autowired
//    private MappingJackson2HttpMessageConverter messageConverter;
//
//    @Autowired
//    private GlobalExceptionHandler globalExceptionHandler;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @BeforeEach
    void beforeEach(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .alwaysDo(print())
                .build();

    }
    @Test
    @Order(1)
    void postPerson() throws Exception{
        PersonDto dto = PersonDto.of("martin","programming","??????",LocalDate.now(),"programmer","010-1111-2222");
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/person")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(toJsonString(dto)))
                .andExpect(status().isCreated());

        Person result= personRepository.findAll(Sort.by(Sort.Direction.DESC,"id")).get(0);
        System.out.println(result);
        assertAll(
                ()-> assertThat(result.getName()).isEqualTo("martin"),
                ()-> assertThat(result.getHobby()).isEqualTo("programming"),
                ()-> assertThat(result.getAddress()).isEqualTo("??????"),
                ()-> assertThat(result.getBirthday()).isEqualTo(Birthday.of(LocalDate.now())),
                ()-> assertThat(result.getJob()).isEqualTo("programmer"),
                ()-> assertThat(result.getPhoneNumber()).isEqualTo("010-1111-2222")
        );

    }



    @Test
    @Order(2)
    void getPerson() throws Exception {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/person/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("martin"))
            .andExpect(jsonPath("$.hobby").isEmpty())
            .andExpect(jsonPath("$.address").isEmpty())
            .andExpect(jsonPath("$.birthday").value("1991-08-15"))
            .andExpect(jsonPath("$.job").isEmpty())
            .andExpect(jsonPath("$.phoneNumber").isEmpty())
            .andExpect(jsonPath("$.deleted").value(false))
        ;
    }


    @Test
    void getPersonByBirthday() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/person/birthday-friends"))
                .andExpect(status().isOk());
    }

    @Test
    void postPersonIfNameIsEmptyString() throws Exception{
        PersonDto dto = new PersonDto();
        dto.setName("");

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/person")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .content(toJsonString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("????????? ??????????????????."));
    }



    @Test
    void postPersonIfNameIsBlankString() throws Exception{
        PersonDto dto = new PersonDto();
        dto.setName(" ");

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/person")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .content(toJsonString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("????????? ??????????????????."));
    }


    //?????? Person ???????????? ???????????? test
    @Test
    void getAll() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/person")
        .param("page","1")
        .param("size","2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPages").value(4))
                .andExpect(jsonPath("$.totalElements").value(8))
                .andExpect(jsonPath("$.numberOfElements").value(2))
        .andExpect(jsonPath("$.content.[0].name").value("dennis"))
        .andExpect(jsonPath("$.content.[1].name").value("sophia"))
;
    }





    @Test
    @Order(3)
    void modifyPerson() throws Exception{
        PersonDto dto = PersonDto.of("martin","programming","??????",LocalDate.now(),"programmer","010-1111-2222");

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/person/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        //???????????? ?????? ????????? ??????????????? ???????????? null??? ????????? ??????.
                        .content(toJsonString(dto)))
                .andExpect(status().isOk());

        Person result=personRepository.findById(1L).get();

        //????????? ???????????? ?????? ??????
        assertAll(
            ()-> assertThat(result.getName()).isEqualTo("martin"),
            ()-> assertThat(result.getHobby()).isEqualTo("programming"),
            ()-> assertThat(result.getAddress()).isEqualTo("??????"),
            ()-> assertThat(result.getBirthday()).isEqualTo(Birthday.of(LocalDate.now())),
            ()-> assertThat(result.getJob()).isEqualTo("programmer"),
            ()-> assertThat(result.getPhoneNumber()).isEqualTo("010-1111-2222")
        );
    }

    @Test
    @Order(3)
    void modifyPersonIfNameIsDifferent() throws Exception{
        PersonDto dto = PersonDto.of("james","programming","??????",LocalDate.now(),"programmer","010-1111-2222");

            mockMvc.perform(
                    MockMvcRequestBuilders.put("/api/person/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(toJsonString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("?????? ????????? ???????????? ????????????."));
    }


    @Test
    void modifyPersonIfPersonNotFound() throws Exception{
        PersonDto dto = PersonDto.of("james","programming","??????",LocalDate.now(),"programmer","010-1111-2222");
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/person/10")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(toJsonString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("Person Entity??? ???????????? ????????????."));

    }


    @Test
    @Order(4)
    void modifyName() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/person/1")
                        .param("name","martinModified"))
                .andExpect(status().isOk());
        assertThat(personRepository.findById(1L).get().getName()).isEqualTo("martinModified");

    }

    @Test
    @Order(5)
    void deletePerson() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/person/1"))
                .andExpect(status().isOk());
        assertTrue(personRepository.findPeopleDeleted().stream().anyMatch(person->person.getId().equals(1L)));


//                .andExpect(content().string("true"));
//        log.info("people deleted : {}",personRepository.findPeopleDeleted());
    }

//    @Test
//    void checkJsonString() throws  JsonProcessingException{
//        PersonDto dto = new PersonDto();
//        dto.setName("martin");
//        dto.setBirthday(LocalDate.now());
//        dto.setAddress("??????");
//
//        System.out.println(toJsonString(dto));
//    }

    @Test
    void postPersonIfNameIsNull() throws Exception{
        PersonDto dto = new PersonDto();

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/person")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(toJsonString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("????????? ??????????????????."));
    }




    private String toJsonString(PersonDto personDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(personDto);
    }

}
