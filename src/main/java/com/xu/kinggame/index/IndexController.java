/*package com.xu.kinggame.index;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xu.kinggame.entity.News;
import com.xu.kinggame.entity.NewsComment;
import com.xu.kinggame.service.CommentService;
import com.xu.kinggame.service.NewsService;
import com.xu.kinggame.util.AntiXssUtils;
import com.xu.kinggame.util.Result;
import com.xu.kinggame.util.ResultGenerator;

@Controller
public class IndexController {

	@Resource
	private NewsService newsService;
	@Resource
	private CommentService commentService;
	@GetMapping("/news/{newsId}")
	public String detail(HttpServletRequest request,@PathVariable("newsId") Long newsId) {
		News newsDetail = newsService.selectNewsById(newsId);
		if(newsDetail!=null) {
			request.setAttribute("newsDetail", newsDetail);
		}
		request.setAttribute("pageName", "详情");
        return "index/detail";
	}
	
	@PostMapping("/news/comment")
	@ResponseBody
	public Result comment(HttpServletRequest request,HttpSession session,@RequestParam Long newsId, @RequestParam String verifyCode,
            @RequestParam String commentator, @RequestParam String commentBody) {
		if(StringUtils.isEmpty(verifyCode)) {
			return ResultGenerator.genFailResult("请输入验证码");
		}
		String kaptchaCode = session.getAttribute("verifyCode") + "";
        if (StringUtils.isEmpty(kaptchaCode)) {
            return ResultGenerator.genFailResult("非法请求");
        }
        if(!verifyCode.equals(kaptchaCode)) {
        	return ResultGenerator.genFailResult("验证码错误");
        }
        
        String ref = request.getHeader("Referer");
        if (StringUtils.isEmpty(ref)) {
            return ResultGenerator.genFailResult("非法请求");
        }
        if (null == newsId || newsId < 0) {
            return ResultGenerator.genFailResult("非法请求");
        }
        if (StringUtils.isEmpty(commentator)) {
            return ResultGenerator.genFailResult("请输入称呼");
        }
        if (StringUtils.isEmpty(commentBody)) {
            return ResultGenerator.genFailResult("请输入评论内容");
        }
        if (commentBody.trim().length() > 200) {
            return ResultGenerator.genFailResult("评论内容过长");
        }
        
        NewsComment newsComment = new NewsComment();
        newsComment.setNewId(newsId);
        newsComment.setCommentator(AntiXssUtils.cleanString(commentator));
        newsComment.setCommentBody(AntiXssUtils.cleanString(commentBody));
        return ResultGenerator.genSuccessResult(commentService.addComment(newsComment));
	}
	
	
}
*/