package com.yupi.nikaSearch.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.nikaSearch.model.enums.DataSourceTypeEnum;
import com.yupi.nikaSearch.service.JueArticleService;
import com.yupi.nikaSearch.service.PictureService;
import com.yupi.nikaSearch.service.TencentArticleService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component
public class DataSourceRegistry {

    @Resource
    private ArticleDataSource articleDataSource;

    @Resource
    private PictureDataSource pictureDataSource;

    @Resource
    private JueArticleDatasource jueArticleDatasource;


    @Resource
    private CsdnArticleDatasource csdnArticleDatasource;

    @Resource
    private TencentArticleDatasource tencentArticleDatasource;


    private Map<DataSourceTypeEnum,DataSource> dataSourceMap;


    @PostConstruct
    public void initDataSourceMap(){
        dataSourceMap = new HashMap<>();
        dataSourceMap.put(DataSourceTypeEnum.Article,articleDataSource);
        dataSourceMap.put(DataSourceTypeEnum.JueArticle,jueArticleDatasource);
        dataSourceMap.put(DataSourceTypeEnum.Picture,pictureDataSource);
        dataSourceMap.put(DataSourceTypeEnum.CsdnArticle,csdnArticleDatasource);
        dataSourceMap.put(DataSourceTypeEnum.TencentArticle,tencentArticleDatasource);

    }


    public DataSource getDataSourceByType(String type){
        DataSourceTypeEnum typeEnum = DataSourceTypeEnum.getEnumByValue(type);
        return dataSourceMap.get(typeEnum);
    }
}
