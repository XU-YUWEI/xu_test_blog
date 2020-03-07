package com.xu.kinggame.dao;


import org.springframework.stereotype.Component;

import com.xu.kinggame.entity.BlogTag;
import com.xu.kinggame.entity.BlogTagCount;
import com.xu.kinggame.util.PageQueryUtil;

import java.util.List;

@Component
public interface BlogTagMapper {
    int deleteByPrimaryKey(Integer tagId);

    int insert(BlogTag record);

    int insertSelective(BlogTag record);

    BlogTag selectByPrimaryKey(Integer tagId);

    BlogTag selectByTagName(String tagName);

    int updateByPrimaryKeySelective(BlogTag record);

    int updateByPrimaryKey(BlogTag record);

    List<BlogTag> findTagList(PageQueryUtil pageUtil);

    int getTotalTags(PageQueryUtil pageUtil);

    int deleteBatch(Integer[] ids);

	int batchInsertBlogTag(List<BlogTag> tagListForInsert);

	List<BlogTagCount> getTagCount();
}