package com.xu.kinggame.controller.admin;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xu.kinggame.service.CommentService;
import com.xu.kinggame.util.PageQueryUtil;
import com.xu.kinggame.util.Result;
import com.xu.kinggame.util.ResultGenerator;

@Controller
@RequestMapping("/admin")
public class CommentController {

	@Resource
	private CommentService commentService;
	
	@GetMapping("/comments")
	public String list(HttpServletRequest request) {
        request.setAttribute("path", "comments");
        return "admin/comment";
    }
	   @GetMapping("/comment/list")
	   @ResponseBody
	   public Result list(@RequestParam Map<String, Object> params) {
		   /*System.out.println(params);*/
		   if(StringUtils.isEmpty(params.get("limit"))||StringUtils.isEmpty(params.get("page"))) {
			   return ResultGenerator.genFailResult("参数错误");
		   }
		   PageQueryUtil pageQueryUtil = new PageQueryUtil(params);
		   return ResultGenerator.genSuccessResult(commentService.selectComment(pageQueryUtil));
	   }
	   
	   @PostMapping("/comment/check")
	   @ResponseBody
	   public Result Check(@RequestBody Integer[] ids) {
		   if(ids.length<1) {
			   return ResultGenerator.genFailResult("参数异常");
		   }
		   if(commentService.checkComment(ids)) {
			   return ResultGenerator.genSuccessResult();
		   }else {
			   return ResultGenerator.genFailResult("删除失败");
		   }
	   }
	   
	   @PostMapping("/comment/delete")
	   @ResponseBody
	   public Result delete(@RequestBody Integer[] ids) {
		   if(ids.length<1) {
			   return ResultGenerator.genFailResult("参数异常");
		   }
		   if(commentService.deleteComment(ids)) {
			   return ResultGenerator.genSuccessResult();
		   }else {
			   return ResultGenerator.genFailResult("删除失败");
		   }
	   }
	   
	   @PostMapping("/comments/reply")
	   @ResponseBody
	   public Result reply(@RequestParam("commentId") Long commentId,
               @RequestParam("replyBody") String replyBody) {
		   /*System.out.println(replyBody);*/
		   if (commentId == null || commentId < 1 || StringUtils.isEmpty(replyBody)) {
	            return ResultGenerator.genFailResult("参数异常！");
	        }
	        if (commentService.reply(commentId, replyBody)) {
	            return ResultGenerator.genSuccessResult();
	        } else {
	            return ResultGenerator.genFailResult("回复失败");
	        }
	   }
	   
	
}
