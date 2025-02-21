package com.daily.shop_system.mapper;

import com.daily.shop_system.dto.ImageDTO;
import com.daily.shop_system.model.Image;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ImageMapper {

    @Mapping(source = "id", target = "imageId")
    @Mapping(source = "fileName", target = "imageName")
    ImageDTO imageToImageDTO(Image image);

    @Mapping(source = "imageId", target = "id")
    @Mapping(source = "imageName", target = "fileName")
    Image imageDTOToImage(ImageDTO imageDTO);

    List<ImageDTO> imageListToImageDTOList(List<Image> images);
    List<Image> imageDTOListToImageList(List<ImageDTO> imageDTOs);

}
