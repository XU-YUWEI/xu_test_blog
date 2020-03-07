package com.xu.kinggame.controller.admin;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xu.kinggame.entity.BlogLink;
import com.xu.kinggame.service.LinkService;
import com.xu.kinggame.util.PageQueryUtil;
import com.xu.kinggame.util.Result;
import com.xu.kinggame.util.ResultGenerator;

@Controller
@RequestMapping("/admin")
public class LinkController {

	@Resource
	private LinkService linkService;
	
	@GetMapping("/links")
	public String link(HttpServletRequest request) {
		request.setAttribute("path", "links");
		return "admin/link";
	}
	
	@GetMapping("/links/list")
	@ResponseBody
	public Result link(@RequestParam Map<String, Object> params) {
		if (StringUtils.isEmpty(params.get("limit"))||StringUtils.isEmpty(params.get("page"))) {
			ResultGenerator.genFailResult("参数错误");
		}
		PageQueryUtil pageQueryUtil = new PageQueryUtil(params);
		return ResultGenerator.genSuccessResult(linkService.selectLink(pageQueryUtil));
	}
	
	@PostMapping("/links/addlink")
	@ResponseBody
	public Result addlink(@RequestParam("linkType") Integer linkType,
            @RequestParam("linkName") String linkName,
            @RequestParam("linkUrl") String linkUrl,
            @RequestParam("linkRank") Integer linkRank,
            @RequestParam("linkDescription") String linkDescription) {
		if (linkType == null || linkType < 0 || linkRank == null || linkRank < 0 || StringUtils.isEmpty(linkName) || StringUtils.isEmpty(linkName) || StringUtils.isEmpty(linkUrl) || StringUtils.isEmpty(linkDescription)) {
            return ResultGenerator.genFailResult("参数异常！");
        }
		
		BlogLink link = new BlogLink();
		link.setLinkType(linkType.byteValue());
        link.setLinkRank(linkRank);
        link.setLinkName(linkName);
        link.setLinkUrl(linkUrl);
        link.setLinkDescription(linkDescription);
        return ResultGenerator.genSuccessResult(linkService.addlink(link));
	}
	
	@PostMapping("/links/update")
	@ResponseBody
	public Result update(@RequestParam("linkId") Integer linkId,
            @RequestParam("linkType") Integer linkType,
            @RequestParam("linkName") String linkName,
            @RequestParam("linkUrl") String linkUrl,
            @RequestParam("linkRank") Integer linkRank,
            @RequestParam("linkDescription") String linkDescription) {
		BlogLink link = linkService.selectLinkById(linkId);
		if (link == null) {
            return ResultGenerator.genFailResult("无数据！");
        }
		if (linkType == null || linkType < 0 || linkRank == null || linkRank < 0 || StringUtils.isEmpty(linkName) || StringUtils.isEmpty(linkName) || StringUtils.isEmpty(linkUrl) || StringUtils.isEmpty(linkDescription)) {
            return ResultGenerator.genFailResult("参数异常！");
        }
		link.setLinkType(linkType.byteValue());
		link.setLinkRank(linkRank);
		link.setLinkName(linkName);
		link.setLinkUrl(linkUrl);
		link.setLinkDescription(linkDescription);
		return ResultGenerator.genSuccessResult(linkService.updatelink(link));
	}
	
	@PostMapping("/links/delete")
	@ResponseBody
	public Result delete(@RequestBody Integer[] ids) {
		System.out.println("1");
		if(ids.length<1) {
			return ResultGenerator.genFailResult("参数错误");
		}
		if(linkService.deleteLink(ids)) {
			System.out.println("2");
			return ResultGenerator.genSuccessResult();
		}else {
			System.out.println("3");
			return ResultGenerator.genFailResult("删除失败");
		}
	}
	
	@GetMapping("/links/info/{id}")
	@ResponseBody
	public Result info(@PathVariable Integer id) {
		BlogLink link = linkService.selectLinkById(id);
		return ResultGenerator.genSuccessResult(link);
	}
	
}
