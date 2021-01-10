package com.kgc.kmall.manager.service;

import com.alibaba.fastjson.JSON;
import com.kgc.kmall.bean.*;
import com.kgc.kmall.manager.mapper.PmsSkuAttrValueMapper;
import com.kgc.kmall.manager.mapper.PmsSkuImageMapper;
import com.kgc.kmall.manager.mapper.PmsSkuInfoMapper;
import com.kgc.kmall.manager.mapper.PmsSkuSaleAttrValueMapper;
import com.kgc.kmall.service.SkuService;
import com.kgc.kmall.util.RedisUtil;
import org.apache.dubbo.config.annotation.Service;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.locks.Lock;

/**
 * @author shkstart
 * @create 2020-12-23 18:49
 */
@Component
@Service
public class SkuServiceImpl implements SkuService {
    @Resource
    PmsSkuInfoMapper pmsSkuInfoMapper;
    @Resource
    PmsSkuImageMapper pmsSkuImageMapper;
    @Resource
    PmsSkuAttrValueMapper pmsSkuAttrValueMapper;
    @Resource
    PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper;

   /* @Resource
    RedisUtil redisUtil;*/

    @Resource
    RedissonClient redissonClient;

    RedisUtil redisUtil = new RedisUtil();

    @Override
    public String saveSkuInfo(PmsSkuInfo skuInfo) {
        pmsSkuInfoMapper.insert(skuInfo);
        Long skuInfoId  = skuInfo.getId();
        for (PmsSkuImage pmsSkuImage : skuInfo.getSkuImageList()) {
            pmsSkuImage.setSkuId(skuInfoId);
            pmsSkuImageMapper.insert(pmsSkuImage);
        }
        for (PmsSkuAttrValue pmsSkuAttrValue : skuInfo.getSkuAttrValueList()) {
            pmsSkuAttrValue.setSkuId(skuInfoId);
            pmsSkuAttrValueMapper.insert(pmsSkuAttrValue);
        }
        for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : skuInfo.getSkuSaleAttrValueList()) {
            pmsSkuSaleAttrValue.setSkuId(skuInfoId);
            pmsSkuSaleAttrValueMapper.insert(pmsSkuSaleAttrValue);
        }
        return "success";
    }

    @Override
    public PmsSkuInfo selectBySkuId(Long skuId) {
        /*PmsSkuInfo pmsSkuInfo=null;
        Jedis jedis = redisUtil.getJedis();
        String skuKey= "sku:"+skuId+":info";
        String skuInfoJson = jedis.get(skuKey);
        if(skuInfoJson==null ){
            //使用nx分布式锁，避免缓存击穿
            String skuLockKey="sku:"+skuId+":lock";
            String skuLockValue= UUID.randomUUID().toString();
            //获取分布式锁
            String lock = jedis.set(skuLockKey, skuLockValue, "NX", "PX",60*1000);
            //拿到分布式锁
            if (lock.equals("OK")){
                pmsSkuInfo = pmsSkuInfoMapper.selectByPrimaryKey(skuId);
                //防止缓存穿透，从DB中找不到数据也要缓存，但是缓存时间不要太长
                if (pmsSkuInfo!=null){
                    //保存到redis
                    String skuInfoJsonStr = JSON.toJSONString(pmsSkuInfo);
                    //有效期随机，防止缓存雪崩
                    Random random=new Random();
                    int i = random.nextInt(10);
                    jedis.setex(skuKey,i*60*1000,skuInfoJsonStr);
                }else{
                    jedis.setex(skuKey,5*60*1000, "empty");
                }
               *//* //写完缓存后删除分布式锁,获取锁的值，并对比原来的值
                String skuLockValue2 = jedis.get(skuLockKey);
                if (skuLockValue2!=null&&skuLockValue2.equals(skuLockValue)){
                    //刚刚做完判断，过期
                    jedis.del(skuLockKey);
                }*//*
                String script ="if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
                jedis.eval(script, Collections.singletonList(skuLockKey),Collections.singletonList(skuLockValue));
            }else {
                //如果分布式锁访问失败，线程休眠3秒，重新访问(递归调用)
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return  selectBySkuId(skuId);
            }
        }else if(skuInfoJson.equals("empty")){
            return null;
        }else{
            pmsSkuInfo = JSON.parseObject(skuInfoJson, PmsSkuInfo.class);
        }
        jedis.close();
        return pmsSkuInfo;*/
        redisUtil.initPool("192.168.60.132",6379,0);
        PmsSkuInfo pmsSkuInfo = null;
        Jedis jedis = redisUtil.getJedis();
        String key = "sku:" + skuId + ":info";
        String skuJson = jedis.get(key);
        if (skuJson != null) {
            System.out.println("缓存");
            //缓存中有数据
            pmsSkuInfo = JSON.parseObject(skuJson, PmsSkuInfo.class);
            jedis.close();
            return pmsSkuInfo;
        } else {
            Lock lock = redissonClient.getLock("lock");//声明锁
            lock.lock();//上锁
            try {
                System.out.println("数据库");
                //缓存中无数据，从数据库读取并缓存
                pmsSkuInfo = pmsSkuInfoMapper.selectByPrimaryKey(skuId);

                if (pmsSkuInfo != null) {
                    String json = JSON.toJSONString(pmsSkuInfo);
                    //缓存写入随机有效期，防止缓存雪崩
                    System.out.println("缓存写入有效期防止崩");
                    Random random = new Random();
                    int i = random.nextInt(10);
                    jedis.setex(key, i * 60 * 1000, json);
                } else {
                    //如果数据库和缓存都没有数据就写入缓存中一条数据，设置有效期，防止缓存穿透
                    System.out.println("写入数据库没有的缓存数据");
                    jedis.setex(key, 5 * 60 * 1000, "empty");
                }
                jedis.close();
            } finally {
                lock.unlock();//解锁
            }
        }
        return pmsSkuInfo;
    }


    @Override
    public List<PmsSkuInfo> selectBySpuId(Long spuId) {
        return pmsSkuInfoMapper.selectBySpuId(spuId);
    }

    @Override
    public List<PmsSkuInfo> getAllSku() {
        List<PmsSkuInfo> pmsSkuInfos = pmsSkuInfoMapper.selectByExample(null);
        for (PmsSkuInfo pmsSkuInfo : pmsSkuInfos) {
            PmsSkuAttrValueExample example=new PmsSkuAttrValueExample();
            PmsSkuAttrValueExample.Criteria criteria = example.createCriteria();
            criteria.andSkuIdEqualTo(pmsSkuInfo.getId());
            List<PmsSkuAttrValue> pmsSkuAttrValues = pmsSkuAttrValueMapper.selectByExample(example);
            pmsSkuInfo.setSkuAttrValueList(pmsSkuAttrValues);
        }
        return pmsSkuInfos;
    }
}
