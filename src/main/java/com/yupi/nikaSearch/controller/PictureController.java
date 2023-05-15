package com.yupi.nikaSearch.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.yupi.nikaSearch.annotation.AuthCheck;
import com.yupi.nikaSearch.common.BaseResponse;
import com.yupi.nikaSearch.common.DeleteRequest;
import com.yupi.nikaSearch.common.ErrorCode;
import com.yupi.nikaSearch.common.ResultUtils;
import com.yupi.nikaSearch.constant.UserConstant;
import com.yupi.nikaSearch.exception.BusinessException;
import com.yupi.nikaSearch.exception.ThrowUtils;
import com.yupi.nikaSearch.model.dto.picture.PictureQueryRequest;
import com.yupi.nikaSearch.model.dto.post.PostAddRequest;
import com.yupi.nikaSearch.model.dto.post.PostEditRequest;
import com.yupi.nikaSearch.model.dto.post.PostQueryRequest;
import com.yupi.nikaSearch.model.dto.post.PostUpdateRequest;
import com.yupi.nikaSearch.model.entity.Picture;
import com.yupi.nikaSearch.model.entity.Post;
import com.yupi.nikaSearch.model.entity.User;
import com.yupi.nikaSearch.model.vo.PostVO;
import com.yupi.nikaSearch.service.PictureService;
import com.yupi.nikaSearch.service.PostService;
import com.yupi.nikaSearch.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 帖子接口
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@RestController
@RequestMapping("/picture")
@Slf4j
public class PictureController {

    @Resource
    private PictureService pictureService;



    /**
     * 分页搜索（从 ES 查询，封装类）
     *
     * @param pictureQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/search/page/vo")
    public BaseResponse<Page<Picture>> searchPictureByPage(@RequestBody PictureQueryRequest pictureQueryRequest,
                                                          HttpServletRequest request) {
        long size = pictureQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<Picture> pictures = pictureService.searchPicture(pictureQueryRequest.getSearchText(), pictureQueryRequest.getCurrent(), size);
        return ResultUtils.success(pictures);
    }


}
