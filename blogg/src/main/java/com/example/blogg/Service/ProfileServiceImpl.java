package com.example.blogg.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.blogg.entity.Profile;
import com.example.blogg.repository.ProfileRepository;

import java.util.List;

@Service
public class ProfileServiceImpl implements ProfileService {

	@Autowired
    private ProfileRepository profileRepository;

    @Autowired
    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    @Override
    public Profile getProfileById(Long id) {
        return profileRepository.findById(id).orElse(null);
    }

    @Override
    public Profile saveProfile(Profile profile) {
    	
        List<Profile> existingProfiles = profileRepository.findByUsername(profile.getUsername());

        if (!existingProfiles.isEmpty()) {
            Profile existingProfile = existingProfiles.get(0);
            existingProfile.setFullName(profile.getFullName());
            existingProfile.setEmailAddress(profile.getEmailAddress());
            existingProfile.setPhoneNumber(profile.getPhoneNumber());
            existingProfile.setAddress(profile.getAddress());
            existingProfile.setSocialMediaLinks(profile.getSocialMediaLinks());
            existingProfile.setAboutMe(profile.getAboutMe());
            existingProfile.setDateOfBirth(profile.getDateOfBirth());
            existingProfile.setGender(profile.getGender());
            existingProfile.setLocation(profile.getLocation());
            existingProfile.setPassword(profile.getPassword());

            return profileRepository.save(existingProfile);
        } else {
            return profileRepository.save(profile);
        }
    }


	@Override
	public List<Profile> getProfileByUsername(String username) {
		return profileRepository.findByUsername(username);
	}






    // Implement other methods as needed
}
