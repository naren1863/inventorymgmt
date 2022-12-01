package com.synergy.invservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

@lombok.Getter
@lombok.Setter
@ToString(callSuper=true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Uom extends DomainObject{

    @JsonProperty(value = "UomId")
    String uomId;

    @JsonProperty(value = "UomDescription")
    private String uomDescription;
}
