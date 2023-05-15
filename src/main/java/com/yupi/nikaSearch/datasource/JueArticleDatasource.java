package com.yupi.nikaSearch.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.nikaSearch.model.entity.CsdnArticle;
import com.yupi.nikaSearch.model.entity.JueArticle;
import com.yupi.nikaSearch.model.vo.UserVO;
import com.yupi.nikaSearch.service.JueArticleService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class JueArticleDatasource implements DataSource<JueArticle>{

    @Resource
    private JueArticleService jueArticleService;


    @Override
    public Page<JueArticle> search(String searchText, long current, long pageSize) {
        return jueArticleService.searchArticle(searchText,current,pageSize);
    }
}
