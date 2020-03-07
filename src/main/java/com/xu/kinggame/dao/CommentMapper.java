package com.xu.kinggame.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.xu.kinggame.entity.BlogComment;
import com.xu.kinggame.util.PageQueryUtil;

@Component
public interface CommentMapper {

	int addComment(BlogComment newsComment);

	List<BlogComment> selectComment(PageQueryUtil pageQueryUtil);

	int selectCommentCount(PageQueryUtil pageQueryUtil);

	int deleteComment(Integer[] ids);

	int checkComment(Integer[] ids);

	BlogComment selectByPrimaryKey(Long commentId);

	int updateByPrimaryKeySelective(BlogComment blogComment);

}
