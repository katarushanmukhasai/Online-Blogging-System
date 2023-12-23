package com.example.blogg.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.blogg.Service.AdminService;
import com.example.blogg.Service.BlogsService;
import com.example.blogg.Service.UserService;
import com.example.blogg.entity.Admin;
import com.example.blogg.entity.Blogs;
import com.example.blogg.entity.User;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private BlogsService blogsService;
	
	@Autowired
	private UserService userService;
	
	
	
	@GetMapping("/login")
    public String loginPage() {
        return "admin_login";
    }
	
	@GetMapping("/announce")
    public String createAnnounce() {
        return "redirect:http://localhost:8082/announcements/createannounce";
    }
	
	@PostMapping("/login")
    public String loginAdmin(@RequestParam String username, @RequestParam String password, HttpSession session) {
        Admin admin = adminService.loginAdmin(username, password);
        if (admin != null) {
            session.setAttribute("loggedInUser", admin);
            return "redirect:/admin/home";
        }
        return "redirect:/admin/login";
    }
	
    @GetMapping("/home")
    public String userHomePage(Model model, HttpSession session) {
        List<Blogs> blogs = blogsService.fetchAllBlogs();
        List<User> users = userService.fetchAllUsers();
        Admin loggedInUser = (Admin) session.getAttribute("loggedInUser");
        model.addAttribute("blogs", blogs);
        model.addAttribute("users", users);
        model.addAttribute("loggedInUser", loggedInUser);
        return "admin_home";
    }
    
    @GetMapping("/blogs")
    public String blogsPage(Model model, HttpSession session) {
        List<Blogs> blogs = blogsService.fetchAllBlogs();
        Admin loggedInUser = (Admin) session.getAttribute("loggedInUser");
        model.addAttribute("blogs", blogs);
        model.addAttribute("loggedInUser", loggedInUser);
        return "blogs";
    }
    
    @GetMapping("/users")
    public String usersPage(Model model, HttpSession session) {
        List<User> users = userService.fetchAllUsers();
        Admin loggedInUser = (Admin) session.getAttribute("loggedInUser");
        model.addAttribute("users", users);
        model.addAttribute("loggedInUser", loggedInUser);
        return "users";
    }
    
    @GetMapping("/updateBlog/{authorid}")
    public String showUpdateBlogForm( @PathVariable("authorid") Long authorid, Model model, HttpSession session) {
    	Blogs blog = blogsService.getBlogsById(authorid);
    	model.addAttribute("blog", blog);
        return "user_update_blog"; 
    }
    
    @PostMapping("/deleteBlog/{username}/{authorid}")
    public String showDeleteBlogForm(@PathVariable("authorid") Long authorid, @PathVariable("username") String username, Model model) {
    	model.addAttribute("authorid", authorid);
    	model.addAttribute("username", username);
        return "user_delete_blog";
    }
    
    @GetMapping("/user_update/{id}")
    public String showUpdateUserForm( @PathVariable("id") Long id, Model model, HttpSession session) {
    	List<User> user = userService.fetchUserById(id);
//    	for (User u : user) {
//            System.out.println("User ID: " + u.getId() + ", Username: " + u.getUsername());
//        }
    	model.addAttribute("user", user.get(0));
        return "user_update"; 
    }
    
    @PostMapping("/user_delete/{username}/{id}")
    public String showDeleteUserForm(@PathVariable("id") Long id, @PathVariable("username") String username, Model model) {
    	model.addAttribute("id", id);
    	model.addAttribute("username", username);
        return "user_delete";
    }
    
    
    @GetMapping("/admins")
    public String listAdmins(Model model, HttpSession session) {
        List<Admin> admins = adminService.fetchAllAdmin();
        Admin loggedInUser = (Admin) session.getAttribute("loggedInUser");
        System.out.println(loggedInUser.getUsername());
        model.addAttribute("admins", admins);
        model.addAttribute("adms", loggedInUser.getUsername());
        model.addAttribute("admin", "admin");

        return "manage_admins";
    }
    
    
    @GetMapping("/create")
    public String showAdminCreationForm(Model model) {
        return "create_admin";
    }
    
    @PostMapping("/create")
    public String createAdmin(@ModelAttribute("admin") Admin admin) {
    	adminService.addAdmin(admin);
    	return "redirect:/admin/admins";
    }

    
    @GetMapping("/updateAdmin/{id}")
    public String showAdminUpdateForm(@PathVariable("id") Long id, Model model) {
    	List<Admin> admin = adminService.fetchAdminById(id);
    	model.addAttribute("admin",admin.get(0));
    	return "update_admin";
    }
    
    @PostMapping("/{id}")
    public String updateAdmin(@PathVariable("id") Long id, @ModelAttribute("admin") Admin updatedAdmin) {
        System.out.println(id);
    	adminService.updateAdmin(id, updatedAdmin);
        return "redirect:/admin/admins";
    }
    
    @PostMapping("/deleteAdmin/{username}/{id}")
    public String showDeleteAdminForm(@PathVariable("id") Long id, @PathVariable("username") String username, Model model) {
    	model.addAttribute("id", id);
    	model.addAttribute("username", username);
        return "delete_admin";
    }
    
    
    @PostMapping("/deleteAdmin")
    public String deleteAdmin(@RequestParam("id") Long id, @RequestParam("username") String username) {
        System.out.println(id);
    	adminService.deleteAdmin(id);
    	return "redirect:/admin/admins"; 
    }
    
    
}
