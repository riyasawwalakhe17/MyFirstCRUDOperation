package com.company.serviceImpl;

import com.company.entity.Developer;
import com.company.repository.DeveloperRepository;
import com.company.service.DeveloperService;
import com.company.utility.DeveloperIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeveloperServiceImpl implements DeveloperService {

    @Autowired
    private DeveloperRepository developerRepository;

    @Override
    public String saveDeveloper(Developer developer) {
        //call from here
        String devId = DeveloperIDGenerator.generateDeveloperId(developer);
        developer.setDeveloperId(devId);
        developerRepository.save(developer);


        Developer saveDeveloper = developerRepository.save(developer);
        return "developer saved";
    }

    @Override
    public List<Developer> getAllDevelopers() {
        List<Developer> developerList = developerRepository.findAll();
        return developerList;
    }

    @Override
    public Developer getDeveloperById( int id) {
        Developer developer = developerRepository.findById(id)
                .orElseThrow(()->new NullPointerException("Developer with id is not found"+id));
        return developer;
    }

    @Override
    public String deleteDeveloper(int id) {
        developerRepository.deleteById(id);

        return "Developer deleted Successfully";
    }

    @Override
    public Developer upadateDeveloper(int id, Developer newData) {
        Developer developer = developerRepository.findById(id).orElseThrow(()->new NullPointerException("Updated Developer in db does not found with id "+id));
        System.err.println("Old Developer from db"+developer);
        System.err.println("Developer object with value to be updated"+newData);

        developer.setfName(newData.getfName());
        developer.setlName(newData.getlName());
        developer.setAge(newData.getAge());
        developer.setCity(newData.getCity());
        developer.setSalary(newData.getSalary());

        Developer updateDeveloper = developerRepository.save(developer);
        System.err.println("Developer with updated value"+updateDeveloper);
        return updateDeveloper ;
    }

    @Override
    public List<Developer> saveListOfDeveloper(List<Developer> developerList) {
        developerRepository.saveAll(developerList);
        return developerList;
    }


}
