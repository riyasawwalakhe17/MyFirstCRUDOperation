package com.company.service;

import com.company.entity.Developer;
import org.springframework.stereotype.Service;

import java.util.List;


public interface DeveloperService {

    String saveDeveloper(Developer developer);

    List<Developer> getAllDevelopers();

    Developer getDeveloperById(int id);

    String deleteDeveloper(int id);

    Developer upadateDeveloper(int id, Developer newData);

    List<Developer> saveListOfDeveloper(List<Developer> developerList);
}
