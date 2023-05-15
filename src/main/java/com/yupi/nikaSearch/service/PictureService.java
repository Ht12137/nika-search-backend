package com.yupi.nikaSearch.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.nikaSearch.model.entity.Picture;

import java.util.List;

public interface PictureService {

    Page<Picture> searchPicture(String searchText, long pageNum, long pageSize);
}
