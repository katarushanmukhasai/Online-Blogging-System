package com.example.blogg.Service;

import java.util.List;

import com.example.blogg.entity.Blogs;
import com.example.blogg.entity.Profile;

public interface BlogsService {
    
    Blogs saveBlogs(Blogs blog);

    List<Blogs> fetchAllBlogs();

    Blogs getBlogsById(Long authorid);

    List<Blogs> getBlogsByUsername(String username);

    Blogs updateBlogsByUsername(String Username, Blogs blog);

    String deleteBlogsById(Long authorid);

	void updateBlogsByProfile(String username, Profile updatedProfile);
}
