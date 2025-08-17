package com.dayan.cookshare.service.image;

import com.dayan.cookshare.dto.ImageDTO;
import com.dayan.cookshare.model.Image;
import com.dayan.cookshare.model.Recipe;
import com.dayan.cookshare.repository.ImageRepository;
import com.dayan.cookshare.service.recipe.IRecipeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService{
    private final ImageRepository imageRepository;
    private final IRecipeService recipeService;

    @Override
    public Image getImageById(Long imageId) {
        return imageRepository.findById(imageId)
                .orElseThrow(() -> new EntityNotFoundException("Image not found"));
    }

    @Override
    public void deleteImage(Long imageId) {
        imageRepository.findById(imageId).ifPresentOrElse(imageRepository::delete, () ->{
            throw new EntityNotFoundException("Image not found");
        });

    }
    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(file.getBytes()); // ✅ просто массив байтов
            imageRepository.save(image);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    @Override
    public ImageDTO saveImage(Long recipeId, MultipartFile file) {
        Recipe recipe = recipeService.getRecipeById(recipeId);

        try {
            Image image = new Image();
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(file.getBytes()); // ✅ напрямую массив
            image.setRecipe(recipe);

            String buildDownloadUrl = "/api/images/image/download/";
            image.setDownloadUrl(buildDownloadUrl);

            Image savedImage = imageRepository.save(image);
            savedImage.setDownloadUrl(buildDownloadUrl + savedImage.getId());
            imageRepository.save(savedImage);

            ImageDTO imageDto = new ImageDTO();
            imageDto.setId(savedImage.getId());
            imageDto.setFileName(savedImage.getFileName());
            imageDto.setDownloadUrl(savedImage.getDownloadUrl());
            return imageDto;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


}
