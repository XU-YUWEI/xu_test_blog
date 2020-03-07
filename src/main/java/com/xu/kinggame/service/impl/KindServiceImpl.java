package com.xu.kinggame.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xu.kinggame.dao.KindMapper;
import com.xu.kinggame.entity.Kind;
import com.xu.kinggame.service.KindService;
import com.xu.kinggame.util.PageQueryUtil;
import com.xu.kinggame.util.PageResult;

@Service
public class KindServiceImpl implements KindService{

	@Resource
	private KindMapper kindMapper;
	@Override
	public PageResult UserList(PageQueryUtil params) {
		List<Kind> list = kindMapper.selectUser(params);
		int count = kindMapper.selectCount(params);
		PageResult pageResult = new PageResult(list, count, params.getLimit(), params.getPage());
		/*System.out.println(pageResult);
		System.out.println("333");*/
		return pageResult;
	}
	@Override
	public boolean insertKind(String kindName) {
		Kind kind = kindMapper.selectKindByName(kindName);
		if(kind==null) {
			Kind kinds = new Kind();
			kinds.setKindTitle(kindName);
			return kindMapper.insertKind(kinds)>0;
		}
		return false;
	}
	@Override
	public boolean updateKind(String kindName, Long kindId) {
		Kind kind = kindMapper.selectKindById(kindId);
		/*System.out.println(kind);*/
		if(kind!=null) {
			
			kind.setKindTitle(kindName);
			return kindMapper.updateKind(kind)>0;
		}
		return false;
	}
	@Override
	public Kind selectById(Long kindId) {
		Kind kind = kindMapper.selectKindById(kindId);
		return kind;
	}
	@Override
	public boolean deleteKind(Integer[] ids) {
		if(kindMapper.deleteKind(ids)>0) 
		{
			return true;
		}else {
			return false;
		}
	}
	@Override
	public List<Kind> getAllKinds() {
		List<Kind> list = kindMapper.selectUser(null);
		return list;
	}

}
