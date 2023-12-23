package com.example.blogg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.blogg.Service.BlogsService;
import com.example.blogg.Service.ProfileService;
import com.example.blogg.Service.UserService;
import com.example.blogg.entity.Profile;
import com.example.blogg.entity.User;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/profile")
public class ProfileController {

	@Autowired
    private ProfileService profileService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BlogsService blogService;


	@GetMapping("")
	public String viewProfile(Model model, HttpSession session) {
	    User loggedInUser = (User) session.getAttribute("loggedInUser"); // change it to and use pathvariable 
	    System.out.println(loggedInUser.getUsername() + " " + loggedInUser.getId());

	    List<Profile> profile = profileService.getProfileByUsername(loggedInUser.getUsername());

	    if (!profile.isEmpty()) {
	        Profile p = profile.get(0);
	        System.out.println(p.getFullName());
	        // Add the profile to the model
	        model.addAttribute("profile", p);
	    } else {
	    	Profile nullProfile = new Profile();
	    	nullProfile.setUsername(null);
	    	nullProfile.setFullName(null);
	    	nullProfile.setEmailAddress(null);
	    	nullProfile.setPhoneNumber(null);
	    	nullProfile.setAddress(null);
	    	nullProfile.setSocialMediaLinks(null);
	    	nullProfile.setAboutMe(null);
	    	nullProfile.setDateOfBirth(null);
	    	nullProfile.setGender(null);
	    	nullProfile.setLocation(null);
	    	nullProfile.setPassword(null);
	        model.addAttribute("profile", nullProfile);
	    }
	    model.addAttribute("username",loggedInUser.getUsername());
	    model.addAttribute("password", loggedInUser.getPassword());
	    return "Profile";
	}


    @GetMapping("/update")
    public String updateProfileForm(Model model, HttpSession session) { 
    	User loggedInUser = (User) session.getAttribute("loggedInUser");
        List<Profile> profile = profileService.getProfileByUsername(loggedInUser.getUsername());

        // Add the profile to the model
        model.addAttribute("profile", profile.get(0));

        return "update_profile";
    }

    @PostMapping("/update/{username}")
    public String updateProfile(@PathVariable String username, Model model, HttpSession session) {
        List<Profile> profiles = profileService.getProfileByUsername(username);
        Profile profile = profiles.isEmpty() ? null : profiles.get(0);
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        model.addAttribute("username", username);
        model.addAttribute("password", loggedInUser.getPassword());
        model.addAttribute("profile", profile);

        return "update_profile";
    }
    
    @PostMapping("/update")
    public String updateProfile(Profile updatedProfile, HttpSession session) {
        System.out.println(updatedProfile.getEmailAddress());
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        userService.updateUserByProfile(loggedInUser.getId(), updatedProfile);
        blogService.updateBlogsByProfile(loggedInUser.getUsername(), updatedProfile);
        profileService.saveProfile(updatedProfile);
        

        return "redirect:/user/login";
    }

    // You can add more methods as needed
}
