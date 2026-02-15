package com.project.foodapp.auth_users.services;

import java.util.List;

import com.project.foodapp.auth_users.dtos.UserDTO;
import com.project.foodapp.auth_users.entity.User;
import com.project.foodapp.response.Response;

public interface UserService {

    User getCurrentLoggedInUser();

    Response<List<UserDTO>> getAllUsers();

    Response<UserDTO> getOwnAccountDetails();

    Response<?> updateOwnAccount(UserDTO userDTO);

    Response<?> deactivateOwnAccount();

}
