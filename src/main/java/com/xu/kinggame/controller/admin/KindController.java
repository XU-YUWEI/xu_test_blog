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

import com.xu.kinggame.entity.Kind;
import com.xu.kinggame.service.KindService;
import com.xu.kinggame.util.PageQueryUtil;
import com.xu.kinggame.util.Result;
import com.xu.kinggame.util.ResultGenerator;

@Controller
@RequestMapping("/admin")
public class KindController {

	@Resource
	private KindService kindService;
	@GetMapping("/categories")
	public String categories(HttpServletRequest request) {
		request.setAttribute("path", "categories");
		/*System.out.println("00");*/
		return "admin/category";
	}
	@GetMapping("/kind/list")
	@ResponseBody
	public Result list(@RequestParam Map<String , Object> params) {
		/*System.out.println(params);*/
		if(StringUtils.isEmpty(params.get("page"))||StringUtils.isEmpty(params.get("page"))) {
			return ResultGenerator.genFailResult("参数异常");
		}
		PageQueryUtil pageQueryUtil = new PageQueryUtil(params);
		/*System.out.println("11");*/
		return ResultGenerator.genSuccessResult(kindService.UserList(pageQueryUtil));
	}
	
	@PostMapping("/kind/addkind")
	@ResponseBody
	public Result addKind(@RequestParam("categoryName") String kindName) {
		if(StringUtils.isEmpty(kindName)) {
			return ResultGenerator.genFailResult("参数异常");
		}
		
		if(kindService.insertKind(kindName)) {
			return ResultGenerator.genSuccessResult();
		}else {
		return ResultGenerator.genFailResult("添加分类失败");
		}
	}
	
	@GetMapping("/kind/info/{id}")
	@ResponseBody
	public Result info(@PathVariable("id") Long id) {
		Kind kind=kindService.selectById(id);
		return ResultGenerator.genSuccessResult(kind);
	}
	
	@PostMapping("/kind/update")
	@ResponseBody
	public Result addKind(@RequestParam("categoryName") String kindName,@RequestParam("categoryId") Long kindId) {
		if(StringUtils.isEmpty(kindName)||kindId<1) {
			return ResultGenerator.genFailResult("参数异常");
		}
		
		if(kindService.updateKind(kindName,kindId)) {
			return ResultGenerator.genSuccessResult();
		}else {
		return ResultGenerator.genFailResult("修改分类失败");
		}
	}
	
	@PostMapping("/kind/delete")
	@ResponseBody
	public Result delete(@RequestBody Integer[] ids) {
		System.out.println("ww");
		  if(ids.length<1) {
			  return ResultGenerator.genFailResult("参数异常");
		  }
		  if(kindService.deleteKind(ids)) {
			  return ResultGenerator.genSuccessResult();
		  }
		  return ResultGenerator.genFailResult("修改失败");
	}
}
