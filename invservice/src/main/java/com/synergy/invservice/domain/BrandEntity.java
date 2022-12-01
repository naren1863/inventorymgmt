package com.synergy.invservice.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "BRAND")
@DynamicUpdate
@ToString(callSuper=true)
@EqualsAndHashCode(callSuper=false)
public class BrandEntity extends DomainEntity implements Serializable {

    public BrandEntity(){}

    public BrandEntity(String brandCode, String brandName) {
        this.brandCode = brandCode;
        this.brandName = brandName;
    }

    @Column(name="BRAND_CODE", unique = true, updatable=false, nullable = false, precision = 8)
    private String brandCode;

    @Column(name="BRAND_NAME", unique = false, updatable=true, nullable = false, precision = 8)
    private String brandName;


}
