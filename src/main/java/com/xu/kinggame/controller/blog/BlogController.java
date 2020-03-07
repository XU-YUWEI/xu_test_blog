 package com.xu.kinggame.controller.blog;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.commonmark.node.Link;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xu.kinggame.controller.cus.BlogDetailVO;
import com.xu.kinggame.entity.BlogComment;
import com.xu.kinggame.entity.BlogLink;
import com.xu.kinggame.service.CommentService;
import com.xu.kinggame.service.LinkService;
import com.xu.kinggame.service.NewsService;
import com.xu.kinggame.service.TagService;
import com.xu.kinggame.util.MyBlogUtils;
import com.xu.kinggame.util.PageResult;
import com.xu.kinggame.util.PatternUtil;
import com.xu.kinggame.util.Result;
import com.xu.kinggame.util.ResultGenerator;

@Controller
public class BlogController {
	
	@Resource
	private NewsService newsService;
	
	@Resource
	private TagService tagService;
	
	@Resource
	private CommentService commentService;
	
	@Resource
	private LinkService linkService;
	
	@GetMapping({"/", "/index", "index.html"})
    public String index(HttpServletRequest request) {
        return this.page(request, 1);
    }
	

	@GetMapping({"/page/{pageNum}"})
    public String page(HttpServletRequest request, @PathVariable("pageNum") int pageNum) {
        PageResult blogPageResult = newsService.getBlogsForIndexPage(pageNum);
        if (blogPageResult == null) {
            return "error/error_404";
        }
        request.setAttribute("blogPageResult", blogPageResult);
        request.setAttribute("newBlogs", newsService.getBlogListForIndexPage(1));
        request.setAttribute("hotBlogs", newsService.getBlogListForIndexPage(0));
        request.setAttribute("hotTags", tagService.getBlogTagCountForIndex());
        request.setAttribute("pageName", "首页");
        return "blog/index";
    }
	
	@GetMapping("/search/{keyword}")
	public String search(HttpServletRequest request,@PathVariable("keyword") String keyword) {
		return search(request,keyword,1);
	}
	
	@GetMapping("/search/{keyword}/{page}")
	public String search(HttpServletRequest request,@PathVariable("keyword") String keyword,@PathVariable("page") Integer page) {
		PageResult pageResult = newsService.getBlogsPageBySearch(keyword, page);
		request.setAttribute("blogPageResult", pageResult);
        request.setAttribute("pageName", "搜索");
        request.setAttribute("pageUrl", "search");
        request.setAttribute("keyword", keyword);
        return "blog/list";
	}
	
	@GetMapping("/category/{categoryName}")
	public String category(HttpServletRequest request,@PathVariable("categoryName") String categoryName) {
		return category(request,categoryName,1);
	}
	
	@GetMapping("/category/{categoryName}/{page}")
	public String category(HttpServletRequest request, @PathVariable("categoryName") String categoryName, @PathVariable("page") Integer page) {
		PageResult pageResult = newsService.getBlogsPageByCategory(categoryName, page);
        request.setAttribute("blogPageResult", pageResult);
        request.setAttribute("pageName", "分类");
        request.setAttribute("pageUrl", "category");
        request.setAttribute("keyword", categoryName);
        return "blog/list";
	}
	
	@GetMapping("/tag/{tagName}")
	public String tag(HttpServletRequest request, @PathVariable("tagName") String tagName) {
       return tag(request, tagName, 1);
    }
	
	@GetMapping({"/tag/{tagName}/{page}"})
    public String tag(HttpServletRequest request, @PathVariable("tagName") String tagName, @PathVariable("page") Integer page) {
        PageResult blogPageResult = newsService.getBlogsPageByTag(tagName, page);
        request.setAttribute("blogPageResult", blogPageResult);
        request.setAttribute("pageName", "标签");
        request.setAttribute("pageUrl", "tag");
        request.setAttribute("keyword", tagName);
        return "blog/list";
    }
	
	@GetMapping("/blog/{blogId}")
	public String tag(HttpServletRequest request, @PathVariable("blogId") Long blogId,@RequestParam(value = "commentPage", required = false, defaultValue = "1") Integer commentPage) {
       BlogDetailVO blogDetailVO = newsService.getBlogDetail(blogId);
       if(blogDetailVO != null) {
    	   request.setAttribute("blogDetailVO", blogDetailVO);
    	   request.setAttribute("commentPageResult", commentService.getCommentPageByBlogIdAndPageNum(blogId, commentPage));
       }
       request.setAttribute("pageName", "详情");
       return "blog/detail";
    }
	
    /**
     * 评论操作
     */
    @PostMapping(value = "/blog/comment")
    @ResponseBody
    public Result comment(HttpServletRequest request, HttpSession session,@RequestParam Long blogId, @RequestParam String verifyCode,@RequestParam String commentator, @RequestParam String email,@RequestParam String websiteUrl, @RequestParam String commentBody) {
        /*System.out.println(commentBody);*/
    	if (StringUtils.isEmpty(verifyCode)) {
            return ResultGenerator.genFailResult("验证码不能为空");
        }
        String kaptchaCode = session.getAttribute("verifyCode") + "";
        if (StringUtils.isEmpty(kaptchaCode)) {
            return ResultGenerator.genFailResult("非法请求");
        }
        if (!verifyCode.equals(kaptchaCode)) {
            return ResultGenerator.genFailResult("验证码错误");
        }
        String ref = request.getHeader("Referer");
        if (StringUtils.isEmpty(ref)) {
            return ResultGenerator.genFailResult("非法请求");
        }
        if (null == blogId || blogId < 0) {
            return ResultGenerator.genFailResult("非法请求");
        }
        if (StringUtils.isEmpty(commentator)) {
            return ResultGenerator.genFailResult("请输入称呼");
        }
        if (StringUtils.isEmpty(email)) {
            return ResultGenerator.genFailResult("请输入邮箱地址");
        }
        if (!PatternUtil.isEmail(email)) {
            return ResultGenerator.genFailResult("请输入正确的邮箱地址");
        }
        if (StringUtils.isEmpty(commentBody)) {
            return ResultGenerator.genFailResult("请输入评论内容");
        }
        if (commentBody.trim().length() > 200) {
            return ResultGenerator.genFailResult("评论内容过长");
        }
        BlogComment comment = new BlogComment();
        comment.setBlogId(blogId);
        comment.setCommentator(MyBlogUtils.cleanString(commentator));
        comment.setEmail(email);
        if (PatternUtil.isURL(websiteUrl)) {
            comment.setWebsiteUrl(websiteUrl);
        }
        comment.setCommentBody(MyBlogUtils.cleanString(commentBody));
        return ResultGenerator.genSuccessResult(commentService.addComment(comment));
    }
    
    @GetMapping("/link")
    public String link(HttpServletRequest request) {
    	request.setAttribute("pageName", "友情链接");
    	Map<Byte, List<BlogLink>> linkMap = linkService.getLinksForLinkPage();
    	if(linkMap!=null) {
    		if(linkMap.containsKey((byte) 0)) {
    			request.setAttribute("favoriteLinks", linkMap.get((byte) 0));
    		}
    		if(linkMap.containsKey((byte) 1)) {
    			request.setAttribute("recommendLinks",linkMap.get((byte) 1));
    		}
    		if(linkMap.containsKey((byte) 2)) {
    			request.setAttribute("personalLinks",linkMap.get((byte) 2));
    		}
    	
    	}
    	return "blog/link";
    }
	
}
