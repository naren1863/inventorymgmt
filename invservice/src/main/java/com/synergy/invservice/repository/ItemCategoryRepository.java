package com.synergy.invservice.repository;

import com.synergy.invservice.domain.ItemCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemCategoryRepository extends JpaRepository<ItemCategoryEntity, Long> {

    @Query("SELECT be FROM ItemCategoryEntity be WHERE be.categoryCode = :categoryCode")
    ItemCategoryEntity findByCategoryCode(@Param("categoryCode") String categoryCode);
}
