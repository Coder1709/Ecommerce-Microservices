package com.app.ecom_application.service;

import com.app.ecom_application.dto.AddressDto;
import com.app.ecom_application.dto.UserRequest;
import com.app.ecom_application.dto.UserResponse;
import com.app.ecom_application.model.Address;
import com.app.ecom_application.model.User;

import com.app.ecom_application.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public List<UserResponse> fetchAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public UserResponse addUser(UserRequest userRequest) {
        User user = mapToUser(userRequest);
        User savedUser = userRepository.save(user);
        return mapToUserResponse(savedUser);
    }

    public Optional<UserResponse> fetchUser(Long id) {
        return userRepository.findById(id)
                .map(this::mapToUserResponse);
    }

    public Optional<UserResponse> updateUser(Long id, UserRequest userRequest) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    updateUserFields(existingUser, userRequest);
                    User savedUser = userRepository.save(existingUser);
                    return mapToUserResponse(savedUser);
                });
    }

    // Private mapper methods
    private UserResponse mapToUserResponse(User user) {
        if (user == null) return null;

        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());
        
        if (user.getAddress() != null) {
            response.setAddress(mapToAddressDto(user.getAddress()));
        }
        
        return response;
    }

    private User mapToUser(UserRequest userRequest) {
        if (userRequest == null) return null;

        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());

        
        if (userRequest.getAddress() != null) {
            user.setAddress(mapToAddress(userRequest.getAddress()));
        }
        
        return user;
    }

    private AddressDto mapToAddressDto(Address address) {
        if (address == null) return null;

        AddressDto addressDto = new AddressDto();
        addressDto.setStreet(address.getStreet());
        addressDto.setCity(address.getCity());
        addressDto.setState(address.getState());
        addressDto.setCountry(address.getCountry());
        addressDto.setZipcode(address.getZipcode());
        
        return addressDto;
    }

    private Address mapToAddress(AddressDto addressDto) {
        if (addressDto == null) return null;

        Address address = new Address();
        address.setStreet(addressDto.getStreet());
        address.setCity(addressDto.getCity());
        address.setState(addressDto.getState());
        address.setCountry(addressDto.getCountry());
        address.setZipcode(addressDto.getZipcode());
        
        return address;
    }

    private void updateUserFields(User existingUser, UserRequest updateRequest) {
        if (updateRequest.getFirstName() != null) {
            existingUser.setFirstName(updateRequest.getFirstName());
        }
        if (updateRequest.getLastName() != null) {
            existingUser.setLastName(updateRequest.getLastName());
        }
        if (updateRequest.getEmail() != null) {
            existingUser.setEmail(updateRequest.getEmail());
        }
        if (updateRequest.getPhone() != null) {
            existingUser.setPhone(updateRequest.getPhone());
        }
        if (updateRequest.getAddress() != null) {
            if (existingUser.getAddress() == null) {
                existingUser.setAddress(new Address());
            }
            updateAddressFields(existingUser.getAddress(), updateRequest.getAddress());
        }
    }

    private void updateAddressFields(Address existingAddress, AddressDto updateAddressDto) {
        if (updateAddressDto.getStreet() != null) {
            existingAddress.setStreet(updateAddressDto.getStreet());
        }
        if (updateAddressDto.getCity() != null) {
            existingAddress.setCity(updateAddressDto.getCity());
        }
        if (updateAddressDto.getState() != null) {
            existingAddress.setState(updateAddressDto.getState());
        }
        if (updateAddressDto.getCountry() != null) {
            existingAddress.setCountry(updateAddressDto.getCountry());
        }
        if (updateAddressDto.getZipcode() != null) {
            existingAddress.setZipcode(updateAddressDto.getZipcode());
        }
    }
}