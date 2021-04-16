package com.fastcampus.javaallinone.project3.mycontact.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@ToString(exclude = "phoneNumber") //요소 추가시 자동으로 print 해주는 것에 추가해줌
//exclude는 print되는 요소들중 숨기고 싶은 요소가 있을시 추가해주면 해당 요소는 print 되지 않는다.
public class Person {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private int age;
    private String hobby;
    private String bloodType;
    private String address;
    private LocalDate birthday;
    private String job;
    private String phoneNumber;


}
