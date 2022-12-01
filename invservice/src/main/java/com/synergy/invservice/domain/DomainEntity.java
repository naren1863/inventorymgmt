package com.synergy.invservice.domain;

import com.synergy.invservice.repository.LocalDateTimeConverter;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Data
@ToString()
@MappedSuperclass
public class DomainEntity {

    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="PK", unique = true, updatable=false, nullable = false, precision = 8)
    @Id
    private Long pk;

    @Column(name="DELETED")
    private Boolean deleted;

    @Column(name="CREATED_TIMESTAMP")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime createdTimestamp;

    @Column(name="UPDATED_TIMESTAMP")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime updatedTimestamp;

    @Column(name="CREATED_BY", length = 45)
    private String createdBy;

    @Column(name="UPDATED_BY",  length = 45)
    private String updatedBy;
}
