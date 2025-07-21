package com.youssef.real_estate_api.mapper;

import com.youssef.real_estate_api.domain.Address;
import com.youssef.real_estate_api.domain.Photo;
import com.youssef.real_estate_api.domain.Promotion;
import com.youssef.real_estate_api.domain.Property;
import com.youssef.real_estate_api.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface PropertyMapper {

    @Mapping(target = "ownerUsername", expression = "java(property.getOwner() != null ? property.getOwner().getUsername() : null)")
    @Mapping(target = "priceUnit", expression = "java(property.getPriceUnit().name())")
    @Mapping(target = "type", expression = "java(property.getType().name())")
    PropertyResponseDTO toDTO(Property property);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "priceUnit", expression = "java(com.youssef.real_estate_api.enums.PriceUnit.valueOf(dto.getPriceUnit().toUpperCase()))")
    @Mapping(target = "type", expression = "java(com.youssef.real_estate_api.enums.PropertyType.valueOf(dto.getType().toUpperCase()))")
    Property toEntity(PropertyRequestDTO dto);

    AddressDTO addressToDto(Address address);
    Address dtoToAddress(AddressDTO dto);

    Photo dtoToPhoto(PhotoDTO dto);
    PhotoDTO photoToDto(Photo photo);

    Promotion dtoToPromotion(PromotionDTO dto);
    PromotionDTO promotionToDto(Promotion promotion);


    }

