package com.kgc.kmall.service;

import com.kgc.kmall.bean.PmsBaseSaleAttr;
import com.kgc.kmall.bean.PmsProductImage;
import com.kgc.kmall.bean.PmsProductInfo;
import com.kgc.kmall.bean.PmsProductSaleAttr;

import java.util.List;

/**
 * @author shkstart
 * @create 2020-12-17 11:45
 */
public interface SpuService {
    List<PmsProductInfo> selectAllBycatalog3Id(Long catalog3Id);
    List<PmsBaseSaleAttr> baseSaleAttrList();
    Integer saveSpuInfo(PmsProductInfo pmsProductInfo);
    List<PmsProductSaleAttr> spuSaleAttrList(Long spuId);
    List<PmsProductImage> spuImageList(Long spuId);
    List<PmsProductSaleAttr> spuSaleAttrListIsCheck(Long spuId, Long skuId);
}
