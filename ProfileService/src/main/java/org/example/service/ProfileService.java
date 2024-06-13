package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.Address;
import org.example.entity.Profile;
import org.example.exceptions.ErrorType;
import org.example.exceptions.ProfileMicroServiceException;
import org.example.model.StatusUpdateModel;
import org.example.repository.ProfileRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
    @RabbitListener(queues = "createProfile.Queue")
    @Transactional
    public void createProfile(String authId){
        Profile profile = Profile.builder()
                .authId(authId)
                .build();
        profileRepository.save(profile);
    }
    @RabbitListener(queues = "updateStatus.Queue")
    @Transactional
    public void updateStatus(StatusUpdateModel model){
        Profile profile = profileRepository.findByAuthId(model.getAuthId()).orElseThrow(()-> new ProfileMicroServiceException(ErrorType.USER_NOT_FOUND));
        profile.setStatus(model.getStatus());
        profileRepository.save(profile);
    }

    public Profile findByProfileId(String userId){
        return profileRepository.findById(userId).orElseThrow(()-> new ProfileMicroServiceException(ErrorType.USER_NOT_FOUND));
    }

    public void updateProfile(Profile profile) {
        profileRepository.save(profile);
    }

    public void addBalance(Double balance, String profileId) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(()-> new ProfileMicroServiceException(ErrorType.USER_NOT_FOUND));
        profile.setBalance(profile.getBalance()+balance);
        profileRepository.save(profile);
    }
    @RabbitListener(queues = "updateBalance.Queue")
    @Transactional
    public void updateBalance(String updateText){
        String[] parts = updateText.split("\\*"); // Split the text by the '*' delimiter
        String userId = parts[0]; // The first part is the userId
        String totalPrice = parts[1]; // The second part is the total price
        // Convert totalPrice to a numeric type if needed
        double totalPriceValue = Double.parseDouble(totalPrice);

        Profile profile = profileRepository.findById(userId).orElseThrow(()-> new ProfileMicroServiceException(ErrorType.USER_NOT_FOUND));
        profile.setBalance(profile.getBalance() - totalPriceValue);
        profileRepository.save(profile);
    }

    @RabbitListener(queues = "getBalance.Queue")
    @Transactional
    public Double getBalance(String profileId) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(()-> new ProfileMicroServiceException(ErrorType.USER_NOT_FOUND));
        return profile.getBalance();
    }

    @RabbitListener(queues = "getProfileId.Queue")
    @Transactional
    public String getProfileId(String authId) {
        Profile profile = profileRepository.findByAuthId(authId).orElseThrow(()-> new ProfileMicroServiceException(ErrorType.USER_NOT_FOUND));
        return profile.getId();
    }

    public List<Profile> findAll() {
        return profileRepository.findAll();
    }
}
