package com.xu.kinggame.dao;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.xu.kinggame.entity.Blog;
import com.xu.kinggame.util.PageQueryUtil;

import java.util.List;

@Component
public interface BlogMapper {
    int deleteByPrimaryKey(Long blogId);

    int insert(Blog record);

    int insertSelective(Blog record);

    Blog selectByPrimaryKey(Long blogId);

    int updateByPrimaryKeySelective(Blog record);

    int updateByPrimaryKeyWithBLOBs(Blog record);

    int updateByPrimaryKey(Blog record);

	List<Blog> findBlogList(PageQueryUtil pageUtil);

	int getTotalBlogs(PageQueryUtil pageUtil);

	int deleteBatch(Integer[] ids);

	List<Blog> selectBlogListByType(@Param("type") int type, @Param("limit") int limit);

	List<Blog> getBlogsPageByTagId(PageQueryUtil pageQueryUtil);

	int getTotalBlogsByTagId(PageQueryUtil pageQueryUtil);
}