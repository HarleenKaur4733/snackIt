package com.project.foodapp.menu.services;

import java.util.List;

import com.project.foodapp.menu.dtos.MenuDTO;
import com.project.foodapp.response.Response;

public interface MenuService {

    Response<MenuDTO> createMenu(MenuDTO menuDTO);

    Response<MenuDTO> updateMenu(MenuDTO menuDTO);

    Response<MenuDTO> getMenuById(Long id);

    Response<?> deleteMenu(Long id);

    Response<List<MenuDTO>> getMenus(Long categoryId, String search);

}
