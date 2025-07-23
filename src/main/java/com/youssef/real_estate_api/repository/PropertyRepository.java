package com.youssef.real_estate_api.repository;


import com.youssef.real_estate_api.domain.Property;
import com.youssef.real_estate_api.enums.PriceUnit;
import com.youssef.real_estate_api.enums.PropertyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    @Query("SELECT p FROM Property p WHERE "
            + "(:city IS NULL OR p.city = :city) AND "
            + "(:promo IS NULL OR p.promo = :promo) AND "
            + "(:type IS NULL OR p.type = :type) AND "
            + "(:unit IS NULL OR p.priceUnit = :unit) AND "
            + "(:minRooms IS NULL OR p.rooms >= :minRooms) AND "
            + "(:stars IS NULL OR p.stars >= :stars)")
    List<Property> customFilter(

            @Param("city") String city,
            @Param("promo") Boolean promo,
            @Param("type") PropertyType type,      // نوع enum
            @Param("unit") PriceUnit unit,          // نوع enum
            @Param("minRooms") Integer minRooms,
            @Param("stars") Integer stars);
    @Query("SELECT p FROM Property p JOIN FETCH p.owner WHERE p.id = :id")
    Optional<Property> findByIdWithOwner(@Param("id") Long id);

}

