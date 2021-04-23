package com.fastcampus.javaallinone.project3.mycontact.service;

import com.fastcampus.javaallinone.project3.mycontact.controller.dto.PersonDto;
import com.fastcampus.javaallinone.project3.mycontact.domain.Person;
import com.fastcampus.javaallinone.project3.mycontact.repository.PersonRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


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

    @Test
    void getPerson(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(new Person("martin")));

        Person person =personService.getPerson(1L);

        assertThat(person.getName()).isEqualTo("martin");
    }

    @Test
    void getPersonIfNotFound(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.empty());

        Person person =personService.getPerson(1L);

        //personService 에서 null로 return 하는데 실제 null 이 return 됬는지 확인하는것
        assertThat(person).isNull();
    }

    @Test
    void put(){
        PersonDto dto=PersonDto.of("martin","programming","판교", LocalDate.now()
        ,"programmer","010-1111-2222");

        personService.put(dto);

        //Mock에서 save가 됬는지 확인하는법//times는 1번 실행됬는지 확인하느거
        //즉 위의 내용들(personService.put(dto)가 실행되서 personRepository.save()가 1번이상 실행되었는지 확인해서 알려주는것
        verify(personRepository,times(1)).save(any(Person.class));
    }
}
