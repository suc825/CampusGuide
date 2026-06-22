package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.XiaoyuanjianjieEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.XiaoyuanjianjieVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.XiaoyuanjianjieView;


/**
 * 校园简介
 *
 * @author 
 * @email 
 * @date 2023-03-04 11:25:49
 */
public interface XiaoyuanjianjieService extends IService<XiaoyuanjianjieEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<XiaoyuanjianjieVO> selectListVO(Wrapper<XiaoyuanjianjieEntity> wrapper);
   	
   	XiaoyuanjianjieVO selectVO(@Param("ew") Wrapper<XiaoyuanjianjieEntity> wrapper);
   	
   	List<XiaoyuanjianjieView> selectListView(Wrapper<XiaoyuanjianjieEntity> wrapper);
   	
   	XiaoyuanjianjieView selectView(@Param("ew") Wrapper<XiaoyuanjianjieEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<XiaoyuanjianjieEntity> wrapper);
   	

}

