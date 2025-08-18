package com.company.helper;

import com.company.entity.Developer;

import java.util.ArrayList;
import java.util.List;

public class ExcelValidator {

    //validations for developer data
    public static List<String> validateDeveloperData(Developer dev) {
        List<String> errors = new ArrayList<>();

        if (dev.getFName() == null || dev.getFName().trim().isEmpty()) {
            errors.add("First name is required.");
        }
        if (dev.getLName() == null || dev.getLName().trim().isEmpty()) {
            errors.add("Last name is required.");
        }
        if (dev.getAge() <= 0 || dev.getAge() > 120) {
            errors.add("Age must be between 1 and 120.");
        }
        if (dev.getGender() == null ||
                (!dev.getGender().equalsIgnoreCase("Male") && !dev.getGender().equalsIgnoreCase("Female"))) {
            errors.add("Gender must be Male or Female.");
        }
        if (dev.getCity() == null || dev.getCity().trim().isEmpty()) {
            errors.add("City is required.");
        }
        if (dev.getSalary() <= 0) {
            errors.add("Salary must be greater than 0.");
        }
        if (dev.getYOB() < 1900 || dev.getYOB() > java.time.Year.now().getValue()) {
            errors.add("Year of birth must be valid.");
        }

        return errors;
    }
}
