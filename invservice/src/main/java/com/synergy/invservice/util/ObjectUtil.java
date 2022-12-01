package com.synergy.invservice.util;

import com.synergy.invservice.domain.DomainEntity;
import com.synergy.invservice.dto.DomainObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ObjectUtil {

    public DomainEntity convertToEntity(DomainObject domainObject, DomainEntity domainEntity)
    {
        domainEntity.setPk(domainEntity.getPk());
        domainEntity.setDeleted(domainObject.getDeleted());
        if(domainEntity.getPk() == null){
            domainEntity.setCreatedBy(domainObject.getCreatedBy());
            domainEntity.setCreatedTimestamp(LocalDateTime.now());
        } else {
            domainEntity.setUpdatedBy(domainObject.getUpdatedBy());
            domainEntity.setUpdatedTimestamp(LocalDateTime.now());
        }
        return domainEntity;
    }

    public DomainObject convertToDataObject(DomainObject domainObject, DomainEntity domainEntity)
    {
        domainObject.setPk(domainEntity.getPk());
        domainObject.setDeleted(domainEntity.getDeleted());
        domainObject.setCreatedBy(domainEntity.getCreatedBy());
        domainObject.setUpdatedBy(domainEntity.getUpdatedBy());
        domainObject.setCreatedTimestamp(domainEntity.getCreatedTimestamp());
        domainObject.setUpdatedTimestamp(domainEntity.getUpdatedTimestamp());

        return domainObject;
    }


}
