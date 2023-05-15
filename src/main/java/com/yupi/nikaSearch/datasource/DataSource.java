package com.yupi.nikaSearch.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.nikaSearch.exception.DataSourceFailException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

import java.util.List;

public interface DataSource<T> {
    Page<T> search(String searchText, long current, long pageSize);

}
