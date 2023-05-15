package com.yupi.nikaSearch.esdao;

import com.yupi.nikaSearch.model.dto.article.ArticleEsDTO;
import com.yupi.nikaSearch.model.dto.post.PostEsDTO;
import com.yupi.nikaSearch.model.dto.post.PostQueryRequest;
import com.yupi.nikaSearch.model.entity.Post;
import com.yupi.nikaSearch.service.PostService;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * 帖子 ES 操作测试
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@SpringBootTest
public class PostEsDaoTest {

    @Resource
    private PostEsDao postEsDao;

    @Resource
    private PostService postService;

    @Resource
    private ArticleEsDao articleEsDao;

    @Test
    void test() {
        PostQueryRequest postQueryRequest = new PostQueryRequest();
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<Post> page =
                postService.searchFromEs(postQueryRequest);
        System.out.println(page);
    }

    @Test
    void testSelect() {
        System.out.println(postEsDao.count());
        Page<PostEsDTO> PostPage = postEsDao.findAll(
                PageRequest.of(0, 5, Sort.by("createTime")));
        List<PostEsDTO> postList = PostPage.getContent();
        System.out.println(postList);
    }

    @Test
    void testAdd() {
        ArticleEsDTO articleEsDTO = new ArticleEsDTO();
        articleEsDTO.setTitle("nika");
        articleEsDTO.setContent("nika是南信大的学生");
        articleEsDTO.setUserId(0L);
        articleEsDTO.setCreateTime(new Date());
        articleEsDTO.setUpdateTime(new Date());
        articleEsDTO.setIsDelete(0);
        articleEsDao.save(articleEsDTO);
        System.out.println(articleEsDTO);
    }

    @Test
    void testFindById() {
        Optional<PostEsDTO> postEsDTO = postEsDao.findById(1L);
        System.out.println(postEsDTO);
    }

    @Test
    void testCount() {
        System.out.println(postEsDao.count());
    }

    @Test
    void testFindByCategory() {
        List<PostEsDTO> postEsDaoTestList = postEsDao.findByUserId(1L);
        System.out.println(postEsDaoTestList);
    }
}
