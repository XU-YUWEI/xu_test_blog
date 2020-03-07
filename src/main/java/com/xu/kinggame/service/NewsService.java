package com.xu.kinggame.service;

import java.util.List;

import com.xu.kinggame.controller.cus.BlogDetailVO;
import com.xu.kinggame.controller.cus.CustomerBlogList;
import com.xu.kinggame.entity.Blog;
/*import com.xu.kinggame.entity.News;*/
import com.xu.kinggame.util.PageQueryUtil;
import com.xu.kinggame.util.PageResult;

public interface NewsService {

	PageResult selectNews(PageQueryUtil pageUtil);

	String addNew(Blog blog);


	String updateNew(Blog blog);

	boolean deleteNew(Integer[] ids);

	/*News selectNewsById(Long id);*/

	Blog selectblogsById(Long id);

	List<CustomerBlogList> getBlogListForIndexPage(int i);
	
	PageResult getBlogsForIndexPage(int page);
	
	PageResult getBlogsPageBySearch(String keyword, int page);
	
	PageResult getBlogsPageByCategory(String categoryName, int page);
	
	PageResult getBlogsPageByTag(String tagName, int page);
	
	BlogDetailVO getBlogDetail(Long blogId);

}
