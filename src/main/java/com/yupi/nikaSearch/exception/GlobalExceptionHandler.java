package com.yupi.nikaSearch.exception;

import com.yupi.nikaSearch.common.BaseResponse;
import com.yupi.nikaSearch.common.ErrorCode;
import com.yupi.nikaSearch.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public BaseResponse<?> businessExceptionHandler(BusinessException e) {
        log.error("BusinessException", e);
        return ResultUtils.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<?> runtimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException", e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, "系统错误");
    }
    @ExceptionHandler(DataSourceFailException.class)
    public BaseResponse<?> dataSourceExceptionHandler(DataSourceFailException e) {
        log.error("DataSourceFailException", e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, "获取数据源接口失败");
    }
}
