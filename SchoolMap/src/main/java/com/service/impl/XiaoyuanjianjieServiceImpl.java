package com.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.List;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.utils.PageUtils;
import com.utils.Query;


import com.dao.XiaoyuanjianjieDao;
import com.entity.XiaoyuanjianjieEntity;
import com.service.XiaoyuanjianjieService;
import com.entity.vo.XiaoyuanjianjieVO;
import com.entity.view.XiaoyuanjianjieView;

@Service("xiaoyuanjianjieService")
public class XiaoyuanjianjieServiceImpl extends ServiceImpl<XiaoyuanjianjieDao, XiaoyuanjianjieEntity> implements XiaoyuanjianjieService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<XiaoyuanjianjieEntity> page = this.selectPage(
                new Query<XiaoyuanjianjieEntity>(params).getPage(),
                new EntityWrapper<XiaoyuanjianjieEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<XiaoyuanjianjieEntity> wrapper) {
		  Page<XiaoyuanjianjieView> page =new Query<XiaoyuanjianjieView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}
    
    @Override
	public List<XiaoyuanjianjieVO> selectListVO(Wrapper<XiaoyuanjianjieEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public XiaoyuanjianjieVO selectVO(Wrapper<XiaoyuanjianjieEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<XiaoyuanjianjieView> selectListView(Wrapper<XiaoyuanjianjieEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public XiaoyuanjianjieView selectView(Wrapper<XiaoyuanjianjieEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
