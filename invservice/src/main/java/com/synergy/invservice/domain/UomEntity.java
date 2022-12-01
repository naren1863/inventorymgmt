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
@Table(name = "UOM")
@DynamicUpdate
@ToString(callSuper=true)
@EqualsAndHashCode(callSuper=false)
public class UomEntity extends DomainEntity implements Serializable {

    public UomEntity(){}

    public UomEntity(String uomId, String uomDescription) {
        this.uomId = uomId;
        this.uomDescription = uomDescription;
    }

    @Column(name="UOM_ID", unique = true, updatable=false, nullable = false, precision = 8)
    private String uomId;

    @Column(name="UOM_DESCRIPTION", unique = false, updatable=true, nullable = false, precision = 8)
    private String uomDescription;


}
