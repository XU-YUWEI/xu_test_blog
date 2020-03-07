package com.xu.kinggame.dao;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.xu.kinggame.entity.BlogTagRelation;

import java.util.List;

@Component
public interface BlogTagRelationMapper {
    int deleteByPrimaryKey(Long relationId);

    int insert(BlogTagRelation record);

    int insertSelective(BlogTagRelation record);

    BlogTagRelation selectByPrimaryKey(Long relationId);

    List<Long> selectDistinctTagIds(Integer[] tagIds);

    int updateByPrimaryKeySelective(BlogTagRelation record);

    int updateByPrimaryKey(BlogTagRelation record);
    
    int batchInsert(@Param("relationList") List<BlogTagRelation> blogTagRelationList);

	int deleteByBlogId(Long blogId);
}