package com.yupi.nikaSearch.model.vo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yupi.nikaSearch.model.entity.Post;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 帖子视图
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Data
public class SearchVO implements Serializable {

    private List<Object> dataList;


    private static final long serialVersionUID = 1L;

}
