package com.fastcampus.javaallinone.project3.mycontact.repository;

import com.fastcampus.javaallinone.project3.mycontact.domain.Person;
import com.fastcampus.javaallinone.project3.mycontact.domain.dto.Birthday;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static org.assertj.core.api.Assertions.assertThat;

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

    
//    //값이 같은지 hashcode가 같은지 확인하기
//    @Test
//    void hashCodeAndEquals(){
//        Person person1 = new Person("Martin", 10, "A");
//        Person person2 = new Person("Martin", 10, "A");
//
//        System.out.println(person1.equals(person2));
//        System.out.println(person1.hashCode());
//        System.out.println(person2.hashCode());
//
//        Map<Person, Integer> map = new HashMap<>();
//        map.put(person1, person1.getAge());
//
//        System.out.println(map);
//        System.out.println(map.get(person2));
//    }

    //혈액형으로 찾아보기
    @Test
    void findByBloodType(){
//        givenPerson("Martin", 10, "A");
//        givenPerson("David", 9, "B");
//        givenPerson("Dennis", 8, "O");
//        givenPerson("Sophia", 7, "AB");
//        givenPerson("Benny", 6, "A");
//        givenPerson("Jenny", 5, "A");

        List<Person> result = personRepository.findByBloodType("A");
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getName()).isEqualTo("Martin");
        assertThat(result.get(1).getName()).isEqualTo("Benny");

    }


    @Test
    void findBirthdayBetween(){
//        givenPerson("martin", 10, "A",LocalDate.of(1991,8,30));
//        givenPerson("David", 9, "B", LocalDate.of(1992,7,10));
//        givenPerson("Dennis", 8, "O", LocalDate.of(1993,1,5));
//        givenPerson("Benny", 6, "A", LocalDate.of(1995,8,1));
//        givenPerson("Jenny", 5, "A", LocalDate.of(1998,4,15));

        //위에 생성된 얘들중에 8월인 애들만 찾아보는것
        List<Person> result = personRepository.findByMonthOfBirthday(8);
//        result.forEach(System.out::println);
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getName()).isEqualTo("Martin");
        assertThat(result.get(1).getName()).isEqualTo("Sophia");


    }

    //메서드 오버로딩
//    private void givenPerson(String name, int age, String bloodType){
//        givenPerson(name, age, bloodType, null);
//    }
//
//    private void givenPerson(String name, int age, String bloodType, LocalDate birthday){
//        Person person = new Person(name, age, bloodType);
//        person.setBirthday(new Birthday(birthday));
//        personRepository.save(person);
//    }
}