package com.synergy.invservice.repository;

import com.synergy.invservice.domain.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BrandRepository extends JpaRepository<BrandEntity, Long> {

    @Query("SELECT be FROM BrandEntity be WHERE be.brandCode = :brandCode")
    BrandEntity findByBrandCode(@Param("brandCode") String brandCode);
}
