package com.dayan.cookshare.dto;

import com.dayan.cookshare.model.User;
import lombok.Data;

@Data
public class RecipeDTO {
    private Long id;
    private String title;
    private String instruction;
    private String prepTime;
    private String cookTime;
    private String category;
    private String description;
    private String cuisine;
    private ImageDTO imageDTO;
    private Long likeCount;
    private UserDTO user;

}
