package com.yupi.nikaSearch.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.nikaSearch.model.entity.CsdnArticle;
import com.yupi.nikaSearch.model.entity.JueArticle;
import com.yupi.nikaSearch.service.CsdnArticleService;
import com.yupi.nikaSearch.service.JueArticleService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class CsdnArticleDatasource implements DataSource<CsdnArticle>{

    @Resource
    private CsdnArticleService csdnArticleService;


    @Override
    public Page<CsdnArticle> search(String searchText, long current, long pageSize) {
        return csdnArticleService.searchArticle(searchText,current,pageSize);
    }
}
