package com.entity.vo;

import com.entity.JianzhusukanEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
 

/**
 * 建筑速看
 * 手机端接口返回实体辅助类 
 * （主要作用去除一些不必要的字段）
 * @author 
 * @email 
 * @date 2023-03-04 11:25:49
 */
public class JianzhusukanVO  implements Serializable {
	private static final long serialVersionUID = 1L;

	 			
	/**
	 * 照片
	 */
	
	private String zhaopian;
		
	/**
	 * 楼层数
	 */
	
	private String loucengshu;
		
	/**
	 * 建筑面积(㎡)
	 */
	
	private Float jianzhumianji;
		
	/**
	 * 地点
	 */
	
	private String didian;
		
	/**
	 * 建立时间
	 */
		
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat 
	private Date jianlishijian;
		
	/**
	 * 使用用途
	 */
	
	private String shiyongyongtu;
		
	/**
	 * 建筑介绍
	 */
	
	private String jianzhujieshao;
				
	
	/**
	 * 设置：照片
	 */
	 
	public void setZhaopian(String zhaopian) {
		this.zhaopian = zhaopian;
	}
	
	/**
	 * 获取：照片
	 */
	public String getZhaopian() {
		return zhaopian;
	}
				
	
	/**
	 * 设置：楼层数
	 */
	 
	public void setLoucengshu(String loucengshu) {
		this.loucengshu = loucengshu;
	}
	
	/**
	 * 获取：楼层数
	 */
	public String getLoucengshu() {
		return loucengshu;
	}
				
	
	/**
	 * 设置：建筑面积(㎡)
	 */
	 
	public void setJianzhumianji(Float jianzhumianji) {
		this.jianzhumianji = jianzhumianji;
	}
	
	/**
	 * 获取：建筑面积(㎡)
	 */
	public Float getJianzhumianji() {
		return jianzhumianji;
	}
				
	
	/**
	 * 设置：地点
	 */
	 
	public void setDidian(String didian) {
		this.didian = didian;
	}
	
	/**
	 * 获取：地点
	 */
	public String getDidian() {
		return didian;
	}
				
	
	/**
	 * 设置：建立时间
	 */
	 
	public void setJianlishijian(Date jianlishijian) {
		this.jianlishijian = jianlishijian;
	}
	
	/**
	 * 获取：建立时间
	 */
	public Date getJianlishijian() {
		return jianlishijian;
	}
				
	
	/**
	 * 设置：使用用途
	 */
	 
	public void setShiyongyongtu(String shiyongyongtu) {
		this.shiyongyongtu = shiyongyongtu;
	}
	
	/**
	 * 获取：使用用途
	 */
	public String getShiyongyongtu() {
		return shiyongyongtu;
	}
				
	
	/**
	 * 设置：建筑介绍
	 */
	 
	public void setJianzhujieshao(String jianzhujieshao) {
		this.jianzhujieshao = jianzhujieshao;
	}
	
	/**
	 * 获取：建筑介绍
	 */
	public String getJianzhujieshao() {
		return jianzhujieshao;
	}
			
}
