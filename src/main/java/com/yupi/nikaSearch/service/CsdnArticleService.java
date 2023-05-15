package com.yupi.nikaSearch.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.nikaSearch.model.entity.CsdnArticle;
import com.yupi.nikaSearch.model.entity.JueArticle;

public interface CsdnArticleService {

    Page<CsdnArticle> searchArticle(String searchText, long pageNum, long pageSize);

}
