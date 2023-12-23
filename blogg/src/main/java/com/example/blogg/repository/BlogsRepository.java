package com.example.blogg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.blogg.entity.Blogs;



@Repository
public interface BlogsRepository extends JpaRepository<Blogs, Long>{
	List<Blogs> findByUsername(String username);
	List<Blogs> findByAuthorid(Long authorid);
}
