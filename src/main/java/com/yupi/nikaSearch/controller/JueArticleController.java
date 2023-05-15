package com.yupi.nikaSearch.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.nikaSearch.common.BaseResponse;
import com.yupi.nikaSearch.common.ErrorCode;
import com.yupi.nikaSearch.common.ResultUtils;
import com.yupi.nikaSearch.exception.ThrowUtils;
import com.yupi.nikaSearch.model.dto.picture.PictureQueryRequest;
import com.yupi.nikaSearch.model.entity.JueArticle;
import com.yupi.nikaSearch.model.entity.Picture;
import com.yupi.nikaSearch.service.JueArticleService;
import com.yupi.nikaSearch.service.PictureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 帖子接口
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@RestController
@RequestMapping("/jueArticle")
@Slf4j
public class JueArticleController {

    @Resource
    private JueArticleService jueArticleService;



    /**
     * 分页搜索（从 ES 查询，封装类）
     *
     * @param pictureQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/search/page/vo")
    public BaseResponse<Page<JueArticle>> searchPictureByPage(@RequestBody PictureQueryRequest pictureQueryRequest,
                                                              HttpServletRequest request) {
        long size = pictureQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 10, ErrorCode.PARAMS_ERROR);
        Page<JueArticle> articles = jueArticleService.searchArticle(pictureQueryRequest.getSearchText(), pictureQueryRequest.getCurrent(), size);
        return ResultUtils.success(articles);
    }



    /**
     * 分页搜索（从 ES 查询，封装类）
     *
     * @param pictureQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/get")
    public BaseResponse<String> searchPictureByPage(@RequestParam("articleId") String articleId) {
        String content = jueArticleService.getArticleById(articleId);
        return ResultUtils.success(content);
    }



}
