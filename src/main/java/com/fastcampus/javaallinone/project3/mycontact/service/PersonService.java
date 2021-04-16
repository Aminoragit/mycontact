package com.fastcampus.javaallinone.project3.mycontact.service;

import com.fastcampus.javaallinone.project3.mycontact.domain.Person;
import com.fastcampus.javaallinone.project3.mycontact.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    public List<Person> getPeopleExcludeBlocks(){
        return personRepository.findByBlockIsNull();
    }

    public List<Person> getPeopleByName(String name){
        //Repository에 findByName을 작성하면 간편하게 작성 가능
        //        List<Person> people = personRepository.findAll();
        //        return people.stream().filter(person->person.getName().equals(name)).collect(Collectors.toList());

        return personRepository.findByName(name);
    }



    @Transactional(readOnly = true)
    public Person getPerson(Long id){
        Person person = personRepository.findById(id).get();
        System.out.println("person : " + person);

        //sout은 모든 실행을 다 보여주지만 log는 원하는것만 표시해준다.
        log.info("person: {}",person);
        return person;
    }
}
