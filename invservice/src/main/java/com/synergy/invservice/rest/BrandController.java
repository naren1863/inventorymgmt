package com.synergy.invservice.rest;

import com.synergy.invservice.dto.Brand;
import com.synergy.invservice.service.BrandService;
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
public class BrandController {

    @Autowired
    BrandService brandService;

    @RequestMapping(value = "brand/saveBrand", method = RequestMethod.POST, produces = {
            MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Brand> saveBrand(@RequestBody Brand brandRequest)
    {
        LogService.logMessage("saveBrand: brandRequest: " + brandRequest);
        Brand subjectBrand = brandService.saveBrand(brandRequest);
        LogService.logMessage("saveBrand: Response: " + subjectBrand);
        return new ResponseEntity<Brand>(subjectBrand, HttpStatus.OK);
    }

    @RequestMapping(value = "brand/bulkImport", method = RequestMethod.POST, produces = {
            MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Brand>> bulkImport(@RequestBody List<Brand> brandList)
    {
        LogService.logMessage("BulkImport: brandList: " + brandList);
        List<Brand> responseBrandList = brandService.bulkImport(brandList);
        LogService.logMessage("BulkImport: Response: " + responseBrandList);
        return new ResponseEntity<List<Brand>>(responseBrandList, HttpStatus.OK);
    }

    @RequestMapping(value = "brand/getByBrandCode/{brandCode}", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Brand> getByBrandCode(@PathVariable String brandCode)
    {
        LogService.logMessage("getByBrandCode: brandCode: " + brandCode);
        Brand brand = brandService.getByBrandCode(brandCode);
        return new ResponseEntity<Brand>(brand, HttpStatus.OK);
    }

    @RequestMapping(value = "brand/deleteByBrandCode/{brandCode}", method = RequestMethod.DELETE, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<String> deleteByBrandCode(@PathVariable String brandCode)
    {
        LogService.logMessage("deleteByBrandCode: brandCode: " + brandCode);
        brandService.deleteByBrandCode(brandCode);
        return new ResponseEntity<String>("Deleting the brand "+brandCode+" Successful!", HttpStatus.OK);
    }

    @RequestMapping(value = "/getBrands", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getBrands()
    {
        LogService.logMessage("BrandController: getBrands Rest service called!!");

        List<Brand> brandListResponse = brandService.getBrands();
        LogService.logMessage("BrandController:getBrands: Response: " + brandListResponse);
        return new ResponseEntity<List<Brand>>(brandListResponse, HttpStatus.OK);
    }
}
