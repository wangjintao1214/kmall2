package com.kgc.kmall.kmallsearchweb.controller;

import com.kgc.kmall.bean.*;
import com.kgc.kmall.service.AttrService;
import com.kgc.kmall.service.SearchService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.mockito.Mock;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

/**
 * @author shkstart
 * @create 2021-01-05 15:58
 */
@Controller
public class SearchController {

    @Reference
    SearchService searchService;

    @Reference
    AttrService attrService;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/list")
    public String list(PmsSearchSkuParam pmsSearchSkuParam, Model model) {
        List<PmsSearchSkuInfo> pmsSearchSkuInfos = searchService.list(pmsSearchSkuParam);
        model.addAttribute("skuLsInfoList", pmsSearchSkuInfos);

        //获取平台属性valueId
        Set<Long> valueIdSet = new HashSet<>();

        for (PmsSearchSkuInfo pmsSearchSkuInfo : pmsSearchSkuInfos) {
            for (PmsSkuAttrValue pmsSkuAttrValue : pmsSearchSkuInfo.getSkuAttrValueList()) {
                valueIdSet.add(pmsSkuAttrValue.getValueId());
            }
        }
        System.out.println(Arrays.toString(valueIdSet.toArray()));
        List<PmsBaseAttrInfo> pmsBaseAttrInfos = attrService.selectAttrInfoValueListByValueId(valueIdSet);

        //已选中的valueId
        String[] valueId = pmsSearchSkuParam.getValueId();
        if (valueId != null) {
            //利用迭代器排除已选的平台属性,删除集合元素不能使用for循环，因为会出现数组越界
            Iterator<PmsBaseAttrInfo> iterator = pmsBaseAttrInfos.iterator();
            while (iterator.hasNext()) {
                PmsBaseAttrInfo pmsBaseAttrInfo = iterator.next();
                for (PmsBaseAttrValue pmsBaseAttrValue : pmsBaseAttrInfo.getAttrValueList()) {
                    for (String s : valueId) {
                        if (s.equals(pmsBaseAttrValue.getId().toString())) {
                            iterator.remove();
                        }
                    }
                }
            }
        }
        model.addAttribute("attrList", pmsBaseAttrInfos);

        //拼接平台属性URL
        String urlParam = getURLParam(pmsSearchSkuParam);
        model.addAttribute("urlParam", urlParam);


        //封装面包屑数据
        if (valueId!=null) {
            List<PmsSearchCrumb> pmsSearchCrumbList = new ArrayList<>();
            for (String s : valueId) {
                PmsSearchCrumb pmsSearchCrumb = new PmsSearchCrumb();
                pmsSearchCrumb.setUrlParam(getURLParam(pmsSearchSkuParam,s));
                pmsSearchCrumb.setValueId(s);
                pmsSearchCrumb.setValueName(s);

                String valueName = getValueName(pmsBaseAttrInfos, s);
                pmsSearchCrumb.setValueName(valueName);

                pmsSearchCrumbList.add(pmsSearchCrumb);
            }
            model.addAttribute("attrValueSelectedList", pmsSearchCrumbList);
        }

        //显示关键字
        model.addAttribute("keyword",pmsSearchSkuParam.getKeyword());
        return "list";
    }

    /**
     * 添加拼接方法
     * @param pmsSearchSkuParam
     * @return
     */
    //根据参数对象拼接URL
    public String getURLParam(PmsSearchSkuParam pmsSearchSkuParam){
        StringBuffer buffer=new StringBuffer();
        String catalog3Id = pmsSearchSkuParam.getCatalog3Id();
        String keyword = pmsSearchSkuParam.getKeyword();
//        List<PmsSkuAttrValue> skuAttrValueList = pmsSearchSkuParam.getSkuAttrValueList();
        String[] valueId = pmsSearchSkuParam.getValueId();
        if (StringUtils.isNotBlank(catalog3Id)){
            buffer.append("&catalog3Id="+catalog3Id);
        }
        if (StringUtils.isNotBlank(keyword)){
            buffer.append("&keyword="+keyword);
        }
        if (valueId!=null){
            for (String pmsSkuAttrValue : valueId) {
                buffer.append("&valueId="+pmsSkuAttrValue);
            }
        }

        return buffer.substring(1);
    }

    /**
     * 根据参数对象拼接URL
     * @param pmsSearchSkuParam
     * @param valueId
     * @return
     */
    public String getURLParam(PmsSearchSkuParam pmsSearchSkuParam,String valueId){
        StringBuffer buffer=new StringBuffer();
        String catalog3Id = pmsSearchSkuParam.getCatalog3Id();
        String keyword = pmsSearchSkuParam.getKeyword();
        String[] valueIds = pmsSearchSkuParam.getValueId();
        if (StringUtils.isNotBlank(catalog3Id)){
            buffer.append("&catalog3Id="+catalog3Id);
        }
        if (StringUtils.isNotBlank(keyword)){
            buffer.append("&keyword="+keyword);
        }
        //面包屑的url是不能包括自身的valueId的，因为点击面包屑以后会从当前URL中去除面包屑的valueId
        if (valueIds!=null){
            for (String pmsSkuAttrValue : valueIds) {
                if (valueId.equals(pmsSkuAttrValue)==false)
                    buffer.append("&valueId="+pmsSkuAttrValue);
            }
        }

        return buffer.substring(1);
    }

    /**
     * 根据valueId查询valueName
     * @param pmsBaseAttrInfos
     * @param valueId
     * @return
     */
    public String getValueName(List<PmsBaseAttrInfo> pmsBaseAttrInfos,String valueId){
        String valueName="";
        for (PmsBaseAttrInfo pmsBaseAttrInfo : pmsBaseAttrInfos) {
            for (PmsBaseAttrValue pmsBaseAttrValue : pmsBaseAttrInfo.getAttrValueList()) {
                if (valueId.equals(pmsBaseAttrValue.getId().toString())){
                    valueName=pmsBaseAttrInfo.getAttrName()+":"+pmsBaseAttrValue.getValueName();
                    return valueName;
                }
            }
        }
        return valueName;
    }
}

