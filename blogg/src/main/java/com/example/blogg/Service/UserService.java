package com.example.blogg.Service;


import java.util.List;

import com.example.blogg.entity.Profile;
import com.example.blogg.entity.User;

public interface UserService {
    User registerUser(User user);
    User loginUser(String username, String password);
	List<User> fetchAllUsers();
	List<User> fetchUserById(Long id);
	void updateUser(Long id, User updatedUser);
	void deleteUser(Long id);
	void updateUserByProfile(Long id, Profile updatedProfile);
}