package com.fastcampus.javaallinone.project3.mycontact.repository;

import com.fastcampus.javaallinone.project3.mycontact.domain.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        person.setName("Martin");
        person.setAge(10);
        person.setBloodType("A");
        personRepository.save(person);

        System.out.println(personRepository.findAll());
        List<Person> people = personRepository.findAll();
        assertThat(people.size()).isEqualTo(1);
        assertThat(people.get(0).getName()).isEqualTo("Martin");
        assertThat(people.get(0).getAge()).isEqualTo(10);
        assertThat(people.get(0).getBloodType()).isEqualTo("A");

    }

   @Test
    void hashCodeAndEquals(){
       Person person1= new Person("martin",10,"A");
       Person person2= new Person("martin",10,"A");

       System.out.println(person1.equals(person2)); //Person에서 입력한 equals에 의해 값이 동일한지 확인
       System.out.println(person1.hashCode()); //값은 동일하지만 hashcode는 다르다는걸 알수 있다.
       System.out.println(person2.hashCode());



       //왜 hashCode를 통해 가져오면 안되지?-> DB를 호출할때마다 변경되는 HashCode는 불안정하다
       //따라서 값을 통해 판단하는것이 좋다.
       Map<Person,Integer> map =new HashMap<>();
       map.put(person1,person1.getAge());

       System.out.println(map);
       System.out.println(map.get(person2));

    }






}