package com.dayan.cookshare.dto;

import com.dayan.cookshare.model.User;
import lombok.Data;

import java.util.List;

@Data
public class RecipeDTO {
    private Long id;
    private String title;
    private String instruction;
    private String prepTime;
    private String cookTime;
    private String category;
    private String description;
    private List<String> ingredients;
    private String cuisine;
    private ImageDTO imageDto;
    private Long likeCount;
    private double averageRating;
    private int totalRateCount;
    private UserDTO user;
    private List<ReviewDTO> reviews;
}
