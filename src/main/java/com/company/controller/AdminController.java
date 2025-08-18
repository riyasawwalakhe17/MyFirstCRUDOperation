package com.company.controller;

import com.company.entity.Admin;
import com.company.entity.Developer;
import com.company.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
   private AdminService adminService;

    @PostMapping("/add")
    public ResponseEntity<String> addAdmin(@RequestBody Admin admin){
        adminService.saveAdmin(admin);
        return new ResponseEntity<>("Admin saved successfully", HttpStatus.OK);
    }

    @GetMapping("/getAllData")
    public ResponseEntity<List<Admin>> getAllData(){
        List<Admin> adminList = adminService.getAllAdmin();
        return new ResponseEntity<>(adminList,HttpStatus.OK);
    }

    @GetMapping("/getDataById/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable("id") Long id){
        Admin admin = adminService.getAdminById(id);
        return new ResponseEntity<>(admin,HttpStatus.OK);
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<String> deleteAdminById(@PathVariable("id") Long id){
        String msg= adminService.deleteAdmin(id);
        return new ResponseEntity<>("Admin deleted Successfully", HttpStatus.OK);

    }


}
