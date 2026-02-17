package com.project.foodapp.auth_users.services;

import com.project.foodapp.auth_users.dtos.UserDTO;
import com.project.foodapp.auth_users.entity.User;
import com.project.foodapp.auth_users.repository.UserRepository;
import com.project.foodapp.cloudinary.ImageStorageService;
import com.project.foodapp.email_notifications.dtos.NotificationDTO;
import com.project.foodapp.email_notifications.services.NotificationService;
import com.project.foodapp.exceptions.BadRequestException;
import com.project.foodapp.exceptions.NotFoundException;
import com.project.foodapp.response.Response;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final NotificationService notificationService;
    private final ImageStorageService cloudinaryStorageService;

    @Override
    public User getCurrentLoggedInUser() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("user not found"));
    }

    @Override
    public Response<List<UserDTO>> getAllUsers() {

        log.info("INSIDE getAllUsers()");

        List<User> userList = userRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        List<UserDTO> userDTOS = modelMapper.map(userList, new TypeToken<List<UserDTO>>() {
        }.getType());

        return Response.<List<UserDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("All users retreived successfully")
                .data(userDTOS)
                .build();
    }

    @Override
    public Response<UserDTO> getOwnAccountDetails() {

        log.info("INSIDE getOwnAccountDetails()");

        User user = getCurrentLoggedInUser();
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        return Response.<UserDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("success")
                .data(userDTO)
                .build();
    }

    @Override
    public Response<?> updateOwnAccount(UserDTO userDTO) {

        log.info("INSIDE updateOwnAccount()");

        User user = getCurrentLoggedInUser();
        String profileUrl = user.getProfileUrl();
        MultipartFile imageFile = userDTO.getImageFile();

        log.info("EXISTING Profile URL IS: " + profileUrl);

        // Image handling with Cloudinary
        if (imageFile != null && !imageFile.isEmpty()) {

            // Delete old image
            if (profileUrl != null && !profileUrl.isEmpty()) {
                String publicId = profileUrl.substring(profileUrl.lastIndexOf("/") + 1);
                publicId = publicId.contains(".")
                        ? publicId.substring(0, publicId.lastIndexOf("."))
                        : publicId;

                cloudinaryStorageService.deleteFile(publicId);
                log.info("Deleted old profile image from Cloudinary");
            }

            // Upload new image
            String newImageUrl = cloudinaryStorageService.uploadFile(imageFile);
            user.setProfileUrl(newImageUrl);
        }

        // Update other details (unchanged)
        if (userDTO.getName() != null) {
            user.setName(userDTO.getName());
        }

        if (userDTO.getPhoneNumber() != null) {
            user.setPhoneNumber(userDTO.getPhoneNumber());
        }

        if (userDTO.getAddress() != null) {
            user.setAddress(userDTO.getAddress());
        }

        if (userDTO.getEmail() != null && !userDTO.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(userDTO.getEmail())) {
                throw new BadRequestException("Email already exists");
            }
            user.setEmail(userDTO.getEmail());
        }

        if (userDTO.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        userRepository.save(user);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Account updated successfully")
                .build();
    }

    @Override
    public Response<?> deactivateOwnAccount() {

        log.info("INSIDE deactivateOwnAccount()");

        User user = getCurrentLoggedInUser();

        user.setActive(false);
        userRepository.save(user);

        NotificationDTO notificationDTO = NotificationDTO.builder()
                .recipient(user.getEmail())
                .subject("Account Deactivated")
                .body("Your account has been deactivated. If this was a mistake, please contact support.")
                .build();

        notificationService.sendEmail(notificationDTO);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Account deactivated successfully")
                .build();
    }
}
