package com.synergy.invservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.synergy.invservice.domain.BrandEntity;
import com.synergy.invservice.domain.HsnCodeEntity;
import com.synergy.invservice.domain.ItemCategoryEntity;
import com.synergy.invservice.domain.UomEntity;
import lombok.ToString;

import javax.persistence.Column;

@lombok.Getter
@lombok.Setter
@ToString(callSuper=true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Item extends DomainObject {

    @JsonProperty(value = "ItemId")
    String itemId;

    @JsonProperty(value = "ItemName")
    private String itemName;

    @JsonProperty(value = "ItemDescription")
    private String itemDescription;

    @JsonProperty(value = "Uom")
    private Uom uom;

    @JsonProperty(value = "Category")
    private ItemCategory category;

    @JsonProperty(value = "HsnCode")
    private HsnCode hsnCode;

    @JsonProperty(value = "Brand")
    private Brand brand;

    @JsonProperty(value = "CatalogueNumber")
    private String catalogueNumber;

    @JsonProperty(value = "UnitCost")
    private Double unitCost;

    @JsonProperty(value = "CurrencyCode")
    private String currencyCode;

    public void setBrandFromEntity(BrandEntity brandEntity){
        if(null == brandEntity){
            return;
        }
        if(null == this.brand){
            setBrand(new Brand());
        }
        getBrand().setBrandCode(brandEntity.getBrandCode());
        getBrand().setBrandName(brandEntity.getBrandName());
    }

    public void setCategoryFromEntity(ItemCategoryEntity itemCategoryEntity){
        if(null == itemCategoryEntity){
            return;
        }
        if(null == this.category){
            setCategory(new ItemCategory());
        }
        getCategory().setCategoryCode(itemCategoryEntity.getCategoryCode());
        getCategory().setCategoryName(itemCategoryEntity.getCategoryName());
    }

    public void setUomFromEntity(UomEntity uomEntity){
        if(null == uomEntity){
            return;
        }
        if(null == this.uom){
            setUom(new Uom());
        }
        getUom().setUomId(uomEntity.getUomId());
        getUom().setUomDescription(uomEntity.getUomDescription());
    }

    public void setHsnCodeFromEntity(HsnCodeEntity hsnCodeEntity) {
        if(null == hsnCodeEntity){
            return;
        }
        if(null == this.getHsnCode()){
            setHsnCode(new HsnCode());
        }
        getHsnCode().setHsnCode(hsnCodeEntity.getHsnCode());
        getHsnCode().setHsnCodeDescription(hsnCodeEntity.getHsnCodeDescription());
        getHsnCode().setTaxSlab(hsnCodeEntity.getTaxSlab());
    }

}
