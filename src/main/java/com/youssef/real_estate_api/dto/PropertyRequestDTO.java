package com.youssef.real_estate_api.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyRequestDTO {

    @NotBlank
    private String title;

    @NotBlank
    private String city;

    @Positive
    private double price;

    private boolean promo;

    @NotBlank
    private String priceUnit;

    @NotBlank
    private String type;

    private int rooms;

    private Integer stars;

    private String ownerUsername;
    @NotBlank
    private String phone;

    @NotNull
    private Long ownerId;

    private AddressDTO address;
    private List<PhotoDTO> photos;
    private PromotionDTO promotion;


    public @NotBlank String getTitle() {
        return title;
    }

    public void setTitle(@NotBlank String title) {
        this.title = title;
    }

    public @NotBlank String getCity() {
        return city;
    }

    public void setCity(@NotBlank String city) {
        this.city = city;
    }

    @Positive
    public double getPrice() {
        return price;
    }

    public void setPrice(@Positive double price) {
        this.price = price;
    }

    public boolean isPromo() {
        return promo;
    }

    public void setPromo(boolean promo) {
        this.promo = promo;
    }

    public @NotBlank String getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(@NotBlank String priceUnit) {
        this.priceUnit = priceUnit;
    }

    public @NotBlank String getType() {
        return type;
    }

    public void setType(@NotBlank String type) {
        this.type = type;
    }

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public @NotBlank String getPhone() {
        return phone;
    }

    public void setPhone(@NotBlank String phone) {
        this.phone = phone;
    }

    public @NotNull Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(@NotNull Long ownerId) {
        this.ownerId = ownerId;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public List<PhotoDTO> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoDTO> photos) {
        this.photos = photos;
    }

    public PromotionDTO getPromotion() {
        return promotion;
    }

    public void setPromotion(PromotionDTO promotion) {
        this.promotion = promotion;
    }
}
