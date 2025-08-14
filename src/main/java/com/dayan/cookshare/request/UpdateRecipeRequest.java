package com.dayan.cookshare.request;

import lombok.Data;

import java.util.List;

@Data
public class UpdateRecipeRequest {
    private String title;
    private String instruction;
    private String prepTime;
    private String cookTime;
    private String category;
    private String description;
    private String cuisine;
    private List<String> ingredients;

}
