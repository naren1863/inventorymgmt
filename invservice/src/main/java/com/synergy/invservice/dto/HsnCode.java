package com.synergy.invservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

@lombok.Getter
@lombok.Setter
@ToString(callSuper=true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class HsnCode extends DomainObject{

    @JsonProperty(value = "HsnCode")
    private String hsnCode;

    @JsonProperty(value = "TaxSlab")
    private Double taxSlab;

    @JsonProperty(value = "HsnCodeDescription")
    private String hsnCodeDescription;

}
