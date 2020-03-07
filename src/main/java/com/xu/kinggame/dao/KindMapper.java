package com.xu.kinggame.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.xu.kinggame.entity.Kind;
import com.xu.kinggame.util.PageQueryUtil;

@Component
public interface KindMapper {

	List<Kind> selectUser(PageQueryUtil pageQueryUtil);

	int selectCount(PageQueryUtil params);

	Kind selectKindByName(String kindName);

	int insertKind(Kind kinds);

	Kind selectKindById(Long kindId);

	int updateKind(Kind kinds);


	int deleteKind(Integer[] ids);

}
