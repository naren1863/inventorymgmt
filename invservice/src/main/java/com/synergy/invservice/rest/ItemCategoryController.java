package com.synergy.invservice.rest;

import com.synergy.invservice.dto.ItemCategory;
import com.synergy.invservice.service.ItemCategoryService;
import com.synergy.invservice.util.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("inventory/service/")
public class ItemCategoryController {

    @Autowired
    ItemCategoryService itemCategoryService;

    @RequestMapping(value = "itemCategory/saveItemCategory", method = RequestMethod.POST, produces = {
            MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ItemCategory> saveItemCategory(@RequestBody ItemCategory itemCategoryRequest)
    {
        LogService.logMessage("saveItemCategory: itemCategoryRequest: " + itemCategoryRequest);
        ItemCategory subjectItemCategory = itemCategoryService.saveItemCategory(itemCategoryRequest);
        LogService.logMessage("saveItemCategory: Response: " + subjectItemCategory);
        return new ResponseEntity<ItemCategory>(subjectItemCategory, HttpStatus.OK);
    }

    @RequestMapping(value = "itemCategory/bulkImport", method = RequestMethod.POST, produces = {
            MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<ItemCategory>> bulkImport(@RequestBody List<ItemCategory> itemCategoryList)
    {
        LogService.logMessage("BulkImport: itemCategoryList: " + itemCategoryList);
        List<ItemCategory> responseItemCategoryList = itemCategoryService.bulkImport(itemCategoryList);
        LogService.logMessage("BulkImport: Response: " + responseItemCategoryList);
        return new ResponseEntity<List<ItemCategory>>(responseItemCategoryList, HttpStatus.OK);
    }

    @RequestMapping(value = "itemCategory/getByItemCategoryCode/{itemCategoryCode}", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ItemCategory> getByItemCategoryCode(@PathVariable String itemCategoryCode)
    {
        LogService.logMessage("getByItemCategoryCode: itemCategoryCode: " + itemCategoryCode);
        ItemCategory itemCategory = itemCategoryService.getByCategoryCode(itemCategoryCode);
        return new ResponseEntity<ItemCategory>(itemCategory, HttpStatus.OK);
    }

    @RequestMapping(value = "itemCategory/deleteByCategoryCode/{categoryCode}", method = RequestMethod.DELETE, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<String> deleteByCategoryCode(@PathVariable String categoryCode)
    {
        LogService.logMessage("deleteByItemCategoryCode: categoryCode: " + categoryCode);
        itemCategoryService.deleteByCategoryCode(categoryCode);
        return new ResponseEntity<String>("Deleting the itemCategory "+categoryCode+" Successful!", HttpStatus.OK);
    }

    @RequestMapping(value = "/getItemCategories", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getItemCategories()
    {
        LogService.logMessage("ItemCategoryController: getItemCategories Rest service called!!");

        List<ItemCategory> itemCategoryListResponse = itemCategoryService.getItemCategories();
        LogService.logMessage("ItemCategoryController:getItemCategories: Response: " + itemCategoryListResponse);
        return new ResponseEntity<List<ItemCategory>>(itemCategoryListResponse, HttpStatus.OK);
    }
}
