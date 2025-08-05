package com.company.utility;

import com.company.entity.Developer;

public class DeveloperIDGenerator {


    public static String generateDeveloperId(Developer developer){

        String fname = developer.getfName();
        String lname = developer.getlName();
        int yob = developer.getyOB();

        Character a = lname.charAt(0);

        int b = yob % 100;

        String developerId = a+fname+b;
        System.out.println(b);
        System.err.println("Developer Id  "+developerId);
        return developerId;
    }
}
