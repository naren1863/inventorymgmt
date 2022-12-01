package com.synergy.invservice.service;

import com.mysql.cj.xdevapi.Collection;
import com.synergy.invservice.domain.BrandEntity;
import com.synergy.invservice.domain.HsnCodeEntity;
import com.synergy.invservice.domain.ItemCategoryEntity;
import com.synergy.invservice.domain.ItemEntity;
import com.synergy.invservice.domain.UomEntity;
import com.synergy.invservice.repository.BrandRepository;
import com.synergy.invservice.repository.HsnCodeRepository;
import com.synergy.invservice.repository.ItemCategoryRepository;
import com.synergy.invservice.repository.ItemRepository;
import com.synergy.invservice.dto.Item;
import com.synergy.invservice.repository.UomRepository;
import com.synergy.invservice.util.LogService;
import com.synergy.invservice.util.ObjectUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ObjectUtil objectUtil;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    UomRepository uomRepository;

    @Autowired
    ItemCategoryRepository itemCategoryRepository;

    @Autowired
    HsnCodeRepository hsnCodeRepository;

    public List<Item> getItems()
    {
        List<ItemEntity> itemEntityList = itemRepository.findAll();

        List<Item> itemList = new ArrayList<>();
        itemEntityList.forEach(itemEntity -> {
            itemList.add(convertToDataObject(itemEntity));
        });
        LogService.logMessage("getItems: " + itemList);
        return itemList;
    }

    public List<Item> bulkImport(List<Item> itemList)
    {
        List<Item> returnItemList = new ArrayList<>();
        for(Item item: itemList){
            returnItemList.add(saveItem(item));
        }
        return returnItemList;
    }

    public Item saveItem(Item item)
    {
        ItemEntity itemEntity = checkForExistingEntity(item);
        convertToEntity(item, itemEntity);
        LogService.logMessage("saveItem(): itemEntity: " + itemEntity);
        itemEntity = itemRepository.save(itemEntity);

        item = convertToDataObject(itemEntity);
        LogService.logMessage("saveItem(): item: " + item);
        return item;
    }

    private ItemEntity checkForExistingEntity(Item item)
    {
        ItemEntity itemEntity  = item.getPk() != null
                ? itemRepository.getReferenceById(item.getPk())
                : itemRepository.findByItemId(item.getItemId());

        return ObjectUtils.isEmpty(itemEntity)? new ItemEntity(): itemEntity;
    }

    public Item getByItemId(String itemId) {
        ItemEntity itemEntity = itemRepository.findByItemId(itemId);
        Item item = convertToDataObject(itemEntity);
        LogService.logMessage("getByItemId(): response item: " + item);
        return item;
    }

    public void deleteByItemId(String itemId) {
        ItemEntity itemEntity = itemRepository.findByItemId(itemId);
        itemRepository.deleteById(itemEntity.getPk());
        LogService.logMessage("deleteByItemId(): Deleting the Item "+itemId+" Successful!!");
    }

    private ItemEntity convertToEntity(Item item, ItemEntity itemEntity)
    {
        itemEntity.setItemId(item.getItemId());
        itemEntity.setItemName(item.getItemName());
        itemEntity.setItemDescription(item.getItemDescription());

        itemEntity.setCatalogueNumber(item.getCatalogueNumber());
        itemEntity.setUnitCost(item.getUnitCost());
        itemEntity.setCurrencyCode(item.getCurrencyCode());

        if(!ObjectUtils.isEmpty(item.getUom())
                && !StringUtils.isBlank(item.getUom().getUomId())){
            UomEntity uomEntity = uomRepository.findByUomId(item.getUom().getUomId());
            itemEntity.setUom(uomEntity);
        }

        if(!ObjectUtils.isEmpty(item.getCategory())
                && !StringUtils.isBlank(item.getCategory().getCategoryCode())){
            ItemCategoryEntity itemCategoryEntity = itemCategoryRepository.findByCategoryCode(item.getCategory().getCategoryCode());
            itemEntity.setCategory(itemCategoryEntity);
        }

        if(!ObjectUtils.isEmpty(item.getHsnCode())
                && !StringUtils.isBlank(item.getHsnCode().getHsnCode())){
            HsnCodeEntity hsnCodeEntity = hsnCodeRepository.findByHsnCode(item.getHsnCode().getHsnCode());
            itemEntity.setHsnCode(hsnCodeEntity);
        }

        if(!ObjectUtils.isEmpty(item.getBrand())
                && !StringUtils.isBlank(item.getBrand().getBrandCode())){
            BrandEntity brandEntity = brandRepository.findByBrandCode(item.getBrand().getBrandCode());
            itemEntity.setBrand(brandEntity);
        }
        itemEntity = (ItemEntity) objectUtil.convertToEntity(item, itemEntity);
        return itemEntity;
    }

    private Item convertToDataObject(ItemEntity itemEntity)
    {
        if(ObjectUtils.isEmpty(itemEntity)){
            return null;
        }
        Item item = new Item();
        item.setItemId(itemEntity.getItemId());
        item.setItemName(itemEntity.getItemName());
        item.setItemDescription(itemEntity.getItemDescription());
        item.setCatalogueNumber(itemEntity.getCatalogueNumber());
        item.setUnitCost(itemEntity.getUnitCost());
        item.setCurrencyCode(itemEntity.getCurrencyCode());

        item.setUomFromEntity(itemEntity.getUom());
        item.setBrandFromEntity(itemEntity.getBrand());
        item.setCategoryFromEntity(itemEntity.getCategory());
        item.setHsnCodeFromEntity(itemEntity.getHsnCode());

        objectUtil.convertToDataObject(item, itemEntity);
        return item;
    }
}
