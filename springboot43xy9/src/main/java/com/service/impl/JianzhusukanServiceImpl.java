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


import com.dao.JianzhusukanDao;
import com.entity.JianzhusukanEntity;
import com.service.JianzhusukanService;
import com.entity.vo.JianzhusukanVO;
import com.entity.view.JianzhusukanView;

@Service("jianzhusukanService")
public class JianzhusukanServiceImpl extends ServiceImpl<JianzhusukanDao, JianzhusukanEntity> implements JianzhusukanService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<JianzhusukanEntity> page = this.selectPage(
                new Query<JianzhusukanEntity>(params).getPage(),
                new EntityWrapper<JianzhusukanEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<JianzhusukanEntity> wrapper) {
		  Page<JianzhusukanView> page =new Query<JianzhusukanView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}
    
    @Override
	public List<JianzhusukanVO> selectListVO(Wrapper<JianzhusukanEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public JianzhusukanVO selectVO(Wrapper<JianzhusukanEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<JianzhusukanView> selectListView(Wrapper<JianzhusukanEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public JianzhusukanView selectView(Wrapper<JianzhusukanEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
