package com.kgc.kmall.manager.service;

import com.kgc.kmall.bean.PmsBaseAttrInfo;
import com.kgc.kmall.bean.PmsBaseAttrInfoExample;
import com.kgc.kmall.bean.PmsBaseAttrValue;
import com.kgc.kmall.bean.PmsBaseAttrValueExample;
import com.kgc.kmall.manager.mapper.PmsBaseAttrInfoMapper;
import com.kgc.kmall.manager.mapper.PmsBaseAttrValueMapper;
import com.kgc.kmall.service.AttrService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @author shkstart
 * @create 2020-12-17 11:08
 */
@Component
@Service
public class AttrServiceImpl implements AttrService {
    @Resource
    PmsBaseAttrInfoMapper pmsBaseAttrInfoMapper;
    @Resource
    PmsBaseAttrValueMapper pmsBaseAttrValueMapper;
    @Override
    public List<PmsBaseAttrInfo> selectAll(Integer catalog3Id) {
        PmsBaseAttrInfoExample example=new PmsBaseAttrInfoExample();
        PmsBaseAttrInfoExample.Criteria criteria = example.createCriteria();
        if(catalog3Id!=null){
            criteria.andCatalog3IdEqualTo(catalog3Id);
        }
        List<PmsBaseAttrInfo> pmsBaseAttrInfos = pmsBaseAttrInfoMapper.selectByExample(example);
        //为每个平台属性添加属性值
        for (PmsBaseAttrInfo pmsBaseAttrInfo : pmsBaseAttrInfos) {
            PmsBaseAttrValueExample valueExample=new PmsBaseAttrValueExample();
            PmsBaseAttrValueExample.Criteria criteria1 = valueExample.createCriteria();
            criteria1.andAttrIdEqualTo(pmsBaseAttrInfo.getId());
            List<PmsBaseAttrValue> pmsBaseAttrValues = pmsBaseAttrValueMapper.selectByExample(valueExample);
            pmsBaseAttrInfo.setAttrValueList(pmsBaseAttrValues);
        }
        return pmsBaseAttrInfos;
    }

    @Override
    public Integer add(PmsBaseAttrInfo pmsBaseAttrInfo) {
        int i=0;
        //判断是添加还是修改
        if (pmsBaseAttrInfo.getId()==null){
            //添加
            i = pmsBaseAttrInfoMapper.insert(pmsBaseAttrInfo);
        }else{
            //修改
            i=pmsBaseAttrInfoMapper.updateByPrimaryKey(pmsBaseAttrInfo);
        }
        //删除原属性值
        PmsBaseAttrValueExample example=new PmsBaseAttrValueExample();
        PmsBaseAttrValueExample.Criteria criteria = example.createCriteria();
        criteria.andAttrIdEqualTo(pmsBaseAttrInfo.getId());
        i = pmsBaseAttrValueMapper.deleteByExample(example);
        //添加新属性值
        if (pmsBaseAttrInfo.getAttrValueList().size()>0){
            i=pmsBaseAttrValueMapper.insertBatch(pmsBaseAttrInfo.getId(),pmsBaseAttrInfo.getAttrValueList());
        }
        return i;
    }

    @Override
    public List<PmsBaseAttrValue> seleByattrId(Long attrId) {
        PmsBaseAttrValueExample example=new PmsBaseAttrValueExample();
        PmsBaseAttrValueExample.Criteria criteria = example.createCriteria();
        if(attrId!=null){
            criteria.andAttrIdEqualTo(attrId);
        }
        List<PmsBaseAttrValue> pmsBaseAttrValues = pmsBaseAttrValueMapper.selectByExample(example);
        return pmsBaseAttrValues;
    }

    @Override
    public List<PmsBaseAttrInfo> selectAttrInfoValueListByValueId(Set<Long> valueIds) {
        String join = StringUtils.join(valueIds);
        join = join.substring(1, join.length() - 1);
        System.out.println(join);
        List<PmsBaseAttrInfo> pmsBaseAttrInfos = pmsBaseAttrInfoMapper.selectAttrInfoValueListByValueId(join);
        return pmsBaseAttrInfos;
    }
}
