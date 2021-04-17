package com.fastcampus.javaallinone.project3.mycontact.repository;

import com.fastcampus.javaallinone.project3.mycontact.domain.Person;
import com.fastcampus.javaallinone.project3.mycontact.domain.dto.Birthday;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class PersonRepositoryTest {
    @Autowired
    private PersonRepository personRepository;

    @Test
    void crud(){
        Person person = new Person();
        person.setName("John");
        person.setAge(10);
        person.setBloodType("A");

        personRepository.save(person);

        List<Person> result = personRepository.findByName("John");

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getName()).isEqualTo("John");
        assertThat(result.get(0).getAge()).isEqualTo(10);
        assertThat(result.get(0).getBloodType()).isEqualTo("A");
    }

    //혈액형으로 찾아보기
    @Test
    void findByBloodType(){
        List<Person> result = personRepository.findByBloodType("A");
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getName()).isEqualTo("Martin");
        assertThat(result.get(1).getName()).isEqualTo("Benny");

    }

    @Test
    void findBirthdayBetween(){
        //위에 생성된 얘들중에 8월인 애들만 찾아보는것
        List<Person> result = personRepository.findByMonthOfBirthday(8);
//        result.forEach(System.out::println);
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getName()).isEqualTo("Martin");
        assertThat(result.get(1).getName()).isEqualTo("Sophia");


    }
}