package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.JianzhusukanEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.JianzhusukanVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.JianzhusukanView;


/**
 * 建筑速看
 *
 * @author 
 * @email 
 * @date 2023-03-04 11:25:49
 */
public interface JianzhusukanService extends IService<JianzhusukanEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<JianzhusukanVO> selectListVO(Wrapper<JianzhusukanEntity> wrapper);
   	
   	JianzhusukanVO selectVO(@Param("ew") Wrapper<JianzhusukanEntity> wrapper);
   	
   	List<JianzhusukanView> selectListView(Wrapper<JianzhusukanEntity> wrapper);
   	
   	JianzhusukanView selectView(@Param("ew") Wrapper<JianzhusukanEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<JianzhusukanEntity> wrapper);
   	

}

