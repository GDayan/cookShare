package com.dayan.cookshare.controller;

import com.dayan.cookshare.dto.ImageDTO;
import com.dayan.cookshare.model.Image;
import com.dayan.cookshare.service.image.IImageService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")
public class ImageController {
    private final IImageService iImageService;

    @PostMapping("/upload")
    public ResponseEntity<ImageDTO> uploadImage(@RequestParam("file") MultipartFile file, @RequestParam Long recipeId){
        ImageDTO saveImage = iImageService.saveImage(recipeId, file);
        return ResponseEntity.ok(saveImage);
    }

    @PutMapping("/image/{imageId}/update")
    public ResponseEntity<String> updateImage(@PathVariable Long imageId, @RequestBody MultipartFile file){
        iImageService.updateImage(file, imageId);
        return ResponseEntity.ok("Картина обновлена");
    }

    @PutMapping("/image/{imageId}/update")
    public ResponseEntity<String> deleteImage(@PathVariable Long imageId){
        iImageService.deleteImage(imageId);
        return ResponseEntity.ok("Картина удалена");
    }

    @GetMapping("{/imageId}/image")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) throws SQLException {
        Image image = iImageService.getImageById(imageId);
        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, (int)image.getImage().length()));
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileName())).
                header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +image.getFileName() + "\"").body(resource);
    }
}
