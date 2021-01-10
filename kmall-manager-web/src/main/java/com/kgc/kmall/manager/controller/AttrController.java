package com.kgc.kmall.manager.controller;

import com.kgc.kmall.bean.PmsBaseAttrInfo;
import com.kgc.kmall.bean.PmsBaseAttrValue;
import com.kgc.kmall.service.AttrService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author shkstart
 * @create 2020-12-16 17:08
 */
/*跨域*/
@CrossOrigin
@RestController
@Api(tags = "平台属性接口",description = "提供平台属性相关的Rest API")
public class AttrController {

    @Reference
    AttrService attrService;

    @ApiOperation("属性接口")
    @GetMapping("/attrInfoList")
    @ApiImplicitParam(name = "catalog3Id",value = "三级分类id",required = true)
    public List<PmsBaseAttrInfo> attrInfoList(Integer catalog3Id){
        List<PmsBaseAttrInfo> pmsBaseAttrInfos = attrService.selectAll(catalog3Id);
        for (PmsBaseAttrInfo pmsBaseAttrInfo : pmsBaseAttrInfos) {
            System.out.println(pmsBaseAttrInfo.toString());
        }
        return pmsBaseAttrInfos;
    }
    @ApiOperation("保存属性接口")
    @PostMapping("/saveAttrInfo")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "编号",required = false),
            @ApiImplicitParam(name = "attrName",value = "属性名称",required = true),
            @ApiImplicitParam(name = "catalog3Id",value = "三级分类id",required = true),
            @ApiImplicitParam(name = "isEnabled",value = "启用：1 停用：0",required = true),
            @ApiImplicitParam(name = "attrValueList",value = "属性值",required = true),
    })
    public Integer saveAttrInfo(@RequestBody PmsBaseAttrInfo pmsBaseAttrInfo){
        int i = attrService.add(pmsBaseAttrInfo);
        return i;
    }
    @ApiOperation("获取属性值接口")
    @PostMapping("getAttrValueList")
    @ApiImplicitParam(name = "attrId",value = "属性id",required = true)
    public List<PmsBaseAttrValue> getAttrValueList(Long attrId){
        List<PmsBaseAttrValue> pmsBaseAttrValues = attrService.seleByattrId(attrId);
        for (PmsBaseAttrValue pmsBaseAttrValue : pmsBaseAttrValues) {
            System.out.println(pmsBaseAttrValue.toString());
        }
        return pmsBaseAttrValues;
    }

}

