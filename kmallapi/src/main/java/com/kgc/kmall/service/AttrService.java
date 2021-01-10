package com.kgc.kmall.service;

import com.kgc.kmall.bean.PmsBaseAttrInfo;
import com.kgc.kmall.bean.PmsBaseAttrValue;

import java.util.List;
import java.util.Set;

/**
 * @author shkstart
 * @create 2020-12-17 11:07
 */
public interface AttrService {
    List<PmsBaseAttrInfo> selectAll(Integer catalog3Id);
    Integer add(PmsBaseAttrInfo pmsBaseAttrInfo);
    List<PmsBaseAttrValue> seleByattrId(Long attrId);
    List<PmsBaseAttrInfo> selectAttrInfoValueListByValueId(Set<Long> valueIds);


}
