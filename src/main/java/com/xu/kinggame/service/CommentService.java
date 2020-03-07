package com.xu.kinggame.service;

import com.xu.kinggame.entity.BlogComment;
import com.xu.kinggame.util.PageQueryUtil;
import com.xu.kinggame.util.PageResult;

public interface CommentService {

	Boolean addComment(BlogComment comment);

	PageResult selectComment(PageQueryUtil pageQueryUtil);

	boolean deleteComment(Integer[] ids);

	boolean checkComment(Integer[] ids);

	boolean reply(Long commentId, String replyBody);

	PageResult getCommentPageByBlogIdAndPageNum(Long blogId, Integer commentPage);

}
