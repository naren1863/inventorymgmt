package com.synergy.invservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;

;

@lombok.Getter
@lombok.Setter
@lombok.ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class DomainObject {

    @JsonProperty(value = "Pk")
    private Long pk;

    @JsonProperty(value = "Deleted")
    private Boolean deleted = false;

    @JsonProperty(value = "CreatedTimestamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdTimestamp;

    @JsonProperty(value = "UpdatedTimestamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updatedTimestamp;

    @JsonProperty(value = "CreatedBy")
    private String createdBy;

    @JsonProperty(value = "UpdatedBy")
    private String updatedBy;
}
