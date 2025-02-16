package com.daily.shop_system.service;

import com.daily.shop_system.dto.ImageDTO;
import com.daily.shop_system.model.Image;
import com.daily.shop_system.model.Product;
import com.daily.shop_system.repository.ImageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final ProductService productService;


    public Image getImageById(Long id){
        return imageRepository.findById(id).orElse(null);
    }

    public void deleteImageById(Long id){
        imageRepository.deleteById(id);
    }

    public List<ImageDTO> saveImages(List<MultipartFile> files, Long productId){

        Product product = productService.getProductById(productId);
        List<ImageDTO> savedImageDTO = new ArrayList<>();
        for (MultipartFile file : files) {
            try{
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String patternUrl = "/api/v1/images/image/download/";

                String downloadUrl = patternUrl + image.getId();
                image.setDowloadUrl(downloadUrl);
                Image savedImage = imageRepository.save(image);

                savedImage.setDowloadUrl(patternUrl + savedImage.getId());
                imageRepository.save(savedImage);

                ImageDTO imageDTO = new ImageDTO();
                imageDTO.setImageId(savedImage.getId());
                imageDTO.setImageName(savedImage.getFileName());
                imageDTO.setDownloadUrl(downloadUrl);
                savedImageDTO.add(imageDTO);

            }catch (IOException | SQLException e){
                throw new RuntimeException(e.getMessage());
            }
        }
        return savedImageDTO;

    }

    public void updateImage(MultipartFile file, Long ImageId){

        Image image = getImageById(ImageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileName(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

    }



}
