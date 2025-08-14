package com.dayan.cookshare.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ImageDTO {
    private Long id;
    private String fileName;
    private String download;
}
