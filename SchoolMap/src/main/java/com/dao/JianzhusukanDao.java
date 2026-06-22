package com.dao;

import com.entity.JianzhusukanEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.JianzhusukanVO;
import com.entity.view.JianzhusukanView;


/**
 * 建筑速看
 * 
 * @author 
 * @email 
 * @date 2023-03-04 11:25:49
 */
public interface JianzhusukanDao extends BaseMapper<JianzhusukanEntity> {
	
	List<JianzhusukanVO> selectListVO(@Param("ew") Wrapper<JianzhusukanEntity> wrapper);
	
	JianzhusukanVO selectVO(@Param("ew") Wrapper<JianzhusukanEntity> wrapper);
	
	List<JianzhusukanView> selectListView(@Param("ew") Wrapper<JianzhusukanEntity> wrapper);

	List<JianzhusukanView> selectListView(Pagination page,@Param("ew") Wrapper<JianzhusukanEntity> wrapper);
	
	JianzhusukanView selectView(@Param("ew") Wrapper<JianzhusukanEntity> wrapper);
	

}
