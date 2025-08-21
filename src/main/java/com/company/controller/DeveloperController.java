package com.company.controller;

import com.company.entity.Developer;

import com.company.helper.helper;
import com.company.service.DeveloperService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/developer")
public class DeveloperController {

    @Autowired
    private DeveloperService developerService;

    @PostMapping("/add")
    public ResponseEntity<String> addDeveloper(@RequestBody Developer developer){
        System.err.println(developer);
        developerService.saveDeveloper(developer);
        return new ResponseEntity<>("Hiii.... " +developer.getFName()+" You have successfully login And your developer Id is: "+developer.getDeveloperId(), HttpStatus.CREATED);
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

    @GetMapping("/filter")
    public ResponseEntity<List<Developer>> filterDeveloperByCity(@RequestParam(required = false) String city, @RequestParam(required = false) String gender){
    List<Developer> sortedList = new ArrayList<>();

    if(city!= null){
        sortedList = developerService.filterByCity(city);
    }else {
        sortedList =developerService.filterByGender(gender);
    }

    return new ResponseEntity<>(sortedList,HttpStatus.OK);
    }

    //Api for uploading excel
    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        if (!helper.checkExcelFormat(file)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Please upload a valid Excel file (.xlsx)"));
        }

        try {
            developerService.save(file);
            return ResponseEntity.ok(Map.of("message", " File uploaded and data saved successfully!"));
        } catch (RuntimeException e) {
            // Send validation errors back to Postman
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("validationErrors", e.getMessage()));
        }
    }

    //api for getting data in db
    @GetMapping("/download-excel/{adminId}")
    public void downloadExcel(@PathVariable Long adminId, HttpServletResponse response) throws IOException {
        try {
            ByteArrayInputStream excelStream = developerService.databaseToExcel(adminId);

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=developersExcel.xlsx");

            StreamUtils.copy(excelStream, response.getOutputStream());
            response.flushBuffer();
        } catch (RuntimeException e) {
            // If not admin → show error message instead of file
            response.setContentType("text/plain");
            response.getWriter().write(e.getMessage());
            response.flushBuffer();
        }

    }
    @GetMapping("/byAge/{age}")
    public ResponseEntity<List<Developer>> getDeveloperbyAge(@PathVariable("age")int age){
        List<Developer> developerList=developerService.getDeveloperByAge(age);
        return new ResponseEntity<>(developerList, HttpStatus.OK);
    }
}