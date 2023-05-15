package com.yupi.nikaSearch.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.nikaSearch.common.ErrorCode;
import com.yupi.nikaSearch.exception.BusinessException;
import com.yupi.nikaSearch.model.entity.CsdnArticle;
import com.yupi.nikaSearch.model.entity.JueArticle;
import com.yupi.nikaSearch.model.entity.Picture;
import com.yupi.nikaSearch.service.CsdnArticleService;
import com.yupi.nikaSearch.service.JueArticleService;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ht
 */
@Service
public class CsdnArticleServiceImpl implements CsdnArticleService {

    @Override
    public Page<CsdnArticle> searchArticle(String searchText, long pageNum, long pageSize) {

        String url = "https://so.csdn.net/api/v3/search?q="+searchText+"&t=all&p=1&s=0&tm=0&lv=-1&ft=0&l=&u=&ct=-1&pnt=-1&ry=-1&ss=-1&dct=-1&vco=-1&cc=-1&sc=-1&akt=-1&art=-1&ca=-1&prs=&pre=&ecc=-1&ebc=-1&ia=1&dId=&cl=-1&scl=-1&tcl=-1&platform=pc&ab_test_code_overlap=&ab_test_random_code=";
        String result = HttpRequest.get(url).execute().body();
        Map<String,Object> map = JSONUtil.toBean(result, Map.class);
        List<CsdnArticle> csdnArticles = new ArrayList<>();
        JSONArray data =(JSONArray) map.get("result_vos");
        for (Object article : data) {
            CsdnArticle csdnArticle1 = new CsdnArticle();

            JSONObject csdnArticle = (JSONObject)article;
            String title = csdnArticle.getStr("title");
            if(StringUtils.isNotBlank(title)){
                csdnArticle1.setTitle(title);
            }

            String urlLink = csdnArticle.getStr("url");
            if(StringUtils.isNotBlank(urlLink)){
                csdnArticle1.setLink(urlLink);
            }
            String content = csdnArticle.getStr("body");
            if(StringUtils.isNotBlank(content)){
                csdnArticle1.setContent(content);
            }
            String brief = csdnArticle.getStr("digest");
            if(StringUtils.isNotBlank(brief)){
                csdnArticle1.setBrief(brief);
            }
            csdnArticles.add(csdnArticle1);
            if(csdnArticles.size() > pageSize){
                break;
            }
        }
        Page<CsdnArticle> csdnArticlePage = new Page<>(pageNum,pageSize);
        csdnArticlePage.setRecords(csdnArticles);
        return csdnArticlePage;
    }

}
