package com.synergy.invservice.rest;

import com.synergy.invservice.dto.Uom;
import com.synergy.invservice.service.UomService;
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
public class UomController {

    @Autowired
    UomService uomService;

    @RequestMapping(value = "uom/saveUom", method = RequestMethod.POST, produces = {
            MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Uom> saveUom(@RequestBody Uom uomRequest)
    {
        LogService.logMessage("saveUom: uomRequest: " + uomRequest);
        Uom subjectUom = uomService.saveUom(uomRequest);
        LogService.logMessage("saveUom: Response: " + subjectUom);
        return new ResponseEntity<Uom>(subjectUom, HttpStatus.OK);
    }

    @RequestMapping(value = "uom/bulkImport", method = RequestMethod.POST, produces = {
            MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Uom>> bulkImport(@RequestBody List<Uom> uomList)
    {
        LogService.logMessage("BulkImport: uomList: " + uomList);
        List<Uom> responseUomList = uomService.bulkImport(uomList);
        LogService.logMessage("BulkImport: Response: " + responseUomList);
        return new ResponseEntity<List<Uom>>(responseUomList, HttpStatus.OK);
    }

    @RequestMapping(value = "uom/getByUom/{uomCode}", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Uom> getByUom(@PathVariable String uomCode)
    {
        LogService.logMessage("getByUom: uomCode: " + uomCode);
        Uom uom = uomService.getByUom(uomCode);
        return new ResponseEntity<Uom>(uom, HttpStatus.OK);
    }

    @RequestMapping(value = "uom/deleteByUomId/{uomId}", method = RequestMethod.DELETE, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<String> deleteByUomId(@PathVariable String uomId)
    {
        LogService.logMessage("deleteByUom: uomId: " + uomId);
        uomService.deleteByUomId(uomId);
        return new ResponseEntity<String>("Deleting the uom "+uomId+" Successful!", HttpStatus.OK);
    }

    @RequestMapping(value = "/getUoms", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getUoms()
    {
        LogService.logMessage("UomController: getUoms Rest service called!!");

        List<Uom> uomListResponse = uomService.getUoms();
        LogService.logMessage("UomController:getUoms: Response: " + uomListResponse);
        return new ResponseEntity<List<Uom>>(uomListResponse, HttpStatus.OK);
    }
}
