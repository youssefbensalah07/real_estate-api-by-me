package com.youssef.real_estate_api.domain;

import jakarta.persistence.*;

@Entity
public class Address {
    @Id
    @GeneratedValue
    private Long id;
    private String city;
    private String street;
    private String zipCode;

    @OneToOne
    @JoinColumn(name = "property_id")
    private Property property;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }


}
