package com.yupi.nikaSearch.controller;

import cn.hutool.http.HttpRequest;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.nikaSearch.annotation.AuthCheck;
import com.yupi.nikaSearch.common.BaseResponse;
import com.yupi.nikaSearch.common.DeleteRequest;
import com.yupi.nikaSearch.common.ErrorCode;
import com.yupi.nikaSearch.common.ResultUtils;
import com.yupi.nikaSearch.config.WxOpenConfig;
import com.yupi.nikaSearch.constant.UserConstant;
import com.yupi.nikaSearch.exception.BusinessException;
import com.yupi.nikaSearch.exception.ThrowUtils;
import com.yupi.nikaSearch.model.dto.user.*;
import com.yupi.nikaSearch.model.entity.User;
import com.yupi.nikaSearch.model.vo.LoginUserVO;
import com.yupi.nikaSearch.model.vo.UserVO;
import com.yupi.nikaSearch.service.UserService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * test
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {


    // region 登录相关

    @GetMapping("/testProxy")
    public BaseResponse<String> userRegister() {
        String result = HttpRequest.get("https://github.com/")
                .setHttpProxy("virmach.swsk33-mcs.top",7890)
                .execute()
                .body()
                ;

        return ResultUtils.success(result);
    }


}
