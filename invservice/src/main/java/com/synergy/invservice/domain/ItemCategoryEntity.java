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
@Table(name = "ITEM_CATEGORY")
@DynamicUpdate
@ToString(callSuper=true)
@EqualsAndHashCode(callSuper=false)
public class ItemCategoryEntity extends DomainEntity implements Serializable {

    public ItemCategoryEntity(){}

    public ItemCategoryEntity(String itemCategoryCode, String itemCategoryName) {
        this.categoryCode = itemCategoryCode;
        this.categoryName = itemCategoryName;
    }

    @Column(name="CATEGORY_CODE", unique = true, updatable=false, nullable = false, precision = 8)
    private String categoryCode;

    @Column(name="CATEGORY_NAME", unique = false, updatable=true, nullable = false, precision = 8)
    private String categoryName;


}
