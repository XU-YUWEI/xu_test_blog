package com.xu.kinggame.service.impl;



import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.xu.kinggame.dao.CommentMapper;
import com.xu.kinggame.entity.BlogComment;
import com.xu.kinggame.service.CommentService;
import com.xu.kinggame.util.PageQueryUtil;
import com.xu.kinggame.util.PageResult;

@Service
public class CommentServiceImpl implements CommentService {

	@Resource
	private CommentMapper commentMapper;

	@Override
	public Boolean addComment(BlogComment newsComment) {
		return commentMapper.addComment(newsComment)>0;
	}

	@Override
	public PageResult selectComment(PageQueryUtil pageQueryUtil) {
		List<BlogComment> list = commentMapper.selectComment(pageQueryUtil);
		int count = commentMapper.selectCommentCount(pageQueryUtil);
		PageResult pageResult = new PageResult(list, count, pageQueryUtil.getLimit(), pageQueryUtil.getPage());
		System.out.println(pageResult);
		return pageResult;
	}

	@Override
	public boolean deleteComment(Integer[] ids) {
		return commentMapper.deleteComment(ids)>0;
	}

	@Override
	public boolean checkComment(Integer[] ids) {
		return commentMapper.checkComment(ids)>0;
	}

	@Override
	public boolean reply(Long commentId, String replyBody) {
		BlogComment blogComment = commentMapper.selectByPrimaryKey(commentId);
		if(blogComment!=null&& blogComment.getCommentStatus().intValue()==1) {
			blogComment.setReplyBody(replyBody);
			blogComment.setReplyCreateTime(new Date());
			return commentMapper.updateByPrimaryKeySelective(blogComment)>0;
		}
		return false;
	}

	@Override
	public PageResult getCommentPageByBlogIdAndPageNum(Long blogId, Integer page) {
		if(page<1) {
			return null;
		}
		Map params = new HashMap();
		params.put("page", page);
        //每页8条
        params.put("limit", 8);
        params.put("blogId", blogId);//过滤当前博客下的评论数据
        params.put("commentStatus", 1);//过滤审核通过的数据
        PageQueryUtil pageQueryUtil = new PageQueryUtil(params);
        List<BlogComment> list = commentMapper.selectComment(pageQueryUtil);
        if (!CollectionUtils.isEmpty(list)) {
        int totalCount = commentMapper.selectCommentCount(pageQueryUtil);
        PageResult pageResult = new PageResult(list, totalCount, pageQueryUtil.getLimit(), pageQueryUtil.getPage());
        return pageResult;
        }
        return null;
	}

	
	

}
