package com.company.serviceImpl;

import com.company.entity.Admin;
import com.company.entity.Developer;
import com.company.repository.AdminRepository;
import com.company.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
   private AdminRepository adminRepository;


    @Override
    public String saveAdmin(Admin admin) {

        Admin saveAdmin = adminRepository.save(admin);
        return "Admin saved";
    }

    @Override
    public List<Admin> getAllAdmin() {
        List<Admin> adminList = adminRepository.findAll();
        return adminList;
    }

    @Override
    public Admin getAdminById(Long id) {
        Admin admin = adminRepository.findById(id).orElseThrow(()->new NullPointerException("Admin with id is not found"+id));
        return admin;

    }

    @Override
    public String deleteAdmin(Long id) {
        adminRepository.deleteById(id);
        return "Admin deleted successfully";
    }
}
