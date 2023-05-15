package com.yupi.nikaSearch.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.nikaSearch.model.entity.TencentArticle;
import com.yupi.nikaSearch.service.TencentArticleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Service
public class TencentArticleServiceImpl implements TencentArticleService {


    @Override
    public Page<TencentArticle> searchTencentArticle(String searchText, long pageNum, long pageSize) {

        String json = "{\"action\":\"SearchList\"," +
                "\"payload\":{\"pageNumber\":1,\"q\":\""+searchText+"\",\"searchTab\":\"article\"}}";
        String url = "https://cloud.tencent.com/developer/services/ajax/search?action=SearchList";
        String result = HttpRequest.post(url)
                .body(json)
                .execute()
                .body();
        Map<String,Object> map = JSONUtil.toBean(result,Map.class);
        JSONObject data = (JSONObject)map.get("data");
        JSONArray list = (JSONArray) data.get("list");

        List<TencentArticle> records = new ArrayList<>();
        for (Object articleTemp : list) {
            JSONObject article = (JSONObject) articleTemp;

            TencentArticle tencentArticle = new TencentArticle();

            String title = article.getStr("title");
            if(StringUtils.isNotBlank(title)){
                tencentArticle.setTitle(title);
            }
            String desc = article.getStr("desc");
            if(StringUtils.isNotBlank(desc)){
                tencentArticle.setBrief(desc);
            }
            String articleId = article.getStr("articleId");
            if(StringUtils.isNotBlank(articleId)){
                tencentArticle.setLink("https://cloud.tencent.com/developer/article/"+articleId);
            }
            records.add(tencentArticle);
            if(records.size() >= pageSize) {
                break;
            }
        }
        Page<TencentArticle> page = new Page<>(pageNum,pageSize);
        page.setRecords(records);
        return page;
    }
}
