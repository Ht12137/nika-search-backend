package com.yupi.nikaSearch.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.nikaSearch.model.entity.CsdnArticle;
import com.yupi.nikaSearch.model.entity.TencentArticle;
import com.yupi.nikaSearch.service.CsdnArticleService;
import com.yupi.nikaSearch.service.TencentArticleService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class TencentArticleDatasource implements DataSource<TencentArticle>{

    @Resource
    private TencentArticleService tencentArticleService;


    @Override
    public Page<TencentArticle> search(String searchText, long current, long pageSize) {
        return tencentArticleService.searchTencentArticle(searchText,current,pageSize);
    }
}
