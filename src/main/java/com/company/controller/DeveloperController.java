package com.company.controller;

import com.company.entity.Developer;
import com.company.service.DeveloperService;
import com.company.utility.DeveloperIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/developer")
public class DeveloperController {

    @Autowired
    private DeveloperService developerService;

    @PostMapping("/add")
    public ResponseEntity<String> addDeveloper(@RequestBody Developer developer){



        System.err.println(developer);
        developerService.saveDeveloper(developer);
        return new ResponseEntity<>("Developer saved", HttpStatus.CREATED);
    }

    @GetMapping("/getAllData")
    public ResponseEntity<List<Developer>> getAllData(){
        List<Developer> developerList = developerService.getAllDevelopers();
        return new ResponseEntity<>(developerList,HttpStatus.OK);
    }

    @GetMapping("/getDataById/{id}")
    public ResponseEntity<Developer> getDeveloperById(@PathVariable("id") int id){
        Developer developer = developerService.getDeveloperById(id);
        return new ResponseEntity<>(developer,HttpStatus.OK);
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<String> deleteDeveloperById(@PathVariable("id") int id){
        String msg= developerService.deleteDeveloper(id);
        return new ResponseEntity<>("Developer deleted Successfully", HttpStatus.OK);

    }

    @PutMapping("/updateDataById/{id}")
    public ResponseEntity<Developer> updateDeveloper(@PathVariable("id") int id ,@RequestBody Developer developer){
        Developer updateDeveloper = developerService.upadateDeveloper(id, developer);
        return new ResponseEntity<>(updateDeveloper,HttpStatus.OK);
    }


    @PostMapping("/addListOfData")
    public ResponseEntity<List<Developer>> saveListOfDeveloper(@RequestBody List<Developer> developerList){
        developerService.saveListOfDeveloper(developerList);
        return new ResponseEntity<>(developerList,HttpStatus.OK);
    }
}
