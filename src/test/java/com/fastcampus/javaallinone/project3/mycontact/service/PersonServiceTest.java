package com.fastcampus.javaallinone.project3.mycontact.service;

import com.fastcampus.javaallinone.project3.mycontact.domain.Person;
import com.fastcampus.javaallinone.project3.mycontact.repository.PersonRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class personServiceTest {

    //    @InjectMocks -- 테스트 대상이 되는 목
    @InjectMocks
    private PersonService personService;
    @Mock
    private PersonRepository personRepository;


    //Mock 테스트는 전부를 보는게 아니라 부분부분을 파트로 나눠서 디테일하게 테스트할수 있게해준다.
    //
    @Test
    void getPeopleByName(){
        when(personRepository.findByName("martin"))
                .thenReturn(Lists.newArrayList(new Person("martin")));

        List<Person> result = personService.getPeopleByName("martin");

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getName()).isEqualTo("martin");
    }

//    @Test
//    void getPerson(){
//        Person person = personService.getPerson(3L);
//        assertThat(person.getName()).isEqualTo("dennis");
//    }

}
