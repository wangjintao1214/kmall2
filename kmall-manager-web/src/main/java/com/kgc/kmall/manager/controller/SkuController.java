package com.kgc.kmall.manager.controller;

import com.kgc.kmall.bean.PmsSkuInfo;
import com.kgc.kmall.service.SkuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shkstart
 * @create 2020-12-23 15:44
 */
@CrossOrigin
@RestController
@Api(tags = "sku商品库存相关接口",description = "提供sku商品库存相关的Rest API")
public class SkuController {
    @Reference
    SkuService skuService;
    @ApiOperation("保存sku相关属性接口")
    @PostMapping(value = "/saveSkuInfo")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "库存id(itemID)",required = false),
            @ApiImplicitParam(name = "spuId",value = "商品id",required = true),
            @ApiImplicitParam(name = "price",value = "价格",required = true),
            @ApiImplicitParam(name = "skuName",value = "sku名称",required = true),
            @ApiImplicitParam(name = "skuDesc",value = "商品规格描述",required = true),
            @ApiImplicitParam(name = "weight",value = "重量",required = true),
            @ApiImplicitParam(name = "tmId",value = "品牌(冗余)",required = true),
            @ApiImplicitParam(name = "catalog3Id",value = "三级分类id（冗余)",required = true),
            @ApiImplicitParam(name = "skuDefaultImg",value = "默认显示图片(冗余)",required = true),
            @ApiImplicitParam(name = "skuImageList",value = "商品照片",required = true),
            @ApiImplicitParam(name = "skuAttrValueList",value = "sku平台属性",required = true),
            @ApiImplicitParam(name = "skuSaleAttrValueList",value = "销售属性",required = true),
    })
    public String saveSkuInfo(@RequestBody PmsSkuInfo skuInfo) {
        String result = skuService.saveSkuInfo(skuInfo);
        return result;
    }


}
