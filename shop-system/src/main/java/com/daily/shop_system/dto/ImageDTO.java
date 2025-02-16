package com.daily.shop_system.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ImageDTO {

    private Long imageId;
    private String imageName;
    private String downloadUrl;

}
