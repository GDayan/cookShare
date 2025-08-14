package com.dayan.cookshare.request;

import com.dayan.cookshare.model.Recipe;
import com.dayan.cookshare.model.User;
import lombok.Data;

@Data
public class CreateRecipeRequest {
    private Recipe recipe;
    private User user;
}
