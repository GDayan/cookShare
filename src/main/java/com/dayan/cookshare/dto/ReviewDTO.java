package com.dayan.cookshare.dto;

import lombok.Data;

@Data
public class ReviewDTO {
    private Long id;
    private int star;
    private String feedback;
    private UserDTO userDTO;
}
