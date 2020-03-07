package com.xu.kinggame.service;

import java.util.List;
import java.util.Map;

import com.xu.kinggame.entity.BlogLink;
import com.xu.kinggame.util.PageQueryUtil;
import com.xu.kinggame.util.PageResult;

public interface LinkService {

	PageResult selectLink(PageQueryUtil pageQueryUtil);

	BlogLink selectLinkById(Integer linkId);

	boolean addlink(BlogLink link);

	boolean deleteLink(Integer[] ids);

	boolean updatelink(BlogLink link);
	
	Map<Byte, List<BlogLink>> getLinksForLinkPage();

}
