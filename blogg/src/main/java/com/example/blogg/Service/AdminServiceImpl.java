package com.example.blogg.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.blogg.entity.Admin;
import com.example.blogg.repository.AdminRepository;


@Service
public class AdminServiceImpl implements AdminService{

	@Autowired
	private AdminRepository adminRepository;
	
	@Override
	public Admin registerAdmin(Admin admin) {
		return adminRepository.save(admin);
	}

	@Override
	public Admin loginAdmin(String username, String password) {
		Admin admin = adminRepository.findByUsername(username);
		if(admin != null && admin.getPassword().equals(password)) {
			System.out.println("Welcome "+admin.getUsername());
			return admin;
		}
		System.out.println("User not present");
		return null;
	}

	@Override
	public List<Admin> fetchAllAdmin() {
		return adminRepository.findAll();
	}

	@Override
	public void addAdmin(Admin admin) {
		adminRepository.save(admin);
	}

	@Override
	public List<Admin> fetchAdminById(Long id) {
		Optional<Admin> userOptional = adminRepository.findById(id);
		return userOptional.map(Collections::singletonList).orElse(Collections.emptyList());
	}

	@Override
	public void updateAdmin(Long id, Admin updatedAdmin) {
		Admin existingAdmin = adminRepository.findById(id).orElse(null);
        if (existingAdmin != null) {
            existingAdmin.setUsername(updatedAdmin.getUsername());
            existingAdmin.setEmail(updatedAdmin.getEmail());
            existingAdmin.setPassword(updatedAdmin.getPassword());
            adminRepository.save(existingAdmin); // Save the updated user
        }
		
	}

	@Override
	public void deleteAdmin(Long id) {
		System.out.println("Entered delete");
		if (adminRepository.existsById(id)) {
            adminRepository.deleteById(id);
        } else {
            System.out.print("No such User found");
        }
	}

}
