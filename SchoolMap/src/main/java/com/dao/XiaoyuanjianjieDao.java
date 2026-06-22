package com.dao;

import com.entity.XiaoyuanjianjieEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.XiaoyuanjianjieVO;
import com.entity.view.XiaoyuanjianjieView;


/**
 * 校园简介
 * 
 * @author 
 * @email 
 * @date 2023-03-04 11:25:49
 */
public interface XiaoyuanjianjieDao extends BaseMapper<XiaoyuanjianjieEntity> {
	
	List<XiaoyuanjianjieVO> selectListVO(@Param("ew") Wrapper<XiaoyuanjianjieEntity> wrapper);
	
	XiaoyuanjianjieVO selectVO(@Param("ew") Wrapper<XiaoyuanjianjieEntity> wrapper);
	
	List<XiaoyuanjianjieView> selectListView(@Param("ew") Wrapper<XiaoyuanjianjieEntity> wrapper);

	List<XiaoyuanjianjieView> selectListView(Pagination page,@Param("ew") Wrapper<XiaoyuanjianjieEntity> wrapper);
	
	XiaoyuanjianjieView selectView(@Param("ew") Wrapper<XiaoyuanjianjieEntity> wrapper);
	

}
