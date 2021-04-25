package com.fastcampus.javaallinone.project3.mycontact.service;

import com.fastcampus.javaallinone.project3.mycontact.controller.dto.PersonDto;
import com.fastcampus.javaallinone.project3.mycontact.domain.Person;
import com.fastcampus.javaallinone.project3.mycontact.exception.PersonNotFoundException;
import com.fastcampus.javaallinone.project3.mycontact.exception.RenameNotPermittedException;
import com.fastcampus.javaallinone.project3.mycontact.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    public List<Person> getPeopleByName(String name) {
        //Repository에 findByName을 작성하면 간편하게 작성 가능
        //        List<Person> people = personRepository.findAll();
        //        return people.stream().filter(person->person.getName().equals(name)).collect(Collectors.toList());

        return personRepository.findByName(name);
    }

    @Transactional
    public List<Person> getPersonBirthday(){
        //비교할값들
        Integer startDatetimeMonth = LocalDate.now().getMonthValue();//오늘 월
        Integer startDatetimeDay = LocalDate.now().getDayOfMonth();//오늘 일
        Integer endDateTimeMonth = LocalDate.now().plusDays(1).getMonthValue(); //내일 월
        Integer endDateTimeDay = LocalDate.now().plusDays(1).getDayOfMonth(); //내일 일
        List<Person> result = personRepository.findByBirthday(startDatetimeMonth,startDatetimeDay,endDateTimeMonth,endDateTimeDay);
        return result;
    }



    @Transactional(readOnly = true)
    public Person getPerson(Long id) {
//        Person person = personRepository.findById(id).get();
//        Person person = personRepository.findById(id).orElse(null);

          //sout은 모든 실행을 다 보여주지만 log는 원하는것만 표시해준다.
//        log.info("person: {}", person);
        return personRepository.findById(id).orElse(null);
    }

    @Transactional
    public void put(PersonDto personDto) {
        Person person = new Person();
        person.set(personDto);
        person.setName(personDto.getName());
        personRepository.save(person);
    }

    @Transactional
    public void modify(Long id, PersonDto personDto) {
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);

        if (!person.getName().equals(personDto.getName())) {
            throw new RenameNotPermittedException();
        }

        person.set(personDto);

        personRepository.save(person);
    }


    //커스텀 Exception PersonNotFoundException으로 runtimeException을 기반으로 한 좀더 세분화된 exception을 만들수 있다.
    @Transactional
    public void modify(Long id, String name) {
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);

        //        Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException("아이디가 존재하지 않습니다."));
        person.setName(name);
        personRepository.save(person);
    }

    @Transactional
    public void delete(Long id) {
        //잘못된 request로 바로 delete하면 DB가 손상될수 있으니 deleted로 확인하고 db에 업로드하는 방식을 사용한다.
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        person.setDeleted(true);
        personRepository.save(person);
    }

    public Page<Person> getAll(Pageable pageable) {
        return personRepository.findAll(pageable);
    }

}
