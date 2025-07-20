package com.youssef.real_estate_api.domain;

import com.youssef.real_estate_api.enums.PriceUnit;
import com.youssef.real_estate_api.enums.PropertyType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "properties")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String city;

    private double price;

    private boolean promo;

    @Enumerated(EnumType.STRING)
    private PriceUnit priceUnit;

    @Enumerated(EnumType.STRING)
    private PropertyType type;

    private int rooms;

    private Integer stars; // null if not HOTEL

    private String phone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isPromo() {
        return promo;
    }

    public void setPromo(boolean promo) {
        this.promo = promo;
    }

    public PriceUnit getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(PriceUnit priceUnit) {
        this.priceUnit = priceUnit;
    }

    public PropertyType getType() {
        return type;
    }

    public void setType(PropertyType type) {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }




}