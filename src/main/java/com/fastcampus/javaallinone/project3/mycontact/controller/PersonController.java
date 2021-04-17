package com.fastcampus.javaallinone.project3.mycontact.controller;


import com.fastcampus.javaallinone.project3.mycontact.domain.Person;
import com.fastcampus.javaallinone.project3.mycontact.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/api/person")
public class PersonController {
    @Autowired
    private PersonService personService;

//    @RequestMapping(method = RequestMethod.GET)

    @GetMapping
    @RequestMapping(value="/{id}")
    public Person getPerson(@PathVariable Long id){
        return personService.getPerson(id);
    }

}
