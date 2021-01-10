package com.kgc.kmall.service;

import com.kgc.kmall.bean.OmsCartItem;
import com.kgc.kmall.bean.PmsSearchSkuInfo;
import com.kgc.kmall.bean.PmsSearchSkuParam;

import java.util.List;

/**
 * @author shkstart
 * @create 2021-01-08 14:47
 */
public interface SearchService {
    List<PmsSearchSkuInfo> list(PmsSearchSkuParam pmsSearchSkuParam);
}
