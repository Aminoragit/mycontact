package com.fastcampus.javaallinone.project3.mycontact.domain;

import com.fastcampus.javaallinone.project3.mycontact.controller.dto.PersonDto;
import com.fastcampus.javaallinone.project3.mycontact.domain.dto.Birthday;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
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
@Where(clause = "deleted = false")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotEmpty
    @Column(nullable = false)
    private String name;


    private String hobby;

    @NonNull
    @NotEmpty
    @Column(nullable = false)
    private String bloodType;

    private String address;

    @Valid
    @Embedded
    private Birthday birthday;

    private String job;

    @ToString.Exclude
    private String phoneNumber;

    @ColumnDefault("0")
    private boolean deleted;




    //cascade 폭포수 Person엔티티에서 Block에 대한 영속성을 함께 관리
    //CascadeType.ALL={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE}
    //Block을 null로 해제했는데도 block리스트에 남아있는 엔티티를 자동으로 제거해주기--orphanRemoval = true
    //fetch-> EAGER타입
    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    @ToString.Exclude //불필요한 쿼리 호출을 자동제거
    private Block block;


    public void set(PersonDto personDto){
        if(!StringUtils.isEmpty(personDto.getHobby())){
            this.setHobby(personDto.getHobby()); }

        if(!StringUtils.isEmpty(personDto.getBloodType())){
            this.setBloodType(personDto.getBloodType()); }
        if(!StringUtils.isEmpty(personDto.getAddress())){
            this.setAddress(personDto.getAddress()); }

        if(!StringUtils.isEmpty(personDto.getJob())){
            this.setJob(personDto.getJob()); }

        if(!StringUtils.isEmpty(personDto.getPhoneNumber())){
            this.setPhoneNumber(personDto.getPhoneNumber()); }

    }

    public Integer getAge(){
        if(this.birthday!= null){
            return LocalDate.now().getYear() - this.birthday.getYearOfBirthday()+1;
        }
        else {
            return null;
        }

    }

    public boolean isBirthdayToday(){
        return LocalDate.now().equals(LocalDate.of(this.birthday.getYearOfBirthday(),this.birthday.getMonthOfBirthday(),this.birthday.getDayOfBirthday()));

    }
}
