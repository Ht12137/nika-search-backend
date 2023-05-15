package com.yupi.nikaSearch.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.nikaSearch.common.ErrorCode;
import com.yupi.nikaSearch.exception.BusinessException;
import com.yupi.nikaSearch.exception.DataSourceFailException;
import com.yupi.nikaSearch.model.entity.Picture;
import com.yupi.nikaSearch.model.entity.User;
import com.yupi.nikaSearch.service.PictureService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Service
public class PictureServiceImpl implements PictureService {


    @Override
    public Page<Picture> searchPicture(String searchText, long pageNum, long pageSize){
        long current = pageNum * pageSize;
        String url = String.format("https://cn.bing.com/images/search?q=%s&qs=n&form=QBIR&sp=-1&lq=0&pq=简自豪AA&sc=10-3&cvid=F867EB40F223490ABEB2481CA59CEB66&ghsh=0&ghacc=0&first=%s",searchText,current);
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new DataSourceFailException();
        }
        List<Picture> pictures = new ArrayList<>();
        Elements elements = doc.select(".iuscp.isv");
        for (Element element : elements) {

            User user = new User();
            Elements select = element.select(".iusc");
            String m = select.get(0).attr("m");
            Map<String,String> mAttributes = (Map<String,String>) JSONUtil.toBean(m,Map.class);
            String murl = mAttributes.get("murl");
            String t = mAttributes.get("t");
            Picture picture = new Picture();
            picture.setUrl(murl);
            picture.setTitle(t);
            pictures.add(picture);
            if(pictures.size() >= pageSize){
                break;
            }
        }
        Page<Picture> picturePage = new Page<>(pageNum, pageSize);
        picturePage.setRecords(pictures);
        return picturePage;
    }
}
