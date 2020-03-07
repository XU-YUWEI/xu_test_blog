package com.xu.kinggame.service;

import java.util.List;

import com.xu.kinggame.entity.BlogTagCount;
import com.xu.kinggame.util.PageQueryUtil;
import com.xu.kinggame.util.PageResult;

public interface TagService {

    /**
     * 查询标签的分页数据
     *
     * @param pageUtil
     * @return
     */
    PageResult getBlogTagPage(PageQueryUtil pageUtil);

    Boolean saveTag(String tagName);

    Boolean deleteBatch(Integer[] ids);
    
    List<BlogTagCount> getBlogTagCountForIndex();
    
}
