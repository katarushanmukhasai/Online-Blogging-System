package com.example.blogg.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.blogg.entity.Profile;
import com.example.blogg.entity.User;
import com.example.blogg.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public User registerUser(User user) {
        return userRepository.save(user);
    }
    
    @Override
    public User loginUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
        	System.out.println("Welcome "+user.getUsername());
            return user;
        }
        System.out.println("User not present");
        return null;
    }
    
    @Override
    public List<User> fetchAllUsers() {
        return userRepository.findAll();
    }

	@Override
	public List<User> fetchUserById(Long id) {
		Optional<User> userOptional = userRepository.findById(id);
		return userOptional.map(Collections::singletonList).orElse(Collections.emptyList());
	}

	@Override
	public void updateUser(Long id, User updatedUser) {
		User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPassword(updatedUser.getPassword());
            userRepository.save(existingUser); // Save the updated user
        }
		
	}

	@Override
	public void deleteUser(Long id) {
		System.out.println("Entered delete");
		if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            System.out.print("No such User found");
        }
		
	}

	@Override
	public void updateUserByProfile(Long id, Profile updatedProfile) {
		Optional<User> users = userRepository.findById(id);
		List<User> userList = users.map(Collections::singletonList).orElseGet(Collections::emptyList);
		User us = userList.get(0);
		us.setUsername(updatedProfile.getUsername());
		us.setEmail(updatedProfile.getEmailAddress());
		us.setPassword(updatedProfile.getPassword());
	}
}