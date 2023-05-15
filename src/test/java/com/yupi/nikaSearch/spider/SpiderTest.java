package com.yupi.nikaSearch.spider;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.nikaSearch.common.ErrorCode;
import com.yupi.nikaSearch.exception.BusinessException;
import com.yupi.nikaSearch.model.entity.*;
import com.yupi.nikaSearch.service.PostFavourService;
import com.yupi.nikaSearch.service.PostService;
import com.yupi.nikaSearch.service.UserService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.json.Json;
import java.io.IOException;
import java.util.*;

/**
 * 帖子收藏服务测试
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@SpringBootTest
class SpiderTest {

    @Resource
    private PostService postService;

    private static final User loginUser = new User();

//    @BeforeAll
//    static void setUp() {
//        loginUser.setId(1L);
//    }

    @Resource
    private UserService userService;

    @Test
    void clawTencentArticle() throws IOException {
        String json = "{\"action\":\"SearchList\"," +
                "\"payload\":{\"pageNumber\":1,\"q\":\"设计模式\",\"searchTab\":\"article\"}}";
        String url = "https://cloud.tencent.com/developer/services/ajax/search?action=SearchList";
        String result = HttpRequest.post(url)
                .body(json)
                .execute()
                .body();
        Map<String,Object> map = JSONUtil.toBean(result,Map.class);
        JSONObject data = (JSONObject)map.get("data");
        JSONArray list = (JSONArray) data.get("list");
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

            System.out.println(tencentArticle);
        }

    }


    @Test
    void clawCsdnArticle() throws IOException {
        String url = "https://so.csdn.net/api/v3/search?q=java&t=all&p=1&s=0&tm=0&lv=-1&ft=0&l=&u=&ct=-1&pnt=-1&ry=-1&ss=-1&dct=-1&vco=-1&cc=-1&sc=-1&akt=-1&art=-1&ca=-1&prs=&pre=&ecc=-1&ebc=-1&ia=1&dId=&cl=-1&scl=-1&tcl=-1&platform=pc&ab_test_code_overlap=&ab_test_random_code=";
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
        }
        System.out.println(JSONUtil.toJsonStr(csdnArticles));
    }

    @Test
    void clawJuejinArticle() throws IOException {
        String url = "https://api.juejin.cn/search_api/v1/search?aid=2608&uuid=7159825932965430817&spider=0";
        String body = "{\"key_word\":\"设计模式\",\"id_type\":0,\"cursor\":\"0\",\"limit\":20,\"search_type\":0,\"sort_type\":0,\"version\":1,\"uuid\":\"7159825932965430817\",\"ab_info\":\"{\\\"ab_juejin_search\\\":{\\\"val\\\":{\\\"search_range\\\":1},\\\"vid\\\":\\\"90081172\\\"},\\\"login_guide\\\":{\\\"val\\\":{\\\"login_type\\\":\\\"all\\\"},\\\"vid\\\":\\\"90083871\\\"},\\\"new_card\\\":{\\\"val\\\":false,\\\"vid\\\":\\\"90080875\\\"},\\\"new_lead_way\\\":{\\\"val\\\":1,\\\"vid\\\":\\\"90081279\\\"},\\\"showPeoplePin\\\":{\\\"val\\\":true,\\\"vid\\\":\\\"90081874\\\"},\\\"showSignEntrance\\\":{\\\"val\\\":true,\\\"vid\\\":\\\"90081183\\\"}}\"}";

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

        for (Object article : data) {
            JueArticle jueArticle = new JueArticle();
            JSONObject articleObj = (JSONObject) article;
            JSONObject articleTemp = articleObj.getJSONObject("result_model").getJSONObject("article_info");
            if(articleTemp == null){
                continue;
            }
            String title = (String)articleTemp.getObj("title","默认标题");
            String brief = (String) articleTemp.getObj("brief_content","默认简介");
            System.out.println(title);
            System.out.println(brief);
            String articleId = articleTemp.getStr("article_id");
            if(StringUtils.isBlank(articleId)){
                continue;
            }
            String articleUrl = String.format("https://juejin.cn/post/%s",articleId);
            Document doc = Jsoup.connect(articleUrl).get();
            Elements elements = doc.select(".article");
            System.out.println(elements.get(0));
        }

    }


    @Test
    void clawPic() throws IOException {

        int current = 1;
        String url = String.format("https://cn.bing.com/images/search?q=简自豪&qs=n&form=QBIR&sp=-1&lq=0&pq=简自豪AA&sc=10-3&cvid=F867EB40F223490ABEB2481CA59CEB66&ghsh=0&ghacc=0&first=%s",current);
        Document doc = Jsoup.connect(url).get();
        List<Picture> pictures = new ArrayList<>();
        Elements elements = doc.select(".iuscp.isv");
        for (Element element : elements) {
            User user = new User();
            Elements select = element.select(".iusc");
            String m = select.get(0).attr("m");
            Map<String,String> mAttributes = (Map<String,String>)JSONUtil.toBean(m,Map.class);
            String murl = mAttributes.get("murl");
            String t = mAttributes.get("t");
            Picture picture = new Picture();
            picture.setUrl(murl);
            picture.setTitle(t);
            pictures.add(picture);
        }

    }


    @Test
    void clawNew() {
        String json = "{\n" +
                "    \"sortField\": \"createTime\",\n" +
                "    \"sortOrder\": \"descend\",\n" +
                "    \"reviewStatus\": 1,\n" +
                "    \"current\": 1\n" +
                "}";
        String url = "https://www.code-nav.cn/api/post/list/page/vo";
        String result = HttpRequest.post(url)
                .body(json)
                .execute()
                .body();

        Map<String, Object> map = (Map<String, Object>)JSONUtil.toBean(result, Map.class);
        JSONObject data = (JSONObject)map.get("data");
        JSONArray records = (JSONArray)data.get("records");
        ArrayList<Post> posts = new ArrayList<>();
        for (Object record : records) {
            JSONObject tempRecord = (JSONObject) record;
            Post post = new Post();
            post.setContent(tempRecord.getStr("content"));
            post.setTitle(tempRecord.getStr("title"));
            post.setUserId(1L);
            posts.add(post);
        }
        postService.saveBatch(posts);

    }

    @Test
    void clawHot() {
        String json = "{\n" +
                "    \"sortField\": \"thumbNum\",\n" +
                "    \"sortOrder\": \"descend\",\n" +
                "    \"reviewStatus\": 1,\n" +
                "    \"current\": 2\n" +
                "}";
        String url = "https://www.code-nav.cn/api/post/list/page/vo";
        String result = HttpRequest.post(url)
                .body(json)
                .execute()
                .body();

        Map<String, Object> map = (Map<String, Object>)JSONUtil.toBean(result, Map.class);
        JSONObject data = (JSONObject)map.get("data");
        JSONArray records = (JSONArray)data.get("records");
        ArrayList<Post> posts = new ArrayList<>();
        for (Object record : records) {
            JSONObject tempRecord = (JSONObject) record;
            Post post = new Post();
            post.setContent(tempRecord.getStr("content"));
            post.setTitle(tempRecord.getStr("title"));
            post.setUserId(1L);
            posts.add(post);
        }
        postService.saveBatch(posts);

    }

    @Test
    void clawJing() {
        String json = "{\n" +
                "    \"sortField\": \"thumbNum\",\n" +
                "    \"sortOrder\": \"descend\",\n" +
                "    \"reviewStatus\": 1,\n" +
                "    \"current\": 2\n" +
                "}";
        String url = "https://www.code-nav.cn/api/post/list/page/vo";
        String result = HttpRequest.post(url)
                .body(json)
                .execute()
                .body();

        Map<String, Object> map = (Map<String, Object>)JSONUtil.toBean(result, Map.class);
        JSONObject data = (JSONObject)map.get("data");
        JSONArray records = (JSONArray)data.get("records");
        ArrayList<Post> posts = new ArrayList<>();
        for (Object record : records) {
            JSONObject tempRecord = (JSONObject) record;
            Post post = new Post();
            post.setContent(tempRecord.getStr("content"));
            post.setTitle(tempRecord.getStr("title"));
            post.setUserId(1L);
            posts.add(post);
        }
        postService.saveBatch(posts);

    }

}
