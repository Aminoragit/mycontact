package com.fastcampus.javaallinone.project3.mycontact.service;

import com.fastcampus.javaallinone.project3.mycontact.controller.dto.PersonDto;
import com.fastcampus.javaallinone.project3.mycontact.domain.Person;
import com.fastcampus.javaallinone.project3.mycontact.domain.dto.Birthday;
import com.fastcampus.javaallinone.project3.mycontact.exception.PersonNotFoundException;
import com.fastcampus.javaallinone.project3.mycontact.exception.RenameNotPermittedException;
import com.fastcampus.javaallinone.project3.mycontact.repository.PersonRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class personServiceTest {

    //    @InjectMocks -- 테스트 대상이 되는 목
    @InjectMocks
    private PersonService personService;
    @Mock
    private PersonRepository personRepository;


    //Mock 테스트는 전부를 보는게 아니라 부분부분을 파트로 나눠서 디테일하게 테스트할수 있게해준다.
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
//        PersonDto dto=PersonDto.of("martin","programming","판교", LocalDate.now()
//        ,"programmer","010-1111-2222");

        personService.put(mockPersonDto());

        //Mock에서 save가 됬는지 확인하는법//times는 1번 실행됬는지 확인하느거
        //즉 위의 내용들(personService.put(dto)가 실행되서 personRepository.save()가 1번이상 실행되었는지 확인해서 알려주는것
        verify(personRepository,times(1)).save(argThat(new IsPersonWillBeInserted()));
    }

    @Test
    void modifyPersonNotFound(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class,()->personService.modify(1L,mockPersonDto()));
    }

    @Test
    void modifyNameIsDifferent(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(new Person("tony")));

        assertThrows(RenameNotPermittedException.class,()->personService.modify(1L,mockPersonDto()));
    }

    @Test
    void modify(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(new Person("martin")));

        personService.modify(1L,mockPersonDto());
//        verify(personRepository,times(1)).save(any(Person.class));
        //verify하는데 명확하게 알려줘서 동일한 정보가 실행됬는지 확인하는것
        verify(personRepository,times(1)).save(argThat(new IsPersonWillBeUpdated()));
    }

    @Test
    void modifyByNameIfPersonNotFound(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class,()->personService.modify(1L,"martin"));

    }

    @Test
    void modifyByName(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(new Person("martin")));

        personService.modify(1L,"daniel");

        verify(personRepository,times(1)).save(argThat(new IsNameWillBeUpdated()));
    }

    @Test
    void deleteIfPersonNotFound(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class,()->personService.delete(1L));
    }

    
    //실제로 지워줬는지 확인하는것
    @Test
    void delete(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(new Person("martin")));

        personService.delete(1L);

        verify(personRepository,times(1)).save(argThat(new IsPersonWillBeDeleted()));
    }

    @Test
    void getAll(){
        when(personRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(Lists.newArrayList(new Person("martin"),new Person("dennis"),new Person("tony"))));

        Page<Person> result = personService.getAll(PageRequest.of(0,3));

        assertThat(result.getNumberOfElements()).isEqualTo(3);
        assertThat(result.getContent().get(0).getName()).isEqualTo("martin");
        assertThat(result.getContent().get(1).getName()).isEqualTo("dennis");
        assertThat(result.getContent().get(2).getName()).isEqualTo("tony");
    }









    private static class IsPersonWillBeInserted implements ArgumentMatcher<Person>{
        @Override
        public boolean matches(Person person){
            return equals(person.getName(),"martin")
                    && equals(person.getHobby(),"programming")
                    && equals(person.getAddress(),"판교")
                    && equals(person.getBirthday(),Birthday.of(LocalDate.now()))
                    && equals(person.getJob(),"programmer")
                    && equals(person.getPhoneNumber(),"010-1111-2222");
        }
        private boolean equals(Object actual, Object expected){
            return expected.equals(actual);
        }
    }

    private static class IsPersonWillBeUpdated implements ArgumentMatcher<Person>{
        @Override
        public boolean matches(Person person){
            return equals(person.getName(),"martin")
                    && equals(person.getHobby(),"programming")
                    && equals(person.getAddress(),"판교")
                    && equals(person.getBirthday(),Birthday.of(LocalDate.now()))
                    && equals(person.getJob(),"programmer")
                    && equals(person.getPhoneNumber(),"010-1111-2222");
        }
        private boolean equals(Object actual, Object expected){
            return expected.equals(actual);
        }
    }


    private PersonDto mockPersonDto(){
        return PersonDto.of("martin","programming","판교", LocalDate.now()
                ,"programmer","010-1111-2222");

    }

    private static class IsNameWillBeUpdated implements ArgumentMatcher<Person>{
        @Override
        public boolean matches(Person person){
            return person.getName().equals("daniel");
        }
    }


    private static class IsPersonWillBeDeleted implements ArgumentMatcher<Person>{
        @Override
        public boolean matches(Person person){
            return person.isDeleted();
        }
    }



}
