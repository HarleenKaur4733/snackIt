package com.project.foodapp.menu.dtos;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.foodapp.review.dtos.ReviewDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MenuDTO {

    private Long id;

    @NotBlank(message = "Menu name is mandatory")
    private String name;

    private String description;

    @NotNull(message = "Price is mandatory")
    @Positive(message = "Price must be positive")
    private BigDecimal price;

    private String imageUrl;

    @NotNull(message = "Category ID is mandatory")
    private Long categoryId;

    private MultipartFile imageFile; // For image upload

    private List<ReviewDTO> reviews;

}
