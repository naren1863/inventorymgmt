package com.synergy.invservice.service;

import com.synergy.invservice.domain.BrandEntity;
import com.synergy.invservice.dto.Brand;
import com.synergy.invservice.repository.BrandRepository;
import com.synergy.invservice.util.LogService;
import com.synergy.invservice.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class BrandService {

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    ObjectUtil objectUtil;

    public List<Brand> getBrands() {
        List<BrandEntity> brandEntityList = brandRepository.findAll();

        List<Brand> brandList = new ArrayList<>();
        brandEntityList.forEach(brandEntity -> {
            brandList.add(convertToDataObject(brandEntity));
        });
        LogService.logMessage("getBrands: " + brandList);
        return brandList;
    }

    public List<Brand> bulkImport(List<Brand> brandList)
    {
        List<Brand> returnBrandList = new ArrayList<>();
        for(Brand brand: brandList){
            returnBrandList.add(saveBrand(brand));
        }
        return returnBrandList;
    }

    public Brand saveBrand(Brand brand)
    {
        BrandEntity brandEntity = checkForExistingEntity(brand);
        convertToEntity(brand, brandEntity);
        LogService.logMessage("saveBrand(): brandEntity: " + brandEntity);
        brandEntity = brandRepository.save(brandEntity);

        brand = convertToDataObject(brandEntity);
        LogService.logMessage("saveBrand(): brand: " + brand);
        return brand;
    }

    private BrandEntity checkForExistingEntity(Brand brand)
    {
        BrandEntity brandEntity  = brand.getPk() != null
                ? brandRepository.getReferenceById(brand.getPk())
                : brandRepository.findByBrandCode(brand.getBrandCode());

        return ObjectUtils.isEmpty(brandEntity)? new BrandEntity(): brandEntity;
    }

    public Brand getByBrandCode(String brandCode) {
        BrandEntity brandEntity = brandRepository.findByBrandCode(brandCode);
        Brand brand = convertToDataObject(brandEntity);
        LogService.logMessage("getByBrandCode(): response brand: " + brand);
        return brand;
    }

    public void deleteByBrandCode(String brandCode) {
        BrandEntity brandEntity = brandRepository.findByBrandCode(brandCode);
        brandRepository.deleteById(brandEntity.getPk());
        LogService.logMessage("deleteByBrandCode(): Deleting the Brand "+brandCode+" Successful!!");
    }

    private Brand convertToDataObject(BrandEntity brandEntity)
    {
        if(ObjectUtils.isEmpty(brandEntity)){
            return null;
        }
        Brand brand = new Brand();
        brand.setBrandCode(brandEntity.getBrandCode());
        brand.setBrandName(brandEntity.getBrandName());
        objectUtil.convertToDataObject(brand, brandEntity);
        return brand;
    }

    private BrandEntity convertToEntity(Brand brand, BrandEntity brandEntity)
    {
        brandEntity.setBrandCode(brand.getBrandCode());
        brandEntity.setBrandName(brand.getBrandName());
        brandEntity = (BrandEntity) objectUtil.convertToEntity(brand, brandEntity);
        return brandEntity;
    }
}