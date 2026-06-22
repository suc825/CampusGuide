package com.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.lang.reflect.InvocationTargetException;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.beanutils.BeanUtils;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;


/**
 * 建筑速看
 * 数据库通用操作实体类（普通增删改查）
 * @author 
 * @email 
 * @date 2023-03-04 11:25:49
 */
@TableName("jianzhusukan")
public class JianzhusukanEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public JianzhusukanEntity() {
		
	}
	
	public JianzhusukanEntity(T t) {
		try {
			BeanUtils.copyProperties(this, t);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 主键id
	 */
	@TableId
	private Long id;
	/**
	 * 建筑名称
	 */
					
	private String jianzhumingcheng;
	
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
				
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
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
	
	
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date addtime;

	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 设置：建筑名称
	 */
	public void setJianzhumingcheng(String jianzhumingcheng) {
		this.jianzhumingcheng = jianzhumingcheng;
	}
	/**
	 * 获取：建筑名称
	 */
	public String getJianzhumingcheng() {
		return jianzhumingcheng;
	}
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
