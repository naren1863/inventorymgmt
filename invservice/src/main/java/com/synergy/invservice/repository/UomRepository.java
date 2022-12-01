package com.synergy.invservice.repository;

import com.synergy.invservice.domain.BrandEntity;
import com.synergy.invservice.domain.UomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UomRepository extends JpaRepository<UomEntity, Long> {

    @Query("SELECT ue FROM UomEntity ue WHERE ue.uomId = :uomId")
    UomEntity findByUomId(@Param("uomId") String uom);
}
