package com.example.blogg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.blogg.entity.Blogs;
import com.example.blogg.entity.Comments;

public interface CommentRepository extends JpaRepository<Comments, Long> {
	
}
