package com.yupi.nikaSearch.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.nikaSearch.model.entity.Picture;
import com.yupi.nikaSearch.model.entity.TencentArticle;

public interface TencentArticleService {

    Page<TencentArticle> searchTencentArticle(String searchText, long pageNum, long pageSize);
}
