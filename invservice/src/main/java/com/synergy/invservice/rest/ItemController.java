package com.synergy.invservice.rest;

import com.synergy.invservice.dto.Item;
import com.synergy.invservice.service.ItemService;
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
public class ItemController
{
    @Autowired
    ItemService itemService;

    @RequestMapping(value = "item/saveItem", method = RequestMethod.POST, produces = {
            MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Item> saveItem(@RequestBody Item itemRequest)
    {
        LogService.logMessage("saveItem: itemRequest: " + itemRequest);
        Item subjectItem = itemService.saveItem(itemRequest);
        LogService.logMessage("saveItem: Response: " + subjectItem);
        return new ResponseEntity<Item>(subjectItem, HttpStatus.OK);
    }

    @RequestMapping(value = "item/bulkImport", method = RequestMethod.POST, produces = {
            MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Item>> bulkImport(@RequestBody List<Item> itemList)
    {
        LogService.logMessage("BulkImport: itemList: " + itemList);
        List<Item> responseItemList = itemService.bulkImport(itemList);
        LogService.logMessage("BulkImport: Response: " + responseItemList);
        return new ResponseEntity<List<Item>>(responseItemList, HttpStatus.OK);
    }

    @RequestMapping(value = "item/getByItemId/{itemId}", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Item> getByItemId(@PathVariable String itemId)
    {
        LogService.logMessage("getByItemId: itemId: " + itemId);
        Item item = itemService.getByItemId(itemId);
        return new ResponseEntity<Item>(item, HttpStatus.OK);
    }

    @RequestMapping(value = "item/deleteByItemId/{itemId}", method = RequestMethod.DELETE, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<String> deleteByItemId(@PathVariable String itemId)
    {
        LogService.logMessage("deleteByItemId: itemId: " + itemId);
        itemService.deleteByItemId(itemId);
        return new ResponseEntity<String>("Deleting the item "+itemId+" Successful!", HttpStatus.OK);
    }

    @RequestMapping(value = "/getItems", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getItems()
    {
        LogService.logMessage("ItemController: getItems Rest service called!!");
        List<Item> subjectListResponse = itemService.getItems();
        LogService.logMessage("ItemController:getItems: Response: " + subjectListResponse);
        return new ResponseEntity<List<Item>>(subjectListResponse, HttpStatus.OK);
    }
}
