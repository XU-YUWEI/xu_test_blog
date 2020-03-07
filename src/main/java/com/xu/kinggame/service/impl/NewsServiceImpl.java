package com.xu.kinggame.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.xu.kinggame.controller.cus.BlogDetailVO;
import com.xu.kinggame.controller.cus.BlogList;
import com.xu.kinggame.controller.cus.CustomerBlogList;
import com.xu.kinggame.dao.BlogMapper;
import com.xu.kinggame.dao.BlogTagMapper;
import com.xu.kinggame.dao.BlogTagRelationMapper;
import com.xu.kinggame.dao.KindMapper;
/*import com.xu.kinggame.dao.NewsMapper;*/
import com.xu.kinggame.entity.Blog;
import com.xu.kinggame.entity.BlogTag;
import com.xu.kinggame.entity.BlogTagRelation;
import com.xu.kinggame.entity.Kind;
/*import com.xu.kinggame.entity.News;*/
import com.xu.kinggame.service.NewsService;
import com.xu.kinggame.util.MarkDownUtil;
import com.xu.kinggame.util.PageQueryUtil;
import com.xu.kinggame.util.PageResult;
import com.xu.kinggame.util.PatternUtil;
@Service
public class NewsServiceImpl implements NewsService{


	
	@Resource
	private KindMapper kindMapper;
	
	@Resource
	private BlogMapper blogMapper;
	
	@Resource
	private BlogTagMapper blogTagMapper;
	
	@Resource
	private BlogTagRelationMapper blogTagRelationMapper;
	
	@Override
	public PageResult selectNews(PageQueryUtil pageUtil) {
		/*List<News> list = newMapper.selectNews(pageUtil);
		int count = newMapper.selectCount(pageUtil);
		PageResult pageResult = new PageResult(list, count, pageUtil.getLimit(), pageUtil.getPage());
		return pageResult;*/
		List<Blog> blogList = blogMapper.findBlogList(pageUtil);
        int total = blogMapper.getTotalBlogs(pageUtil);
        PageResult pageResult = new PageResult(blogList, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
	}
	@Override
	@Transactional
	public String addNew(Blog blog) {
		/*if(blogMapper.insertSelective(news)>0) {
			return "success";
		}
		else {
			return "添加失败";
		}*/
		Long id = (long)blog.getBlogCategoryId();
		Kind blogCategory = kindMapper.selectKindById(id);
        if (blogCategory == null) {
            blog.setBlogCategoryId(0);
            blog.setBlogCategoryName("默认分类");
        } else {
            //设置博客分类名称
            blog.setBlogCategoryName(blogCategory.getKindTitle());
            //分类的排序值加1
           /* blogCategory.setCategoryRank(blogCategory.getCategoryRank() + 1);*/
        }
        //处理标签数据
        String[] tags = blog.getBlogTags().split(",");
        if (tags.length > 6) {
            return "标签数量限制为6";
        }
        //保存文章
        if (blogMapper.insertSelective(blog) > 0) {
            //新增的tag对象
            List<BlogTag> tagListForInsert = new ArrayList<>();
            //所有的tag对象，用于建立关系数据
            List<BlogTag> allTagsList = new ArrayList<>();
            for (int i = 0; i < tags.length; i++) {
                BlogTag tag = blogTagMapper.selectByTagName(tags[i]);
                if (tag == null) {
                    //不存在就新增
                    BlogTag tempTag = new BlogTag();
                    tempTag.setTagName(tags[i]);
                    tagListForInsert.add(tempTag);
                } else {
                    allTagsList.add(tag);
                }
            }
            //新增标签数据并修改分类排序值
            if (!CollectionUtils.isEmpty(tagListForInsert)) {
                blogTagMapper.batchInsertBlogTag(tagListForInsert);
            }
            /*categoryMapper.updateByPrimaryKeySelective(blogCategory);*/
            List<BlogTagRelation> blogTagRelations = new ArrayList<>();
            //新增关系数据
            allTagsList.addAll(tagListForInsert);
            for (BlogTag tag : allTagsList) {
                BlogTagRelation blogTagRelation = new BlogTagRelation();
                blogTagRelation.setBlogId(blog.getBlogId());
                blogTagRelation.setTagId(tag.getTagId());
                blogTagRelations.add(blogTagRelation);
            }
            if (blogTagRelationMapper.batchInsert(blogTagRelations) > 0) {
                return "success";
            }
        }
        return "保存失败";
		
	}
	@Override
	@Transactional
	public String updateNew(Blog blog) {
		Blog blogForUpdate = blogMapper.selectByPrimaryKey(blog.getBlogId());
		/*System.out.println(news.getNewId());
		System.out.println(updatenew);
		System.out.println(news);*/
		if(blogForUpdate==null) {
			return "数据不存在";
		}
		blogForUpdate.setBlogTitle(blog.getBlogTitle());
        blogForUpdate.setBlogSubUrl(blog.getBlogSubUrl());
        blogForUpdate.setBlogContent(blog.getBlogContent());
        blogForUpdate.setBlogCoverImage(blog.getBlogCoverImage());
        blogForUpdate.setBlogStatus(blog.getBlogStatus());
        blogForUpdate.setEnableComment(blog.getEnableComment());
		/*if(blogMapper.updateNew(blogForUpdate)>0) {
			return "success";
		}else {
			return "修改失败";
		}*/
        Long id = (long)blog.getBlogCategoryId();
        Kind blogCategory = kindMapper.selectKindById(id);
        if (blogCategory == null) {
            blogForUpdate.setBlogCategoryId(0);
            blogForUpdate.setBlogCategoryName("默认分类");
        } else {
            //设置博客分类名称
            blogForUpdate.setBlogCategoryName(blogCategory.getKindTitle());
            Integer b=blogCategory.getKindId().intValue();
            blogForUpdate.setBlogCategoryId(b);	
            //分类的排序值加1
           /* blogCategory.setCategoryRank(blogCategory.getCategoryRank() + 1);*/
        }
        //处理标签数据
        String[] tags = blog.getBlogTags().split(",");
        if (tags.length > 6) {
            return "标签数量限制为6";
        }
        blogForUpdate.setBlogTags(blog.getBlogTags());
        //新增的tag对象
        List<BlogTag> tagListForInsert = new ArrayList<>();
        //所有的tag对象，用于建立关系数据
        List<BlogTag> allTagsList = new ArrayList<>();
        for (int i = 0; i < tags.length; i++) {
            BlogTag tag = blogTagMapper.selectByTagName(tags[i]);
            if (tag == null) {
                //不存在就新增
                BlogTag tempTag = new BlogTag();
                tempTag.setTagName(tags[i]);
                tagListForInsert.add(tempTag);
            } else {
                allTagsList.add(tag);
            }
        }
        //新增标签数据不为空->新增标签数据
        if (!CollectionUtils.isEmpty(tagListForInsert)) {
            blogTagMapper.batchInsertBlogTag(tagListForInsert);
        }
        List<BlogTagRelation> blogTagRelations = new ArrayList<>();
        //新增关系数据
        allTagsList.addAll(tagListForInsert);
        for (BlogTag tag : allTagsList) {
            BlogTagRelation blogTagRelation = new BlogTagRelation();
            blogTagRelation.setBlogId(blog.getBlogId());
            blogTagRelation.setTagId(tag.getTagId());
            blogTagRelations.add(blogTagRelation);
        }
        //修改blog信息->修改分类排序值->删除原关系数据->保存新的关系数据
        /*categoryMapper.updateByPrimaryKeySelective(blogCategory);*/
        //删除原关系数据
        blogTagRelationMapper.deleteByBlogId(blog.getBlogId());
        blogTagRelationMapper.batchInsert(blogTagRelations);
        if (blogMapper.updateByPrimaryKeySelective(blogForUpdate) > 0) {
            return "success";
        }
        return "修改失败";
        
	}
	@Override
	public boolean deleteNew(Integer[] ids) {
		/*if(newMapper.deleteNew(ids)>0) {
			return true;
		}else {
			return false;
		}*/
		if (ids.length < 1) {
            return false;
        }
        return blogMapper.deleteBatch(ids) > 0;
	}
	
	/*@Override
	public News selectNewsById(Long id) {
		News updatenew = newMapper.selectNewById(id);
		return updatenew;
		
	}*/
	@Override
	public Blog selectblogsById(Long id) {
		return blogMapper.selectByPrimaryKey(id);
	}
	@Override
	public List<CustomerBlogList> getBlogListForIndexPage(int type){
		List<CustomerBlogList> customerBlogLists = new ArrayList<>();
		List<Blog> blogs = blogMapper.selectBlogListByType(type,9);
		if(!CollectionUtils.isEmpty(blogs)) {
			for(Blog blog:blogs) {
				CustomerBlogList customerBlogList = new CustomerBlogList();
				BeanUtils.copyProperties(blog, customerBlogList);
				customerBlogLists.add(customerBlogList);
			}
		}
		return customerBlogLists;
		
	}
	@Override
	public PageResult getBlogsForIndexPage(int page) {
		Map params = new HashMap();
		params.put("page", page);
		params.put("limit", 8);
		params.put("blogStatus", 1);
		PageQueryUtil pageQueryUtil = new PageQueryUtil(params);
		List<Blog> list = blogMapper.findBlogList(pageQueryUtil);
		List<BlogList> blogLists = getBlogListVOsByBlogs(list);
		int total = blogMapper.getTotalBlogs(pageQueryUtil);
		PageResult pageResult = new PageResult(blogLists, total, pageQueryUtil.getLimit(), pageQueryUtil.getPage());
		return pageResult;
	}
	
	private List<BlogList>getBlogListVOsByBlogs(List<Blog> blogList){
		List<BlogList> blogLists =new ArrayList<>();
		if(!CollectionUtils.isEmpty(blogList)) {
			List<Integer> kindIds = blogList.stream().map(Blog::getBlogCategoryId).collect(Collectors.toList());
			/*Map<Integer,String> blogKindMap = new HashMap<>();
			if(!CollectionUtils.isEmpty(kindIds)) {
				List<Kind> blogKinds = kindMapper.selectKindByids(kindIds);
				if(!CollectionUtils.isEmpty(blogKinds)) {
					blogKindMap = blogKinds.stream().collect(Collectors.toMap(Kind::getKindId, key1 -> key2));
				}
			}*/
			
			for(Blog blog:blogList) {
				BlogList blogList2 = new BlogList();
				BeanUtils.copyProperties(blog, blogList2);
				/*if(blogKindMap.containsKey(blog.getBlogCategoryId())) {
					
				}*/
				if(!kindIds.contains(blog.getBlogCategoryId())) {
					blogList2.setBlogCategoryId(0);
					blogList2.setBlogCategoryName("默认分类");
				}
				blogLists.add(blogList2);
				
			}
		}
		return blogLists;
	}
	@Override
	public PageResult getBlogsPageBySearch(String keyword, int page) {
		if (page>0 && PatternUtil.validKeyword(keyword)) {
			Map param = new HashMap<>();
			param.put("page", page);
            param.put("limit", 9);
            param.put("keyword", keyword);
            param.put("blogStatus", 1);
            PageQueryUtil pageQueryUtil = new PageQueryUtil(param);
            List<Blog> blogs = blogMapper.findBlogList(pageQueryUtil);
            List<BlogList> blogLists = getBlogListVOsByBlogs(blogs);
            int total = blogMapper.getTotalBlogs(pageQueryUtil);
            PageResult pageResult = new PageResult(blogLists, total, pageQueryUtil.getLimit(), pageQueryUtil.getPage());
            return pageResult;
		}
		return null;
	}
	@Override
	public PageResult getBlogsPageByCategory(String categoryName, int page) {
		if(PatternUtil.validKeyword(categoryName)) {
			Kind kind = kindMapper.selectKindByName(categoryName);
			/*if ("默认分类".equals(categoryName) && kind == null) {
                kind = new Kind();
                kind.setKindId(0);
            }*/
			if(kind!=null && page>0) {
			Map param = new HashMap<>();
			param.put("page", page);
            param.put("limit", 9);
            param.put("blogCategoryId", kind.getKindId());
            param.put("blogStatus", 1);
			PageQueryUtil pageQueryUtil = new PageQueryUtil(param);
			List<Blog> blogs = blogMapper.findBlogList(pageQueryUtil);
			List<BlogList> blogLists = getBlogListVOsByBlogs(blogs);
			int total = blogMapper.getTotalBlogs(pageQueryUtil);
			PageResult pageResult = new PageResult(blogLists, total, pageQueryUtil.getLimit(), pageQueryUtil.getPage());
			return pageResult;
			}
		}
		return null;
	}
	@Override
	public PageResult getBlogsPageByTag(String tagName, int page) {
		if(PatternUtil.validKeyword(tagName)) {
			BlogTag blogTag = blogTagMapper.selectByTagName(tagName);
			if(blogTag!=null && page>0) {
				Map param = new HashMap<>();
				param.put("page", page);
                param.put("limit", 9);
                param.put("tagId", blogTag.getTagId());
                PageQueryUtil pageQueryUtil = new PageQueryUtil(param);
                List<Blog> blogs = blogMapper.getBlogsPageByTagId(pageQueryUtil);
                List<BlogList> blogTags = getBlogListVOsByBlogs(blogs);
                int total = blogMapper.getTotalBlogsByTagId(pageQueryUtil);
                PageResult pageResult = new PageResult(blogTags, total, pageQueryUtil.getLimit(), pageQueryUtil.getPage());
                return pageResult;
			}
		}
		return null;
	}
	@Override
	public BlogDetailVO getBlogDetail(Long blogId) {
		Blog blog = blogMapper.selectByPrimaryKey(blogId);
		
		BlogDetailVO blogDetailVO = getBlogDetailVO(blog);
		if (blogDetailVO != null) {
            return blogDetailVO;
        }
        return null;
	}
	
	private BlogDetailVO getBlogDetailVO(Blog blog) {
		if(blog!=null&&blog.getBlogStatus()==1) {
			blog.setBlogViews(blog.getBlogViews()+1);
			blogMapper.updateByPrimaryKey(blog);
			BlogDetailVO blogDetailVO = new BlogDetailVO();
			BeanUtils.copyProperties(blog, blogDetailVO);
			
			blogDetailVO.setBlogContent(MarkDownUtil.mdToHtml(blogDetailVO.getBlogContent()));
			/*BlogCategory blogCategory = categoryMapper.selectByPrimaryKey(blog.getBlogCategoryId());
            if (blogCategory == null) {
                blogCategory = new BlogCategory();
                blogCategory.setCategoryId(0);
                blogCategory.setCategoryName("默认分类");
                blogCategory.setCategoryIcon("/admin/dist/img/category/00.png");
            }*/
			//分类信息
            /*blogDetailVO.setBlogCategoryIcon(blogCategory.getCategoryIcon());*/
			if(StringUtils.isEmpty(blog.getBlogTags())) {
				//标签设置
				List<String> blogTags = Arrays.asList(blog.getBlogTags().split(","));
				blogDetailVO.setBlogTags(blogTags);
				
			}
			return blogDetailVO;
		}
		return null;
	}

}
