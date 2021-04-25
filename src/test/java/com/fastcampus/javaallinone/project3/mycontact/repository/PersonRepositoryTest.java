package com.fastcampus.javaallinone.project3.mycontact.repository;

import com.fastcampus.javaallinone.project3.mycontact.domain.Person;
import com.fastcampus.javaallinone.project3.mycontact.domain.dto.Birthday;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Transactional
@SpringBootTest
class PersonRepositoryTest {
    @Autowired
    private PersonRepository personRepository;

    @Test
    void findByName() {
        List<Person> people = personRepository.findByName("tony");
        assertThat(people.size()).isEqualTo(1);

        Person person = people.get(0);
        assertAll(
                () -> assertThat(person.getName()).isEqualTo("tony"),
                () -> assertThat(person.getHobby()).isEqualTo("reading"),
                () -> assertThat(person.getAddress()).isEqualTo("서울"),
                () -> assertThat(person.getBirthday()).isEqualTo(Birthday.of(LocalDate.of(2021, 7, 10))),
                () -> assertThat(person.getJob()).isEqualTo("officer"),
                () -> assertThat(person.getPhoneNumber()).isEqualTo("010-2222-5555"),
                () -> assertThat(person.isDeleted()).isEqualTo(false)
        );
    }

    @Test
    void findByNameIfDeleted(){
        List<Person> people=personRepository.findByName("andrew");
        assertThat(people.size()).isEqualTo(0);
    }

    @Test
    void findByMonthOfBirthday(){
        List<Person> people = personRepository.findByMonthOfBirthday(7);

        System.out.println(people);
        assertThat(people.size()).isEqualTo(2);
        assertAll(
                ()->assertThat(people.get(0).getName()).isEqualTo("david"),
                ()->assertThat(people.get(1).getName()).isEqualTo("tony")
        );
    }



    @Test
    void findPeopleDeleted(){
        List<Person> people = personRepository.findPeopleDeleted();
        assertThat(people.size()).isEqualTo(1);
        assertThat(people.get(0).getName()).isEqualTo("andrew");

    }

    @Test
    void findByBirthday() {
        Integer startDatetimeMonth = LocalDate.now().getMonthValue();//오늘 월월-일일
        Integer startDatetimeDay = LocalDate.now().getDayOfMonth();//오늘 월월-일일
        Integer endDateTimeMonth = LocalDate.now().plusDays(1).getMonthValue();
        Integer endDateTimeDay = LocalDate.now().plusDays(1).getDayOfMonth();

        List<Person> result = personRepository.findByBirthday(startDatetimeMonth,startDatetimeDay,endDateTimeMonth,endDateTimeDay);
        System.out.println(result);
        assertThat(result.size()).isEqualTo(2);
        assertAll(
                ()->assertThat(result.get(0).getName()).isEqualTo("hans"),
                ()->assertThat(result.get(1).getName()).isEqualTo("hans2")
        );
        System.out.println(result);
//        System.out.println(startDatetimeMonth);
//        System.out.println(startDatetimeDay);
//        System.out.println(endDateTimeMonth);
//        System.out.println(endDateTimeDay);

        }
}


//    @Test
//    void crud(){
//        Person person = new Person();
//        person.setName("john");
//
//        personRepository.save(person);
//
//        List<Person> result = personRepository.findByName("john");
//
//        assertThat(result.size()).isEqualTo(1);
//        assertThat(result.get(0).getName()).isEqualTo("john");
//        assertThat(result.get(0).getAge()).isEqualTo(10);
//        assertThat(result.get(0).getBloodType()).isEqualTo("A");


//    @Test
//    void findBirthdayBetween(){
//        //위에 생성된 얘들중에 8월인 애들만 찾아보는것
//        List<Person> result = personRepository.findByMonthOfBirthday(8);
//        assertThat(result.size()).isEqualTo(2);
//        assertThat(result.get(0).getName()).isEqualTo("martin");
//        assertThat(result.get(1).getName()).isEqualTo("sophia");
//
//
//    }
