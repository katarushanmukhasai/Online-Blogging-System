package com.example.blogg.Service;

import java.util.List;

import com.example.blogg.entity.Admin;

public interface AdminService {
	Admin registerAdmin(Admin admin);
	Admin loginAdmin(String username, String password);
	List<Admin> fetchAllAdmin();
	void addAdmin(Admin admin);
	List<Admin> fetchAdminById(Long id);
	void updateAdmin(Long id, Admin updatedAdmin);
	void deleteAdmin(Long id);
}
