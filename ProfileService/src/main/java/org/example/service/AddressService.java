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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final ProfileService profileService;
    private final AddressMapper addressMapper;

    public void saveAddress(AddressSaveRequestDto dto) {
        Address address = addressRepository.save(addressMapper.toEntity(dto));
        Profile profile = profileService.findByProfileId(dto.getProfileId());
        List<Address> addresses = profile.getAddresses();
        addresses.add(address);
        profile.setAddresses(addresses);
        profileService.updateProfile(profile);
    }

    public void updateAddress(AddressUpdateRequestDto dto) {
        Address address = addressRepository.findById(dto.getAddressId()).orElseThrow(()-> new ProfileMicroServiceException(ErrorType.ADDRESS_NOT_FOUND));
        address.setAddressLine(dto.getAddressLine());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setAddressType(dto.getAddressType());
        addressRepository.save(address);
    }

    public void deleteAddress(String addressId, String profileId) {

        Profile profile = profileService.findByProfileId(profileId);
        List<Address> addresses = profile.getAddresses();
        addresses.removeIf(address -> address.getId().equals(addressId));
        profile.setAddresses(addresses);
        profileService.updateProfile(profile);

        addressRepository.deleteById(addressId);
    }

    public List<Address> findAll(String profileId) {
        return addressRepository.findAllByProfileId(profileId);
    }
}
