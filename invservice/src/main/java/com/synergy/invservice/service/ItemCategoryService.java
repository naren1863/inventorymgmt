package com.synergy.invservice.service;

import com.synergy.invservice.domain.ItemCategoryEntity;
import com.synergy.invservice.dto.ItemCategory;
import com.synergy.invservice.repository.ItemCategoryRepository;
import com.synergy.invservice.util.LogService;
import com.synergy.invservice.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCategoryService {

    @Autowired
    ItemCategoryRepository itemCategoryRepository;

    @Autowired
    ObjectUtil objectUtil;

    public List<ItemCategory> getItemCategories() {
        List<ItemCategoryEntity> itemCategoryEntityList = itemCategoryRepository.findAll();

        List<ItemCategory> itemCategoryList = new ArrayList<>();
        itemCategoryEntityList.forEach(itemCategoryEntity -> {
            itemCategoryList.add(convertToDataObject(itemCategoryEntity));
        });
        LogService.logMessage("getItemCategories: " + itemCategoryList);
        return itemCategoryList;
    }

    public List<ItemCategory> bulkImport(List<ItemCategory> itemCategoryList)
    {
        List<ItemCategory> returnItemCategoryList = new ArrayList<>();
        for(ItemCategory itemCategory: itemCategoryList){
            returnItemCategoryList.add(saveItemCategory(itemCategory));
        }
        return returnItemCategoryList;
    }

    public ItemCategory saveItemCategory(ItemCategory itemCategory)
    {
        ItemCategoryEntity itemCategoryEntity = checkForExistingEntity(itemCategory);
        convertToEntity(itemCategory, itemCategoryEntity);
        LogService.logMessage("saveItemCategory(): itemCategoryEntity: " + itemCategoryEntity);
        itemCategoryEntity = itemCategoryRepository.save(itemCategoryEntity);

        itemCategory = convertToDataObject(itemCategoryEntity);
        LogService.logMessage("saveItemCategory(): itemCategory: " + itemCategory);
        return itemCategory;
    }

    private ItemCategoryEntity checkForExistingEntity(ItemCategory itemCategory)
    {
        ItemCategoryEntity itemCategoryEntity  = itemCategory.getPk() != null
                ? itemCategoryRepository.getReferenceById(itemCategory.getPk())
                : itemCategoryRepository.findByCategoryCode(itemCategory.getCategoryCode());

        return ObjectUtils.isEmpty(itemCategoryEntity)? new ItemCategoryEntity(): itemCategoryEntity;
    }

    public ItemCategory getByCategoryCode(String itemCategoryCode) {
        ItemCategoryEntity itemCategoryEntity = itemCategoryRepository.findByCategoryCode(itemCategoryCode);
        ItemCategory itemCategory = convertToDataObject(itemCategoryEntity);
        LogService.logMessage("getByItemCategoryCode(): response itemCategory: " + itemCategory);
        return itemCategory;
    }

    public void deleteByCategoryCode(String itemCategoryCode) {
        ItemCategoryEntity itemCategoryEntity = itemCategoryRepository.findByCategoryCode(itemCategoryCode);
        itemCategoryRepository.deleteById(itemCategoryEntity.getPk());
        LogService.logMessage("deleteByItemCategoryCode(): Deleting the ItemCategory "+itemCategoryCode+" Successful!!");
    }

    private ItemCategory convertToDataObject(ItemCategoryEntity itemCategoryEntity)
    {
        if(ObjectUtils.isEmpty(itemCategoryEntity)){
            return null;
        }
        ItemCategory itemCategory = new ItemCategory();
        itemCategory.setCategoryCode(itemCategoryEntity.getCategoryCode());
        itemCategory.setCategoryName(itemCategoryEntity.getCategoryName());
        objectUtil.convertToDataObject(itemCategory, itemCategoryEntity);
        return itemCategory;
    }

    private ItemCategoryEntity convertToEntity(ItemCategory itemCategory, ItemCategoryEntity itemCategoryEntity)
    {
        itemCategoryEntity.setCategoryCode(itemCategory.getCategoryCode());
        itemCategoryEntity.setCategoryName(itemCategory.getCategoryName());
        itemCategoryEntity = (ItemCategoryEntity) objectUtil.convertToEntity(itemCategory, itemCategoryEntity);
        return itemCategoryEntity;
    }
}