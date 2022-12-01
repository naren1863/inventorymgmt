package com.synergy.invservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

@lombok.Getter
@lombok.Setter
@ToString(callSuper=true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Brand extends DomainObject{

    @JsonProperty(value = "BrandCode")
    String brandCode;

    @JsonProperty(value = "BrandName")
    private String brandName;
}
