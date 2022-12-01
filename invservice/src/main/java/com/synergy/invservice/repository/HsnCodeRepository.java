package com.synergy.invservice.repository;

import com.synergy.invservice.domain.HsnCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HsnCodeRepository extends JpaRepository<HsnCodeEntity, Long> {

    @Query("SELECT be FROM HsnCodeEntity be WHERE be.hsnCode = :hsnCode")
    HsnCodeEntity findByHsnCode(@Param("hsnCode") String hsnCode);
}
