package com.company.service;

import com.company.entity.Admin;
import com.company.entity.Developer;

import java.util.List;

public interface AdminService {

    String saveAdmin(Admin admin);

    List<Admin> getAllAdmin();

    Admin getAdminById(Long id);

    String deleteAdmin(Long id);


}
