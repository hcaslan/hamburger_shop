package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.request.AddressSaveRequestDto;
import org.example.dto.request.AddressUpdateRequestDto;
import org.example.entity.Address;
import org.example.entity.Profile;
import org.example.exceptions.ErrorType;
import org.example.exceptions.ProfileMicroServiceException;
import org.example.mapper.AddressMapper;
import org.example.repository.AddressRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final ProfileService profileService;
    private final AddressMapper addressMapper;

    public void saveAddress(AddressSaveRequestDto dto, String profileId) {
        Address address = addressRepository.save(addressMapper.toEntity(dto));
        Profile profile = profileService.findByProfileId(profileId);
        List<String> addressIds = profile.getAddressIds();
        addressIds.add(address.getId());
        profile.setAddressIds(addressIds);
        profileService.updateProfile(profile);
    }

    public void updateAddress(AddressUpdateRequestDto dto, String profileId) {
        Address address = addressRepository.findById(dto.getAddressId()).orElseThrow(()-> new ProfileMicroServiceException(ErrorType.ADDRESS_NOT_FOUND));
        if (!address.getProfileId().equals(profileId)) {
            throw new ProfileMicroServiceException(ErrorType.ACCESS_DENIED);
        }
        address.setAddressLine(dto.getAddressLine());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setAddressType(dto.getAddressType());
        addressRepository.save(address);
    }

    public void deleteAddress(String addressId, String profileId) {

        Profile profile = profileService.findByProfileId(profileId);
        List<String> addressIds = profile.getAddressIds();
        addressIds.removeIf(id -> id.equals(addressId));
        profile.setAddressIds(addressIds);
        profileService.updateProfile(profile);

        addressRepository.deleteById(addressId);
    }

    public List<Address> findAllById(String profileId) {
        return addressRepository.findAllByProfileId(profileId);
    }

    @RabbitListener(queues = "getAddress.Queue")
    @Transactional
    public Address findById(String addressId) {
        return addressRepository.findById(addressId).orElseThrow(()-> new ProfileMicroServiceException(ErrorType.ADDRESS_NOT_FOUND));
    }
}
