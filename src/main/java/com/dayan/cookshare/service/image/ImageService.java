package com.dayan.cookshare.service.image;

import com.dayan.cookshare.dto.ImageDTO;
import com.dayan.cookshare.model.Image;
import com.dayan.cookshare.model.Recipe;
import com.dayan.cookshare.repository.ImageRepository;
import com.dayan.cookshare.repository.RecipeRepository;
import com.dayan.cookshare.service.recipe.IRecipeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService{
    private final ImageRepository imageRepository;
    private final IRecipeService iRecipeService;

    @Override
    public Image getImageById(Long imageId) {
        return imageRepository.findById(imageId).orElseThrow(() -> new EntityNotFoundException("Картина не найдена"));
    }

    @Override
    public void deleteImage(Long imageId) {
        imageRepository.findById(imageId).ifPresentOrElse(imageRepository::delete, () -> {throw new EntityNotFoundException("Картина не найдена");});
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);
        try{
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        }
        catch(IOException | SQLException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public ImageDTO saveImage(Long recipeId, MultipartFile file) {
        Recipe recipe = iRecipeService.getRecipeById(recipeId);
        try{
            Image image = new Image();
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
            image.setRecipe(recipe);

            String buildDownloadURL = "/api/images/image/download/";
            String downloadURL = buildDownloadURL + image.getId();
            image.setDownloadUrl(downloadURL);

            Image saveImage = imageRepository.save(image);
            saveImage.setDownloadUrl(buildDownloadURL + saveImage.getId());
            imageRepository.save(saveImage);

            ImageDTO imageDTO = new ImageDTO();
            imageDTO.setId(saveImage.getId());
            image.setFileName(saveImage.getFileName());
            image.setDownloadUrl(saveImage.getDownloadUrl());

            return imageDTO;
        }
        catch(IOException | SQLException e){
            throw  new RuntimeException(e.getMessage());
        }

    }
}
