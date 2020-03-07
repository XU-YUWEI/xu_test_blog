package com.xu.kinggame.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.xu.kinggame.dao.BlogTagMapper;
import com.xu.kinggame.dao.BlogTagRelationMapper;
import com.xu.kinggame.entity.BlogTag;
import com.xu.kinggame.entity.BlogTagCount;
import com.xu.kinggame.service.TagService;
import com.xu.kinggame.util.PageQueryUtil;
import com.xu.kinggame.util.PageResult;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private BlogTagMapper blogTagMapper;
    @Autowired
    private BlogTagRelationMapper relationMapper;

    @Override
    public PageResult getBlogTagPage(PageQueryUtil pageUtil) {
        List<BlogTag> tags = blogTagMapper.findTagList(pageUtil);
        int total = blogTagMapper.getTotalTags(pageUtil);
        PageResult pageResult = new PageResult(tags, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public Boolean saveTag(String tagName) {
        BlogTag temp = blogTagMapper.selectByTagName(tagName);
        if (temp == null) {
            BlogTag blogTag = new BlogTag();
            blogTag.setTagName(tagName);
            return blogTagMapper.insertSelective(blogTag) > 0;
        }
        return false;
    }

    @Override
    public Boolean deleteBatch(Integer[] ids) {
        //已存在关联关系不删除
        List<Long> relations = relationMapper.selectDistinctTagIds(ids);
        if (!CollectionUtils.isEmpty(relations)) {
            return false;
        }
        //删除tag
        return blogTagMapper.deleteBatch(ids) > 0;
    }

	@Override
	public List<BlogTagCount> getBlogTagCountForIndex() {
		return blogTagMapper.getTagCount();
	}
    
    
    
}
