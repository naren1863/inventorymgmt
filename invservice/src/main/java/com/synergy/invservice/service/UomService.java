package com.synergy.invservice.service;

import com.synergy.invservice.domain.UomEntity;
import com.synergy.invservice.dto.Uom;
import com.synergy.invservice.repository.UomRepository;
import com.synergy.invservice.util.LogService;
import com.synergy.invservice.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class UomService {

    @Autowired
    UomRepository uomRepository;

    @Autowired
    ObjectUtil objectUtil;

    public List<Uom> getUoms() {
        List<UomEntity> uomEntityList = uomRepository.findAll();

        List<Uom> uomList = new ArrayList<>();
        uomEntityList.forEach(uomEntity -> {
            uomList.add(convertToDataObject(uomEntity));
        });
        LogService.logMessage("getUoms: " + uomList);
        return uomList;
    }

    public List<Uom> bulkImport(List<Uom> uomList)
    {
        List<Uom> returnUomList = new ArrayList<>();
        for(Uom uom: uomList){
            returnUomList.add(saveUom(uom));
        }
        return returnUomList;
    }

    public Uom saveUom(Uom uom)
    {
        UomEntity uomEntity = checkForExistingEntity(uom);
        convertToEntity(uom, uomEntity);
        LogService.logMessage("saveUom(): uomEntity: " + uomEntity);
        uomEntity = uomRepository.save(uomEntity);

        uom = convertToDataObject(uomEntity);
        LogService.logMessage("saveUom(): uom: " + uom);
        return uom;
    }

    private UomEntity checkForExistingEntity(Uom uom)
    {
        UomEntity uomEntity  = uom.getPk() != null
                ? uomRepository.getReferenceById(uom.getPk())
                : uomRepository.findByUomId(uom.getUomId());

        return ObjectUtils.isEmpty(uomEntity)? new UomEntity(): uomEntity;
    }

    public Uom getByUom(String uomId) {
        UomEntity uomEntity = uomRepository.findByUomId(uomId);
        Uom uom = convertToDataObject(uomEntity);
        LogService.logMessage("getByUom(): response uom: " + uom);
        return uom;
    }

    public void deleteByUomId(String uomId) {
        UomEntity uomEntity = uomRepository.findByUomId(uomId);
        uomRepository.deleteById(uomEntity.getPk());
        LogService.logMessage("deleteByUom(): Deleting the Uom "+ uomId +" Successful!!");
    }

    private Uom convertToDataObject(UomEntity uomEntity)
    {
        if(ObjectUtils.isEmpty(uomEntity)){
            return null;
        }
        Uom uom = new Uom();
        uom.setUomId(uomEntity.getUomId());
        uom.setUomDescription(uomEntity.getUomDescription());
        objectUtil.convertToDataObject(uom, uomEntity);
        return uom;
    }

    private UomEntity convertToEntity(Uom uom, UomEntity uomEntity)
    {
        uomEntity.setUomId(uom.getUomId());
        uomEntity.setUomDescription(uom.getUomDescription());
        uomEntity = (UomEntity) objectUtil.convertToEntity(uom, uomEntity);
        return uomEntity;
    }
}