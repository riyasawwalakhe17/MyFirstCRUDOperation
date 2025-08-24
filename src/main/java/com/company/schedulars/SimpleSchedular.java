package com.company.schedulars;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


public class SimpleSchedular {

    @Scheduled(fixedDelay = 5000)
    public void printSomething(){
        System.err.println("today is thursday");
    }

    @Scheduled(cron = "0 * * * * ?\n")
    public void printSomething2(){
        System.err.println("today is good day");
    }

}
