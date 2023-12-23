package com.example.blogg.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.blogg.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long>{
	Admin findByUsername(String username);
}
