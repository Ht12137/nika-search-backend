package com.yupi.nikaSearch.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.nikaSearch.exception.DataSourceFailException;
import com.yupi.nikaSearch.model.entity.JueArticle;
import com.yupi.nikaSearch.model.entity.Picture;
import com.yupi.nikaSearch.service.JueArticleService;
import com.yupi.nikaSearch.service.PictureService;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class PictureDataSource implements DataSource<Picture>{

    @Resource
    private PictureService pictureService;


    @Override
    @Retryable(value = DataSourceFailException.class, maxAttempts = 3, backoff = @Backoff(delay = 3000L, multiplier = 1.5))
    public Page<Picture> search(String searchText, long current, long pageSize) {

        return pictureService.searchPicture(searchText,current,pageSize);
    }
}
