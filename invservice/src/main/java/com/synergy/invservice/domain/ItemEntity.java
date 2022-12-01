package com.synergy.invservice.domain;

import com.synergy.invservice.dto.Brand;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "ITEM")
@DynamicUpdate
@ToString(callSuper=true)
@EqualsAndHashCode(callSuper=false)
public class ItemEntity extends DomainEntity {

    @Column(name="ITEM_ID", unique = true, updatable=false, nullable = false, precision = 8)
    private String itemId;

    @Column(name="ITEM_NAME", unique = false, updatable=true, nullable = false, precision = 8)
    private String itemName;

    @Column(name="ITEM_DESCRIPTION", unique = false, updatable=true, nullable = true, precision = 8)
    private String itemDescription;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name="UNIT_OF_MEASURE", referencedColumnName = "UOM_ID",
            foreignKey = @ForeignKey(name = "UOM_ID"))
    private UomEntity uom;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name="CATEGORY", referencedColumnName = "CATEGORY_CODE",
            foreignKey = @ForeignKey(name = "CATEGORY_CODE"))
    private ItemCategoryEntity category;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name="HSN_CODE", referencedColumnName = "HSN_CODE",
            foreignKey = @ForeignKey(name = "HSN_CODE"))
    private HsnCodeEntity hsnCode;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name="BRAND_CODE", referencedColumnName = "BRAND_CODE",
            foreignKey = @ForeignKey(name = "BRAND_CODE"))
    private BrandEntity brand;

    @Column(name="CATALOGUE_NUMBER", unique = false, updatable=true, nullable = true, precision = 8)
    private String catalogueNumber;

    @Column(name="UNIT_COST", unique = false, updatable=true, nullable = true, precision = 8)
    private Double unitCost;

    @Column(name="CURRENCY_CODE", unique = false, updatable=true, nullable = true, length = 5)
    private String currencyCode;
}


