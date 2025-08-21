package com.company.serviceImpl;

import com.company.entity.Developer;

import com.company.exception.DeveloperNotFoundException;
import com.company.helper.*;
import com.company.repository.AdminRepository;
import com.company.repository.DeveloperRepository;
import com.company.service.DeveloperService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeveloperServiceImpl implements DeveloperService {

    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired
    private AdminRepository adminRepository;



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
                .orElseThrow(()->new DeveloperNotFoundException("Developer with id is not found"+id));

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

        developer.setFName(newData.getFName());
        developer.setLName(newData.getLName());
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

    @Override
    public List<Developer> filterByCity(String city) {
        List<Developer> developerList = developerRepository.findAll();

      List<Developer> filteredList = developerList.stream().filter(developer -> developer.getCity().equalsIgnoreCase(city)).collect(Collectors.toList());
        return filteredList;
    }

    @Override
    public List<Developer> filterByGender(String gender) {
        List<Developer> developerList = developerRepository.findAll();

        List<Developer> filteredList = developerList.stream().filter(developer -> developer.getGender().equalsIgnoreCase(gender)).collect(Collectors.toList());
        return filteredList;
    }

    @Override
    public String save(MultipartFile file) {

        try {
            List<Developer> developers = helper.convertExcelToListOfProduct(file.getInputStream());
            List<String> allErrors = new ArrayList<>();
            for (Developer dev : developers) {
                //for validations
                List<String> errors = ExcelValidator.validateDeveloperData(dev);
                if (!errors.isEmpty()) {
                    allErrors.add("Row for " + dev.getFName() + " " + dev.getLName() + ": " + errors);
                    continue; // skip invalid records
                }


                //  Only generate ID if Excel value is empty or null
                if (dev.getDeveloperId() == null || dev.getDeveloperId().trim().isEmpty()) {
                    String devId = DeveloperIDGenerator.generateDeveloperId(dev);
                    dev.setDeveloperId(devId);
                }
            }

            //for validation
            if (!allErrors.isEmpty()) {
                throw new RuntimeException("Validation failed: " + allErrors);
            }

            //for adding data in db
            developerRepository.saveAll(developers);

            System.out.println(developers.size() + " Developers saved successfully!");

        } catch (IOException e) {
            throw new RuntimeException("Failed to store excel data: " + e.getMessage());
        }
        return "File Added";
    }

    @Override
    public List<Developer> getAllDeveloper() {
        return developerRepository.findAll();
    }

    // 🔹 Modified method
    @Override
    public ByteArrayInputStream databaseToExcel(Long adminId) {
        // Check if admin exists
        boolean isAdmin = adminRepository.existsById(adminId);

        if (!isAdmin) {
            throw new RuntimeException("Sorry, you don't have access to download");
        }

        try {
            List<Developer> developerList = developerRepository.findAll();
            return ExcelExportHelper.databasetoExcel(developerList);
        } catch (IOException e) {
            throw new RuntimeException("Failed to export data to Excel", e);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Developer> getDeveloperByAge(int age) {
        List<Developer> developerByAge =  developerRepository.findByAge(age);
        return developerByAge;

    }
}




