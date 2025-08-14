package com.dayan.cookshare.service.image;

import com.dayan.cookshare.dto.ImageDTO;
import com.dayan.cookshare.model.Image;
import org.springframework.web.multipart.MultipartFile;

public interface IImageService {
    Image getImageById(Long imageId);
    void deleteImage(Long imageId);
    void updateImage(MultipartFile file, Long imageId);
    ImageDTO saveImage(Long recipeId, MultipartFile file);
}
