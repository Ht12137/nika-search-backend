package com.yupi.nikaSearch.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.nikaSearch.model.entity.CsdnArticle;
import com.yupi.nikaSearch.model.entity.JueArticle;
import com.yupi.nikaSearch.model.entity.Picture;

public interface JueArticleService {
    Page<JueArticle> searchArticle(String searchText, long pageNum, long pageSize);
    String getArticleById(String id);
}
