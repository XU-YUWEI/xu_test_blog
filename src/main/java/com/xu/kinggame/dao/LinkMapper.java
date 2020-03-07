package com.xu.kinggame.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.xu.kinggame.entity.BlogLink;
import com.xu.kinggame.util.PageQueryUtil;
@Component
public interface LinkMapper {

	List<BlogLink> selectLink(PageQueryUtil pageQueryUtil);

	int selectCount(PageQueryUtil pageQueryUtil);

	BlogLink selectLinkById(Integer linkId);

	int addlink(BlogLink link);

	int deleteLink(Integer[] ids);

	int updatelink(BlogLink link);

}
