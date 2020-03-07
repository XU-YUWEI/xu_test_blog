package com.xu.kinggame.controller.admin;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.xu.kinggame.config.Constants;
import com.xu.kinggame.entity.Blog;
/*import com.xu.kinggame.entity.News;*/
import com.xu.kinggame.service.KindService;
import com.xu.kinggame.service.NewsService;
import com.xu.kinggame.util.MyBlogUtils;
import com.xu.kinggame.util.PageQueryUtil;
import com.xu.kinggame.util.Result;
import com.xu.kinggame.util.ResultGenerator;

@Controller
@RequestMapping("/admin")
public class NewsController {

	@Resource
	private NewsService newsService;
	@Resource
	private KindService kindService;
	
	/*@GetMapping("/news")
	public String list(HttpServletRequest request) {
		request.setAttribute("path", "news");
		return "admin/news";
	}*/
    @GetMapping("/blogs")
    public String list(HttpServletRequest request) {
        request.setAttribute("path", "blogs");
        return "admin/blog";
    }
	@GetMapping("/news/edit")
	public String edit(HttpServletRequest request) {
		request.setAttribute("path", "edit");
		request.setAttribute("categories", kindService.getAllKinds());
		return "admin/edit";
	}
	
	@GetMapping("/news/list")
	@ResponseBody
	public Result list(@RequestParam Map<String, Object> params) {
		if(StringUtils.isEmpty(params.get("page"))||StringUtils.isEmpty("limit")) {
			 return ResultGenerator.genFailResult("参数异常");
		}
		PageQueryUtil pageUtil = new PageQueryUtil(params);
		return ResultGenerator.genSuccessResult(newsService.selectNews(pageUtil));
	}
	
	/*@RequestParam("newTitle") String newsTitle,
    这里的@RequestParam里面的参数名应该是与数据库里的一样@RequestParam("newKindId") Long newsCategoryId,
    @RequestParam("newContent") String newsContent,
    @RequestParam("newImage") String newsCoverImage,
    @RequestParam("newStatus") Byte newsStatus*/
	@PostMapping("/news/addNew")
	@ResponseBody
	public Result addNew(@RequestParam("blogTitle") String blogTitle,
            @RequestParam(name = "blogSubUrl", required = false) String blogSubUrl,
            @RequestParam("blogCategoryId") Integer blogCategoryId,
            @RequestParam("blogTags") String blogTags,
            @RequestParam("blogContent") String blogContent,
            @RequestParam("blogCoverImage") String blogCoverImage,
            @RequestParam("blogStatus") Byte blogStatus,
            @RequestParam("enableComment") Byte enableComment) {
		/*System.out.println(newsTitle+" "+newsCategoryId+" "+newsContent+" "+newsCoverImage+" "+newsStatus);*/
		if (StringUtils.isEmpty(blogTitle)) {
            return ResultGenerator.genFailResult("请输入文章标题");
        }
        if (blogTitle.trim().length() > 150) {
            return ResultGenerator.genFailResult("标题过长");
        }
        if (StringUtils.isEmpty(blogTags)) {
            return ResultGenerator.genFailResult("请输入文章标签");
        }
        if (blogTags.trim().length() > 150) {
            return ResultGenerator.genFailResult("标签过长");
        }
        if (blogSubUrl.trim().length() > 150) {
            return ResultGenerator.genFailResult("路径过长");
        }
        if (StringUtils.isEmpty(blogContent)) {
            return ResultGenerator.genFailResult("请输入文章内容");
        }
        if (blogTags.trim().length() > 100000) {
            return ResultGenerator.genFailResult("文章内容过长");
        }
        if (StringUtils.isEmpty(blogCoverImage)) {
            return ResultGenerator.genFailResult("封面图不能为空");
        }
        
        Blog blog = new Blog();
        blog.setBlogTitle(blogTitle);
        blog.setBlogSubUrl(blogSubUrl);
        blog.setBlogCategoryId(blogCategoryId);
        blog.setBlogTags(blogTags);
        blog.setBlogContent(blogContent);
        blog.setBlogCoverImage(blogCoverImage);
        blog.setBlogStatus(blogStatus);
        blog.setEnableComment(enableComment);
        String saveBlogResult = newsService.addNew(blog);
        if ("success".equals(saveBlogResult)) {
            return ResultGenerator.genSuccessResult("添加成功");
        } else {
            return ResultGenerator.genFailResult(saveBlogResult);
        }
	}
	
	@GetMapping("/news/edit/{newsId}")
	public String edit(@PathVariable("newsId") Long id,HttpServletRequest request) {
		/*System.out.println(id);*/
		request.setAttribute("path", "edit");
		Blog blog = newsService.selectblogsById(id);
		if(blog==null) {
			return "error/error_400";
		}
		/*System.out.println("222");*/
		request.setAttribute("blog", blog);
		/*System.out.println(blog);*/
		request.setAttribute("categories", kindService.getAllKinds());
		/*System.out.println(kindService.getAllKinds());*/
        return "admin/edit";
	}
	
	/*@GetMapping("/news/edit/{newsId}")
    public String edit(HttpServletRequest request, @PathVariable("newsId") Long newsId) {
        request.setAttribute("path", "edit");
        News news = newsService.selectNewsById(newsId);
        if (news == null) {
            return "error/error_400";
        }
        request.setAttribute("news", news);
        request.setAttribute("categories", kindService.getAllKinds());
        return "admin/edit";
    }*/
	
	@PostMapping("/news/update")
	@ResponseBody
	public Result update(@RequestParam("blogId") Long blogId,
            @RequestParam("blogTitle") String blogTitle,
            @RequestParam(name = "blogSubUrl", required = false) String blogSubUrl,
            @RequestParam("blogCategoryId") Integer blogCategoryId,
            @RequestParam("blogTags") String blogTags,
            @RequestParam("blogContent") String blogContent,
            @RequestParam("blogCoverImage") String blogCoverImage,
            @RequestParam("blogStatus") Byte blogStatus,
            @RequestParam("enableComment") Byte enableComment) {
		/*System.out.println("ss");*/
		if (StringUtils.isEmpty(blogTitle)) {
            return ResultGenerator.genFailResult("请输入文章标题");
        }
        if (blogTitle.trim().length() > 150) {
            return ResultGenerator.genFailResult("标题过长");
        }
        if (StringUtils.isEmpty(blogTags)) {
            return ResultGenerator.genFailResult("请输入文章标签");
        }
        if (blogTags.trim().length() > 150) {
            return ResultGenerator.genFailResult("标签过长");
        }
        if (blogSubUrl.trim().length() > 150) {
            return ResultGenerator.genFailResult("路径过长");
        }
        if (StringUtils.isEmpty(blogContent)) {
            return ResultGenerator.genFailResult("请输入文章内容");
        }
        if (blogTags.trim().length() > 100000) {
            return ResultGenerator.genFailResult("文章内容过长");
        }
        if (StringUtils.isEmpty(blogCoverImage)) {
            return ResultGenerator.genFailResult("封面图不能为空");
        }
        
        Blog blog = new Blog();
        blog.setBlogId(blogId);
        blog.setBlogTitle(blogTitle);
        blog.setBlogSubUrl(blogSubUrl);
        blog.setBlogCategoryId(blogCategoryId);
        blog.setBlogTags(blogTags);
        blog.setBlogContent(blogContent);
        blog.setBlogCoverImage(blogCoverImage);
        blog.setBlogStatus(blogStatus);
        blog.setEnableComment(enableComment);
        String anser = newsService.updateNew(blog);
       /* System.out.println(anser);*/
        if("success".equals(anser)) {
        	return ResultGenerator.genSuccessResult("修改成功");
        }else {
        	return ResultGenerator.genFailResult(anser);
        }
	}
	
	@PostMapping("/news/delete")
	@ResponseBody
	public Result delete(@RequestBody Integer[] ids) {
		if(ids.length<1) {
			return ResultGenerator.genFailResult("参数异常");
		}
		if (newsService.deleteNew(ids)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("删除失败");
        }
	}
	
	@PostMapping("/news/md/uploadfile")
	public void uploadFileByEditormd(HttpServletRequest request,HttpServletResponse response,@RequestParam(name = "editormd-image-file", required = true) MultipartFile file) throws IOException,URISyntaxException {
		String fileName = file.getOriginalFilename();
		String suffixName = fileName.substring(fileName.lastIndexOf("."));
		//生成文件通用方法
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
		Random random = new Random();
		StringBuilder builder = new StringBuilder();
		builder.append(sdf.format(new Date())).append(random.nextInt(100)).append(suffixName);
		String newfilname = builder.toString();
		//生成文件
		File dataFile = new File(Constants.FILE_UPLOAD_PATH+newfilname);
		String fileUrl = MyBlogUtils.getHost(new URI(request.getRequestURL() + "")) + "/upload/" + newfilname;
        File fileDirectory = new File(Constants.FILE_UPLOAD_PATH);
        try {
            if (!fileDirectory.exists()) {
                if (!fileDirectory.mkdir()) {
                    throw new IOException("文件夹创建失败,路径为：" + fileDirectory);
                }
            }
            file.transferTo(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            request.setCharacterEncoding("utf-8");
            response.setHeader("Content-Type", "text/html");
            response.getWriter().write("{\"success\": 1, \"message\":\"success\",\"url\":\"" + fileUrl + "\"}");
        } catch (UnsupportedEncodingException e) {
            response.getWriter().write("{\"success\":0}");
        } catch (IOException e) {
            response.getWriter().write("{\"success\":0}");
        }
		
	}
	
}
