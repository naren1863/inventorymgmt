package com.synergy.invservice.repository;

import com.synergy.invservice.domain.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

    @Query("SELECT ie FROM ItemEntity ie WHERE ie.itemId = :itemId")
    ItemEntity findByItemId(@Param("itemId") String itemId);
   
}
