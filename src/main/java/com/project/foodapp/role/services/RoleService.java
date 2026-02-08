package com.project.foodapp.role.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.project.foodapp.response.Response;
import com.project.foodapp.role.dtos.RoleDTO;

public interface RoleService {
    Response<RoleDTO> createRole(RoleDTO roleDTO);

    Response<RoleDTO> updateRole(RoleDTO roleDTO);

    Response<List<RoleDTO>> getAllRoles();

    Response<?> deleteRole(Long id);

}
