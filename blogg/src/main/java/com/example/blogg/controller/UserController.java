package com.example.blogg.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

import com.example.blogg.Service.UserService;
import com.example.blogg.entity.Blogs;
import com.example.blogg.entity.User;

import jakarta.servlet.http.HttpSession;

import com.example.blogg.Service.BlogsService;

@Controller
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private BlogsService blogsService;
    
    
    
    
    
    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String email, @RequestParam String password) {
        User user = new User(username, email, password);
        userService.registerUser(user);
        return "redirect:/user/login";
    }
    
    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        User user = userService.loginUser(username, password);
        if (user != null) {
            session.setAttribute("loggedInUser", user);
            return "redirect:/user/home";
        } else {
            model.addAttribute("error", "Invalid username or password. Please try again.");
            return "redirect:/user/login";
        }
    }

    
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    
    @GetMapping("/aboutus")
    public String aboutus() {
    	return "aboutus";
    }
    
    @GetMapping("/home")
    public String userHomePage(Model model, HttpSession session) {
        List<Blogs> blogs = blogsService.fetchAllBlogs();
        List<User> users = userService.fetchAllUsers();
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        Long id=null;
        String message=null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection("jdbc:mysql://localhost/announce", "root", "Sh@161203");
            st = con.createStatement();
            String sql = "SELECT * FROM announcement";
            rs = st.executeQuery(sql);
            while (rs.next()) {
                id = rs.getLong("id");
                message = rs.getString("message");
            }
        }catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("id"+id+" message: "+message);
        model.addAttribute("blogs", blogs);
        model.addAttribute("users", users);
        model.addAttribute("loggedInUser", loggedInUser);
        model.addAttribute("announce",message);
        return "user_home";
    }
    
    
    @GetMapping("/other-home/{username}")
    public String otherUserHomePage(Model model, HttpSession session, @PathVariable("username") String username) {
        List<Blogs> blogs = blogsService.getBlogsByUsername(username);
        List<User> users = userService.fetchAllUsers();
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        
        users.removeIf(user -> user.getUsername().equals(username));
        users.removeIf(user -> user.getUsername().equals(loggedInUser.getUsername()));

        model.addAttribute("blogs", blogs);
        model.addAttribute("users", users);
        model.addAttribute("loggedInUsername", loggedInUser.getUsername());
        model.addAttribute("user", username);
        return "other_home";
    }

    
    
    @GetMapping("/my-blogs")
    public String userMyBlogsPage(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        List<User> users = userService.fetchAllUsers();
        if (loggedInUser != null && loggedInUser.getUsername() != null) {
            String loggedInUsername = loggedInUser.getUsername();
            model.addAttribute("blogs", blogsService.getBlogsByUsername(loggedInUsername));
            model.addAttribute("users", users);
            model.addAttribute("loggedInUser", loggedInUser);
            return "my_blogs";
        } else {
            return "redirect:login"; // Replace with your desired handling logic
        }
    }

    @GetMapping("/create")
    public String createMyBlog() {
        return "create_blog";
    }
    
    @GetMapping("/updateBlog/{username}/{authorid}")
    public String showUpdateBlogForm(@PathVariable("username") String username, @PathVariable("authorid") String authorid, Model model, HttpSession session) {
    	User loggedInUser = (User) session.getAttribute("loggedInUser");
    	String Username = loggedInUser.getUsername();
    	model.addAttribute("username", Username);
        return "update_blog"; 
    }

    @PostMapping("/deleteBlog/{username}/{authorid}")
    public String showDeleteBlogForm(@PathVariable("authorid") Long authorid, @PathVariable("username") String username, Model model) {
    	model.addAttribute("authorid", authorid);
    	model.addAttribute("username", username);
        return "delete_blog";
    }
    
    @PostMapping("/{id}")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute("user") User updatedUser) {
        System.out.println(id);
    	userService.updateUser(id, updatedUser);
        return "redirect:/admin/users";
    }
    
    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam("id") Long id, @RequestParam("username") String username) {
        System.out.println(id);
    	userService.deleteUser(id);
    	return "redirect:/admin/users"; 
    }
    
    
    @GetMapping("/profile")
    public String userProfile(Model model, HttpSession session) {
    	
        return "redirect:/profile";
    }
   
}