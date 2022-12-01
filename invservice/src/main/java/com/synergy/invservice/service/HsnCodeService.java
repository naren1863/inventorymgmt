package com.synergy.invservice.service;

import com.synergy.invservice.domain.HsnCodeEntity;
import com.synergy.invservice.dto.HsnCode;
import com.synergy.invservice.repository.HsnCodeRepository;
import com.synergy.invservice.util.LogService;
import com.synergy.invservice.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class HsnCodeService {

    @Autowired
    HsnCodeRepository hsnCodeRepository;

    @Autowired
    ObjectUtil objectUtil;

    public List<HsnCode> getHsnCodes() {
        List<HsnCodeEntity> hsnCodeEntityList = hsnCodeRepository.findAll();

        List<HsnCode> hsnCodeList = new ArrayList<>();
        hsnCodeEntityList.forEach(hsnCodeEntity -> {
            hsnCodeList.add(convertToDataObject(hsnCodeEntity));
        });
        LogService.logMessage("getHsnCodes: " + hsnCodeList);
        return hsnCodeList;
    }

    public List<HsnCode> bulkImport(List<HsnCode> hsnCodeList)
    {
        List<HsnCode> returnHsnCodeList = new ArrayList<>();
        for(HsnCode hsnCode: hsnCodeList){
            returnHsnCodeList.add(saveHsnCode(hsnCode));
        }
        return returnHsnCodeList;
    }

    public HsnCode saveHsnCode(HsnCode hsnCode)
    {
        HsnCodeEntity hsnCodeEntity = checkForExistingEntity(hsnCode);
        convertToEntity(hsnCode, hsnCodeEntity);
        LogService.logMessage("saveHsnCode(): hsnCodeEntity: " + hsnCodeEntity);
        hsnCodeEntity = hsnCodeRepository.save(hsnCodeEntity);

        hsnCode = convertToDataObject(hsnCodeEntity);
        LogService.logMessage("saveHsnCode(): hsnCode: " + hsnCode);
        return hsnCode;
    }

    private HsnCodeEntity checkForExistingEntity(HsnCode hsnCode)
    {
        HsnCodeEntity hsnCodeEntity  = hsnCode.getPk() != null
                ? hsnCodeRepository.getReferenceById(hsnCode.getPk())
                : hsnCodeRepository.findByHsnCode(hsnCode.getHsnCode());

        return ObjectUtils.isEmpty(hsnCodeEntity)? new HsnCodeEntity(): hsnCodeEntity;
    }

    public HsnCode getByHsnCode(String hsnCode) {
        HsnCodeEntity hsnCodeEntity = hsnCodeRepository.findByHsnCode(hsnCode);
        HsnCode hsnCodeObj = convertToDataObject(hsnCodeEntity);
        LogService.logMessage("getByHsnCode(): response hsnCode: " + hsnCode);
        return hsnCodeObj;
    }

    public void deleteByHsnCode(String hsnCode) {
        HsnCodeEntity hsnCodeEntity = hsnCodeRepository.findByHsnCode(hsnCode);
        hsnCodeRepository.deleteById(hsnCodeEntity.getPk());
        LogService.logMessage("deleteByHsnCode(): Deleting the HsnCode "+hsnCode+" Successful!!");
    }

    private HsnCode convertToDataObject(HsnCodeEntity hsnCodeEntity)
    {
        if(ObjectUtils.isEmpty(hsnCodeEntity)){
            return null;
        }
        HsnCode hsnCode = new HsnCode();
        hsnCode.setHsnCode(hsnCodeEntity.getHsnCode());
        hsnCode.setTaxSlab(hsnCodeEntity.getTaxSlab());
        hsnCode.setHsnCodeDescription(hsnCodeEntity.getHsnCodeDescription());
        objectUtil.convertToDataObject(hsnCode, hsnCodeEntity);
        return hsnCode;
    }

    private HsnCodeEntity convertToEntity(HsnCode hsnCode, HsnCodeEntity hsnCodeEntity)
    {
        hsnCodeEntity.setHsnCode(hsnCode.getHsnCode());
        hsnCodeEntity.setTaxSlab(hsnCode.getTaxSlab());
        hsnCodeEntity.setHsnCodeDescription(hsnCode.getHsnCodeDescription());
        hsnCodeEntity = (HsnCodeEntity) objectUtil.convertToEntity(hsnCode, hsnCodeEntity);
        return hsnCodeEntity;
    }
}