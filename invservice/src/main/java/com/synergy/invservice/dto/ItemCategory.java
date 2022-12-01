package com.synergy.invservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

@lombok.Getter
@lombok.Setter
@ToString(callSuper=true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemCategory extends DomainObject{

    @JsonProperty(value = "CategoryCode")
    String categoryCode;

    @JsonProperty(value = "CategoryName")
    private String categoryName;
}
