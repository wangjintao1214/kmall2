package com.kgc.kmall.manager.controller;

import com.kgc.kmall.bean.PmsBaseCatalog1;
import com.kgc.kmall.bean.PmsBaseCatalog2;
import com.kgc.kmall.bean.PmsBaseCatalog3;
import com.kgc.kmall.service.CatalogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author shkstart
 * @create 2020-12-16 15:31
 */
@CrossOrigin
@RestController
@Api(tags = "级别分类相关接口",description = "提供级别分类相关的Rest API")
public class CatalogController {

    @Reference
    CatalogService catalogService;
    @ApiOperation("一级分类接口")
    @PostMapping("/getCatalog1")
    public List<PmsBaseCatalog1> getCatalog1(){
        List<PmsBaseCatalog1> pmsBaseCatalog1s = catalogService.selectAll();
        for (PmsBaseCatalog1 pmsBaseCatalog1 : pmsBaseCatalog1s) {
            System.out.println(pmsBaseCatalog1.toString());
        }
        return pmsBaseCatalog1s;
    }
    @ApiOperation("二级分类接口")
    @PostMapping("/getCatalog2")
    @ApiImplicitParam(name="catalog1Id",value = "一级分类id",required = true)
    public List<PmsBaseCatalog2> getCatalog2(Integer catalog1Id){
        List<PmsBaseCatalog2> pmsBaseCatalog2s = catalogService.selectAllBycatalog1Id(catalog1Id);
        for (PmsBaseCatalog2 pmsBaseCatalog2 : pmsBaseCatalog2s) {
            System.out.println(pmsBaseCatalog2.toString());
        }
        return pmsBaseCatalog2s;
    }
    @ApiOperation("三级分类接口")
    @ApiImplicitParam(name = "catalog2Id",value = "二级分类id",required = true)
    @PostMapping("/getCatalog3")
    public List<PmsBaseCatalog3> getCatalog3(Integer catalog2Id){
        List<PmsBaseCatalog3> pmsBaseCatalog3s = catalogService.selectAllBycatalog2Id(catalog2Id);
        for (PmsBaseCatalog3 pmsBaseCatalog3 : pmsBaseCatalog3s) {
            System.out.println(pmsBaseCatalog3.toString());
        }
        return pmsBaseCatalog3s;
    }


}
