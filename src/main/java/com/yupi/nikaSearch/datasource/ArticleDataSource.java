package com.yupi.nikaSearch.datasource;
import java.util.ArrayList;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.nikaSearch.common.ErrorCode;
import com.yupi.nikaSearch.common.ResultUtils;
import com.yupi.nikaSearch.exception.ThrowUtils;
import com.yupi.nikaSearch.model.dto.post.PostQueryRequest;
import com.yupi.nikaSearch.model.entity.Picture;
import com.yupi.nikaSearch.model.entity.Post;
import com.yupi.nikaSearch.model.vo.PostVO;
import com.yupi.nikaSearch.service.PictureService;
import com.yupi.nikaSearch.service.PostService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ArticleDataSource implements DataSource<PostVO>{

    @Resource
    private PostService postService;


    @Override
    public Page<PostVO> search(String searchText, long current, long pageSize) {
        PostQueryRequest postQueryRequest = new PostQueryRequest();
        postQueryRequest.setSearchText(searchText);
        Page<Post> postPage = postService.page(new Page<>(current, pageSize),
                postService.getQueryWrapper(postQueryRequest));
        return postService.getPostVOPage(postPage, null);
    }
}
