package com.synergy.invservice.rest;

import com.synergy.invservice.dto.HsnCode;
import com.synergy.invservice.service.HsnCodeService;
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
public class HsnCodeController {

    @Autowired
    HsnCodeService hsnCodeService;

    @RequestMapping(value = "hsnCode/saveHsnCode", method = RequestMethod.POST, produces = {
            MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<HsnCode> saveHsnCode(@RequestBody HsnCode hsnCodeRequest)
    {
        LogService.logMessage("saveHsnCode: hsnCodeRequest: " + hsnCodeRequest);
        HsnCode subjectHsnCode = hsnCodeService.saveHsnCode(hsnCodeRequest);
        LogService.logMessage("saveHsnCode: Response: " + subjectHsnCode);
        return new ResponseEntity<HsnCode>(subjectHsnCode, HttpStatus.OK);
    }

    @RequestMapping(value = "hsnCode/bulkImport", method = RequestMethod.POST, produces = {
            MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<HsnCode>> bulkImport(@RequestBody List<HsnCode> hsnCodeList)
    {
        LogService.logMessage("BulkImport: hsnCodeList: " + hsnCodeList);
        List<HsnCode> responseHsnCodeList = hsnCodeService.bulkImport(hsnCodeList);
        LogService.logMessage("BulkImport: Response: " + responseHsnCodeList);
        return new ResponseEntity<List<HsnCode>>(responseHsnCodeList, HttpStatus.OK);
    }

    @RequestMapping(value = "hsnCode/getByHsnCode/{hsnCode}", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<HsnCode> getByHsnCode(@PathVariable String hsnCode)
    {
        LogService.logMessage("getByHsnCode: hsnCode: " + hsnCode);
        HsnCode hsnCodeObj = hsnCodeService.getByHsnCode(hsnCode);
        return new ResponseEntity<HsnCode>(hsnCodeObj, HttpStatus.OK);
    }

    @RequestMapping(value = "hsnCode/deleteByHsnCode/{hsnCode}", method = RequestMethod.DELETE, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<String> deleteByHsnCode(@PathVariable String hsnCode)
    {
        LogService.logMessage("deleteByHsnCode: hsnCode: " + hsnCode);
        hsnCodeService.deleteByHsnCode(hsnCode);
        return new ResponseEntity<String>("Deleting the hsnCode "+hsnCode+" Successful!", HttpStatus.OK);
    }

    @RequestMapping(value = "/getHsnCodes", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getHsnCodes()
    {
        LogService.logMessage("HsnCodeController: getHsnCodes Rest service called!!");

        List<HsnCode> hsnCodeListResponse = hsnCodeService.getHsnCodes();
        LogService.logMessage("HsnCodeController:getHsnCodes: Response: " + hsnCodeListResponse);
        return new ResponseEntity<List<HsnCode>>(hsnCodeListResponse, HttpStatus.OK);
    }
}
