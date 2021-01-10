package com.kgc.kmall.service;

import com.kgc.kmall.bean.PmsBaseCatalog1;
import com.kgc.kmall.bean.PmsBaseCatalog2;
import com.kgc.kmall.bean.PmsBaseCatalog3;

import java.util.List;

/**
 * @author shkstart
 * @create 2020-12-16 16:44
 */
public interface CatalogService {
    List<PmsBaseCatalog1> selectAll();
    List<PmsBaseCatalog2> selectAllBycatalog1Id(Integer catalog1Id);
    List<PmsBaseCatalog3> selectAllBycatalog2Id(Integer catalog2Id);
}
