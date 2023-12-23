package com.example.blogg.Service;

import java.util.List;

import com.example.blogg.entity.Profile;

public interface ProfileService {
    List<Profile> getAllProfiles();

    Profile getProfileById(Long id);
    List<Profile> getProfileByUsername(String username);

    Profile saveProfile(Profile profile);
    
}

