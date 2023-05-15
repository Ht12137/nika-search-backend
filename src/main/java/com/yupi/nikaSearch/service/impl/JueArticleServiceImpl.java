package com.yupi.nikaSearch.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.nikaSearch.common.ErrorCode;
import com.yupi.nikaSearch.exception.BusinessException;
import com.yupi.nikaSearch.model.entity.JueArticle;
import com.yupi.nikaSearch.model.entity.Picture;
import com.yupi.nikaSearch.service.JueArticleService;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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
public class JueArticleServiceImpl implements JueArticleService {

    @Override
    public Page<JueArticle> searchArticle(String searchText, long pageNum, long pageSize) {
        String url = "https://api.juejin.cn/search_api/v1/search?aid=2608&uuid=7159825932965430817&spider=0";
        String body = "{\"key_word\":\"+"+searchText+"\",\"id_type\":0,\"cursor\":\"0\",\"limit\":20,\"search_type\":0,\"sort_type\":0,\"version\":1,\"uuid\":\"7159825932965430817\",\"ab_info\":\"{\\\"ab_juejin_search\\\":{\\\"val\\\":{\\\"search_range\\\":1},\\\"vid\\\":\\\"90081172\\\"},\\\"login_guide\\\":{\\\"val\\\":{\\\"login_type\\\":\\\"all\\\"},\\\"vid\\\":\\\"90083871\\\"},\\\"new_card\\\":{\\\"val\\\":false,\\\"vid\\\":\\\"90080875\\\"},\\\"new_lead_way\\\":{\\\"val\\\":1,\\\"vid\\\":\\\"90081279\\\"},\\\"showPeoplePin\\\":{\\\"val\\\":true,\\\"vid\\\":\\\"90081874\\\"},\\\"showSignEntrance\\\":{\\\"val\\\":true,\\\"vid\\\":\\\"90081183\\\"}}\"}";

        String result = HttpRequest.post(url)
                .body(body)
                .header("x-secsdk-csrf-token","000100000001920e2a283d739714e054cc86d6c09a4df0adf0d8c960b30c53fe380522a563b91754bd469c919593")
                .header("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36")
                .header("origin","https://juejin.cn")
                .cookie("__tea_cookie_tokens_2608=%257B%2522web_id%2522%253A%25227159825932965430817%2522%252C%2522user_unique_id%2522%253A%25227159825932965430817%2522%252C%2522timestamp%2522%253A1667026765941%257D; _ga=GA1.2.1001669494.1667026767; _tea_utm_cache_2608={%22utm_source%22:%22creator%22%2C%22utm_medium%22:%22banner%22%2C%22utm_campaign%22:%22xiaoce_%22}; passport_csrf_token=3c7888d66f6d7d406aa23ae1ccba3c47; passport_csrf_token_default=3c7888d66f6d7d406aa23ae1ccba3c47; csrf_session_id=e6909fb751d1505ff81306356eb05424")
                .execute()
                .body();
        Map<String,Object> map = JSONUtil.toBean(result, Map.class);
        JSONArray data =(JSONArray) map.get("data");

        List<JueArticle> jueArticles = new ArrayList<>();

        for (Object article : data) {
            JueArticle jueArticle = new JueArticle();
            JSONObject articleObj = (JSONObject) article;
            JSONObject articleTemp = articleObj.getJSONObject("result_model").getJSONObject("article_info");
            if(articleTemp == null){
                continue;
            }
            String title = (String)articleTemp.getObj("title","默认标题");
            String brief = (String) articleTemp.getObj("brief_content","默认简介");
            jueArticle.setBrief(brief);
            jueArticle.setTitle(title);
            String articleId = articleTemp.getStr("article_id");
            if(StringUtils.isBlank(articleId)){
                jueArticles.add(jueArticle);
                continue;
            }
            jueArticle.setId(articleId);
            String articleUrl = String.format("https://juejin.cn/post/%s",articleId);
            jueArticle.setLink(articleUrl);
            jueArticles.add(jueArticle);
            if(jueArticles.size() >= pageSize){
                break;
            }
        }
        Page<JueArticle> jueArticlePage = new Page<>(pageNum, pageSize);
        jueArticlePage.setRecords(jueArticles);
        return jueArticlePage;
    }

    @Override
    public String getArticleById(String id) {
        String articleUrl = String.format("https://juejin.cn/post/%s",id);
        Document doc = null;
        try {
            doc = Jsoup.connect(articleUrl).get();
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        Elements elements = doc.select(".article");
        return elements.get(0).html();
    }
}
