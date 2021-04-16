package com.fastcampus.javaallinone.project3.mycontact.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDate;

//@RequiredArgsConstructor //Notnull 이여야하는 요소들 @Nonnull
//@ToString //요소 추가시 자동으로 print 해주는 것에 추가해줌
//exclude는 print되는 요소들중 숨기고 싶은 요소가 있을시 추가해주면 해당 요소는 print 되지 않는다.
//@EqualsAndHashCode


@Entity
@Data //getter setter Tostring,Requied..., Eqals...를 모두 포함함
@NoArgsConstructor //아무요소가 없는 생성자
@RequiredArgsConstructor
@AllArgsConstructor
//모든 요소가 포함된 생성자
public class Person {
    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private int age;

    private String hobby;

    @NonNull
    private String bloodType;

    private String address;
    private LocalDate birthday;
    private String job;

    @ToString.Exclude
    private String phoneNumber;

    @OneToOne
    private Block block;




//    public boolean equals(Object object) {
//        if (object == null) {
//            return false;
//        }
//        Person person = (Person) object;
//
//        if (!person.getName().equals(this.getName())) {
//            return false;
//        }
//
//        if (person.getAge() != this.getAge()) {
//            return false;
//        }
//        return true;
//    }
//
//    public int hashCode(){
//        return (name+age).hashCode();
//    }
}
