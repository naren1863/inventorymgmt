package com.synergy.invservice.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "HSN_CODE")
@DynamicUpdate
@ToString(callSuper=true)
@EqualsAndHashCode(callSuper=false)
public class HsnCodeEntity extends DomainEntity implements Serializable {

    public HsnCodeEntity(){}

    public HsnCodeEntity(String hsnCode, double taxSlab, String hsnCodeDescription) {
        this.hsnCode = hsnCode;
        this.taxSlab = taxSlab;
        this.hsnCodeDescription = hsnCodeDescription;
    }

    @Column(name="HSN_CODE", unique = true, updatable=false, nullable = false, precision = 8)
    private String hsnCode;

    @Column(name="TAX_SLAB", unique = false, updatable=true, nullable = false, precision = 8)
    private double taxSlab;

    @Column(name="HSN_CODE_DESCRIPTION", unique = true, updatable=false, nullable = false, precision = 8)
    private String hsnCodeDescription;

}
