package com.project.foodapp.menu.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.foodapp.category.entity.Category;
import com.project.foodapp.category.repository.CategoryRepository;
import com.project.foodapp.cloudinary.ImageStorageService;
import com.project.foodapp.exceptions.BadRequestException;
import com.project.foodapp.exceptions.NotFoundException;
import com.project.foodapp.menu.dtos.MenuDTO;
import com.project.foodapp.menu.entity.Menu;
import com.project.foodapp.menu.repository.MenuRepository;
import com.project.foodapp.response.Response;
import com.project.foodapp.review.dtos.ReviewDTO;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ImageStorageService cloudinaryStorageService;

    @Override
    public Response<MenuDTO> createMenu(MenuDTO menuDTO) {

        log.info("Inside createMenu()");

        Category category = categoryRepository.findById(menuDTO.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category not found with ID: " + menuDTO.getCategoryId()));

        String imageUrl = null;
        MultipartFile imageFile = menuDTO.getImageFile();

        if (imageFile == null || imageFile.isEmpty()) {
            throw new BadRequestException("Menu Image is needed");
        }

        imageUrl = cloudinaryStorageService.uploadFile(imageFile);

        Menu menu = Menu.builder()
                .name(menuDTO.getName())
                .description(menuDTO.getDescription())
                .price(menuDTO.getPrice())
                .imageUrl(imageUrl)
                .category(category)
                .build();

        Menu savedMenu = menuRepository.save(menu);

        return Response.<MenuDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Menu created successfully")
                .data(modelMapper.map(savedMenu, MenuDTO.class))
                .build();
    }

    @Override
    public Response<MenuDTO> updateMenu(MenuDTO menuDTO) {

        log.info("Inside updateMenu()");

        Menu existingMenu = menuRepository.findById(menuDTO.getId())
                .orElseThrow(() -> new NotFoundException("Menu not found"));

        Category category = categoryRepository.findById(menuDTO.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category not found"));

        String imageUrl = existingMenu.getImageUrl();
        MultipartFile imageFile = menuDTO.getImageFile();

        if (imageFile != null && !imageFile.isEmpty()) {

            if (imageUrl != null && !imageUrl.isEmpty()) {
                String publicId = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
                publicId = publicId.contains(".")
                        ? publicId.substring(0, publicId.lastIndexOf("."))
                        : publicId;

                cloudinaryStorageService.deleteFile(publicId);
                log.info("Deleted old menu image from Cloudinary");
            }

            imageUrl = cloudinaryStorageService.uploadFile(imageFile);
        }

        if (menuDTO.getName() != null && !menuDTO.getName().isBlank())
            existingMenu.setName(menuDTO.getName());

        if (menuDTO.getDescription() != null && !menuDTO.getDescription().isBlank())
            existingMenu.setDescription(menuDTO.getDescription());

        if (menuDTO.getPrice() != null)
            existingMenu.setPrice(menuDTO.getPrice());

        existingMenu.setImageUrl(imageUrl);
        existingMenu.setCategory(category);

        Menu updatedMenu = menuRepository.save(existingMenu);

        return Response.<MenuDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Menu updated successfully")
                .data(modelMapper.map(updatedMenu, MenuDTO.class))
                .build();
    }

    @Override
    public Response<MenuDTO> getMenuById(Long id) {

        log.info("Inside getMenuById()");

        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Menu not found"));

        MenuDTO menuDTO = modelMapper.map(menu, MenuDTO.class);

        if (menuDTO.getReviews() != null) {
            menuDTO.getReviews().sort(Comparator.comparing(ReviewDTO::getId).reversed());
        }

        return Response.<MenuDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Menu retrieved successfully")
                .data(menuDTO)
                .build();
    }

    @Override
    public Response<?> deleteMenu(Long id) {

        log.info("Inside deleteMenu()");

        Menu menuToDelete = menuRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Menu not found with ID: " + id));

        String imageUrl = menuToDelete.getImageUrl();

        if (imageUrl != null && !imageUrl.isEmpty()) {
            String publicId = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
            publicId = publicId.contains(".")
                    ? publicId.substring(0, publicId.lastIndexOf("."))
                    : publicId;

            cloudinaryStorageService.deleteFile(publicId);
            log.info("Deleted image from Cloudinary: " + publicId);
        }

        menuRepository.deleteById(id);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Menu deleted successfully")
                .build();
    }

    @Override
    public Response<List<MenuDTO>> getMenus(Long categoryId, String search) {

        log.info("Inside getMenus()");

        Specification<Menu> spec = buildSpecification(categoryId, search);

        Sort sort = Sort.by(Sort.Direction.DESC, "id");

        List<Menu> menuList = menuRepository.findAll(spec, sort);

        List<MenuDTO> menuDTOS = menuList.stream()
                .map(menu -> modelMapper.map(menu, MenuDTO.class))
                .toList();

        return Response.<List<MenuDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Menus retrieved")
                .data(menuDTOS)
                .build();
    }

    private Specification<Menu> buildSpecification(Long categoryId, String search) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (categoryId != null) {
                predicates.add(cb.equal(root.get("category").get("id"), categoryId));
            }

            if (search != null && !search.isBlank()) {
                String searchTerm = "%" + search.toLowerCase() + "%";

                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("name")), searchTerm),
                        cb.like(cb.lower(root.get("description")), searchTerm)));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
