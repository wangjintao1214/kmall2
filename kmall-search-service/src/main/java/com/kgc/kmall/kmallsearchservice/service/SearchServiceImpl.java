package com.kgc.kmall.kmallsearchservice.service;

import com.kgc.kmall.bean.PmsSearchSkuInfo;
import com.kgc.kmall.bean.PmsSearchSkuParam;
import com.kgc.kmall.service.SearchService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.apache.dubbo.config.annotation.Service;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shkstart
 * @create 2021-01-08 14:48
 */
@Component
@Service
public class SearchServiceImpl implements SearchService{
    @Resource
    JestClient jestClient;

    @Override
    public List<PmsSearchSkuInfo> list(PmsSearchSkuParam pmsSearchSkuParam) {
        List<PmsSearchSkuInfo> pmsSearchSkuInfos=new ArrayList<>();
        String catalog3Id = pmsSearchSkuParam.getCatalog3Id();
        String keyword = pmsSearchSkuParam.getKeyword();
        String[] valueId = pmsSearchSkuParam.getValueId();

        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder=new BoolQueryBuilder();

        if (catalog3Id!=null){
            TermQueryBuilder termQueryBuilder=new TermQueryBuilder("catalog3Id",catalog3Id);
            boolQueryBuilder.filter(termQueryBuilder);
        }

        if (valueId!=null){
            for (String pmsSkuAttrValue : valueId) {
                TermQueryBuilder termQueryBuilder=new TermQueryBuilder("skuAttrValueList.valueId",pmsSkuAttrValue);
                boolQueryBuilder.filter(termQueryBuilder);
            }
        }

        if (keyword!=null&&keyword.isEmpty()==false){

            MatchQueryBuilder matchQueryBuilder=new MatchQueryBuilder("skuName",keyword);
            boolQueryBuilder.must(matchQueryBuilder);
        }
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.sort("id", SortOrder.DESC);
        String dsl = searchSourceBuilder.toString();
        Search search=new Search.Builder(dsl).addIndex("kmall").addType("PmsSkuInfo").build();
        try {
            SearchResult searchResult = jestClient.execute(search);
            List<SearchResult.Hit<PmsSearchSkuInfo, Void>> hits = searchResult.getHits(PmsSearchSkuInfo.class);
            for (SearchResult.Hit<PmsSearchSkuInfo, Void> hit : hits) {
                PmsSearchSkuInfo pmsSearchSkuInfo = hit.source;
                pmsSearchSkuInfos.add(pmsSearchSkuInfo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pmsSearchSkuInfos;
    }
    }

