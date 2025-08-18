package com.company.helper;

import com.company.entity.Developer;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DeveloperIDGenerator {


    public static String generateDeveloperId(Developer developer){

        String fname = developer.getFName();
        String lname = developer.getLName();
        int yob = developer.getYOB();

        //For getting first letter last name
        Character a = lname.charAt(0);

        //for getting last two digit of yob
        int b = yob % 100;

        //concat
        String developerId = a+fname+b;

        System.out.println(b);
        System.err.println("Developer Id  "+developerId);
        return developerId;
    }



}
