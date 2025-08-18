package com.company.service;

import com.company.entity.Developer;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;


public interface DeveloperService {

    String saveDeveloper(Developer developer);

    List<Developer> getAllDevelopers();

    Developer getDeveloperById(int id);

    String deleteDeveloper(int id);

    Developer upadateDeveloper(int id, Developer newData);

    List<Developer> saveListOfDeveloper(List<Developer> developerList);

    List<Developer> filterByCity(String city);

    List<Developer> filterByGender(String gender);

    String save(MultipartFile file);

    List<Developer> getAllDeveloper();

    ByteArrayInputStream databaseToExcel(Long adminId);
}
