package com.kgc.kmall.manager.controller;

import com.kgc.kmall.bean.PmsBaseSaleAttr;
import com.kgc.kmall.bean.PmsProductImage;
import com.kgc.kmall.bean.PmsProductInfo;
import com.kgc.kmall.bean.PmsProductSaleAttr;
import com.kgc.kmall.service.SpuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FilenameUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author shkstart
 * @create 2020-12-17 11:49
 */
@CrossOrigin
@RestController
@Api(tags = "商品表接口",description = "提供商品表相关的Rest API")
public class SpuController {
    @Reference
    SpuService spuService;

    @Value("${fileServer.url}")
    String fileUrl;


    @ApiOperation("商品表接口")
    @GetMapping("/spuList")
    @ApiImplicitParam(name = "catalog3Id",value = "三级分类id",required = true)
    public List<PmsProductInfo> spuList(Long catalog3Id){
        List<PmsProductInfo> pmsProductInfos = spuService.selectAllBycatalog3Id(catalog3Id);
        for (PmsProductInfo pmsProductInfo : pmsProductInfos) {
            System.out.println(pmsProductInfo.toString());
        }
        return pmsProductInfos;
    }
    @ApiOperation("上传商品图片接口")
    @PostMapping("/fileUpload")
    @ApiImplicitParam(name = "file",value = "商品照片",required = false)
    public String fileUpload(@RequestParam("file")MultipartFile file) throws IOException, MyException {
        //文件上传
        //返回文件上传后的路径
        String imgUrl=fileUrl;
        if(file!=null){
            System.out.println("multipartFile = " + file.getName()+"|"+file.getSize());

            String configFile = this.getClass().getResource("/tracker.conf").getFile();
            ClientGlobal.init(configFile);
            TrackerClient trackerClient=new TrackerClient();
            TrackerServer trackerServer=trackerClient.getTrackerServer();
            StorageClient storageClient=new StorageClient(trackerServer,null);
            String filename=    file.getOriginalFilename();
            String extName = FilenameUtils.getExtension(filename);

            String[] upload_file = storageClient.upload_file(file.getBytes(), extName, null);
            imgUrl=fileUrl ;
            for (int i = 0; i < upload_file.length; i++) {
                String path = upload_file[i];
                imgUrl+="/"+path;
            }
        }
        System.out.println(imgUrl);
        return imgUrl;
    }
    @ApiOperation("销售属性接口")
    @PostMapping("/baseSaleAttrList")
    public List<PmsBaseSaleAttr> baseSaleAttrList(){
        List<PmsBaseSaleAttr> saleAttrList = spuService.baseSaleAttrList();
        return saleAttrList;
    }
    @ApiOperation("保存商品接口")
    @PostMapping("/saveSpuInfo")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "商品id",required = false),
            @ApiImplicitParam(name = "spuName",value = "商品名称",required = true),
            @ApiImplicitParam(name = "description",value = "商品描述(后台简述）",required = true),
            @ApiImplicitParam(name = "catalog3Id",value = "三级分类id",required = true),
            @ApiImplicitParam(name = "tmId",value = "品牌id",required = true),
            @ApiImplicitParam(name = "spuImageList",value = "图片列表",required = true),
            @ApiImplicitParam(name = "spuSaleAttrList",value = "销售属性集合",required = true),
    })
    public String saveSpuInfo(@RequestBody PmsProductInfo pmsProductInfo){
        //保存数据库
        Integer integer = spuService.saveSpuInfo(pmsProductInfo);
        return integer>0?"success":"fail";
    }
    @ApiOperation("销售属性接口")
    @GetMapping("/spuSaleAttrList")
    @ApiImplicitParam(name = "spuId",value = "商品id",required = true)
    public List<PmsProductSaleAttr> spuSaleAttrList(Long spuId){
        List<PmsProductSaleAttr> pmsProductSaleAttrList=spuService.spuSaleAttrList(spuId);
        return pmsProductSaleAttrList;
    }
    @ApiOperation("商品图片接口")
    @GetMapping("/spuImageList")
    @ApiImplicitParam(name = "spuId",value = "商品id",required = true)
    public List<PmsProductImage> spuImageList(Long spuId){
        List<PmsProductImage> pmsProductImageList = spuService.spuImageList(spuId);
        return pmsProductImageList;
    }
}
