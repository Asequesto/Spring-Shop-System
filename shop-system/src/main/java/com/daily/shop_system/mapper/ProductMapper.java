package com.daily.shop_system.mapper;

import com.daily.shop_system.dto.ProductDTO;
import com.daily.shop_system.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = ImageMapper.class)
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "images", target = "images")
    ProductDTO productToProductDTO(Product product);

    @Mapping(source = "images", target = "images")
    Product productDTOToProduct(ProductDTO productDTO);

    List<ProductDTO> productListToProductDTOList(List<Product> productList);
    List<Product> productDTOListToProductList(List<ProductDTO> productDTOList);

}
