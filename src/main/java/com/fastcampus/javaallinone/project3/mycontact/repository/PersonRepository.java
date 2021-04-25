package com.fastcampus.javaallinone.project3.mycontact.repository;


import com.fastcampus.javaallinone.project3.mycontact.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByName(String name);

    @Query(value = "select person from Person person where person.birthday.monthOfBirthday = :monthOfBirthday ")
    List<Person> findByMonthOfBirthday(@Param("monthOfBirthday") int monthOfBirthday);

    @Query(value = "select * from Person person where person.deleted = true", nativeQuery = true)
    List<Person> findPeopleDeleted();


    //1차 시도 : BETWEEN으로 startDate와 endDate사이가 생일인 인원을 찾아보자 -> 년도에 걸려서 안됨
    //2차 시도 : formatting으로 년도를 제외한 월과 일 값 사이가 생일인 인원을 찾아보자 -> LocalDate가 아닌 일반 String이라 안됨
    //3차 시도 : birthday의 month와 day를 CONCAT하여 String이 동일한지 확인하지 -> nativaQuery=true시 CONCAT는 되지만 알수없는 문법오류 발생
    //4차 시도 : 그냥 일반적인 sql문을 사용해서 월과 일이 동일한 값(Integer)인지 확인하자 -> 더럽긴하지만 동작은 한다. personRepositoryTest / findByBirthday
    @Query(value = "select person from Person person where (person.birthday.monthOfBirthday = :startDatetimeMonth AND person.birthday.dayOfBirthday =:startDatetimeDay) OR " +
            "(person.birthday.monthOfBirthday = :endDateTimeMonth AND person.birthday.dayOfBirthday =:endDateTimeDay)")
    List<Person> findByBirthday(@Param("startDatetimeMonth") Integer startDatetimeMonth,
                                @Param("startDatetimeDay") Integer startDatetimeDay,
                                @Param("endDateTimeMonth") Integer endDateTimeMonth,
                                @Param("endDateTimeDay") Integer endDateTimeDay);
}
