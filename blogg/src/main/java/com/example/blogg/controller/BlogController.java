package com.example.blogg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;


import com.example.blogg.Service.BlogsService;
import com.example.blogg.entity.Blogs;
import com.example.blogg.entity.User;

@Controller
@RequestMapping("/blog")
public class BlogController {
    
    @Autowired
    private BlogsService blogService;

    @PostMapping("")
    public String saveBlog(@RequestParam String blogtype , @RequestParam String title , @RequestParam String content, @RequestParam String date,HttpSession session ) {
    	User loggedInUser = (User) session.getAttribute("loggedInUser");
    	String username = loggedInUser.getUsername();
    	Blogs blog = new Blogs(username,blogtype,title,content,date);
    	blogService.saveBlogs(blog);
    	return "redirect:/user/home";
    }

    @GetMapping("")
    public List<Blogs> getAllBlogs() {
        return blogService.fetchAllBlogs();
    }

    @GetMapping("/{authorid}")
    public Blogs getBlogById(@PathVariable("authorid") Long authorid) {
        return blogService.getBlogsById(authorid);
    }

    @GetMapping("/user/{username}")
    public List<Blogs> getBlogsByUsername(@PathVariable("username") String username) {
        return blogService.getBlogsByUsername(username);
    }

    @PostMapping("/{username}/{authorid}")
    public String updateBlog( @ModelAttribute Blogs blog,HttpSession session) {
    	User loggedInUser = (User) session.getAttribute("loggedInUser");
    	String username = loggedInUser.getUsername();
    	System.out.println(username+" "+blog.getUsername()+" "+blog.getAuthorid());
    	blogService.updateBlogsByUsername(username, blog);
        return "redirect:/user/my-blogs"; // Redirect to the user's blog page after updating.
    }
    
    @PostMapping("/admin/{username}/{authorid}")
    public String adminUpdateBlog( @ModelAttribute Blogs blog) {
    	System.out.println(blog.getUsername()+" "+blog.getUsername()+" "+blog.getAuthorid());
    	blogService.updateBlogsByUsername(blog.getUsername(), blog);
        return "redirect:/admin/home"; // Redirect to the admin's manage page after updating.
    }

    @PostMapping("/deleteblog/{authorid}")
    public String deleteBlog(@PathVariable("authorid") Long authorid) {
    	System.out.println(authorid);
    	blogService.deleteBlogsById(authorid);
        return "redirect:/user/my-blogs";
    }
    
    @PostMapping("/admin/deleteblog/{authorid}")
    public String adminDeleteBlog(@PathVariable("authorid") Long authorid) {
    	System.out.println(authorid);
    	blogService.deleteBlogsById(authorid);
        return "redirect:/admin/home";
    }

}
	