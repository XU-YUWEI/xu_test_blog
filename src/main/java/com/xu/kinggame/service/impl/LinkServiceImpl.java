package com.xu.kinggame.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.xu.kinggame.dao.LinkMapper;
import com.xu.kinggame.entity.BlogLink;
import com.xu.kinggame.service.LinkService;
import com.xu.kinggame.util.PageQueryUtil;
import com.xu.kinggame.util.PageResult;
@Service
public class LinkServiceImpl implements LinkService {

	@Autowired
	private LinkMapper linkMapper;
	
	@Override
	public PageResult selectLink(PageQueryUtil pageQueryUtil) {
		List<BlogLink> list = linkMapper.selectLink(pageQueryUtil);
		int count = linkMapper.selectCount(pageQueryUtil);
		PageResult pageResult = new PageResult(list, count, pageQueryUtil.getLimit(), pageQueryUtil.getPage());
		return pageResult;
	}

	@Override
	public BlogLink selectLinkById(Integer linkId) {
		return linkMapper.selectLinkById(linkId);
	}

	@Override
	public boolean addlink(BlogLink link) {
		return linkMapper.addlink(link)>0;
	}

	@Override
	public boolean deleteLink(Integer[] ids) {
		return linkMapper.deleteLink(ids)>0;
	}

	@Override
	public boolean updatelink(BlogLink link) {
		return linkMapper.updatelink(link)>0;
	}

	@Override
	public Map<Byte, List<BlogLink>> getLinksForLinkPage() {
		List<BlogLink> lists = linkMapper.selectLink(null);
		if(!CollectionUtils.isEmpty(lists)) {
			//用type进行分组
			 Map<Byte, List<BlogLink>> listMap = lists.stream().collect(Collectors.groupingBy(BlogLink::getLinkType));
			 return listMap;
		}
		return null;
	}

}
