package com.yupi.nikaSearch.esdao;

import com.yupi.nikaSearch.model.dto.article.ArticleEsDTO;
import com.yupi.nikaSearch.model.dto.post.PostEsDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * 帖子 ES 操作
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
public interface ArticleEsDao extends ElasticsearchRepository<ArticleEsDTO, Long> {

    List<ArticleEsDTO> findByUserId(Long userId);
}