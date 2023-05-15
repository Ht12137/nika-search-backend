package com.yupi.nikaSearch.model.dto.user;

import com.yupi.nikaSearch.common.PageRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户创建请求
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Data
public class SearchQueryRequest extends PageRequest implements Serializable {


    private String type;

    private String searchText;

    private static final long serialVersionUID = 1L;
}