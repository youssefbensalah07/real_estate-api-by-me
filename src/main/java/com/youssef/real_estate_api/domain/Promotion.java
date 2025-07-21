package com.youssef.real_estate_api.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Promotion {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    @JoinColumn(name = "property_id")
    private Property property;

    private double discountPercentage;
    private LocalDate startDate;
    private LocalDate endDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }


}
