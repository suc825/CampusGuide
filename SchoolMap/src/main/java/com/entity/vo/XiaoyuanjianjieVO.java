package com.entity.vo;

import com.entity.XiaoyuanjianjieEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;


/**
 * 校园简介
 * 手机端接口返回实体辅助类
 * @author
 * @email
 * @date 2023-03-04 11:25:49
 */
public class XiaoyuanjianjieVO  implements Serializable {
	private static final long serialVersionUID = 1L;


	/**
	 * 封面
	 */
	private String fengmian;

	/**
	 * 校园简介内容
	 */
	private String xuexiaojianjie;


	/**
	 * 设置：封面
	 */
	public void setFengmian(String fengmian) {
		this.fengmian = fengmian;
	}

	/**
	 * 获取：封面
	 */
	public String getFengmian() {
		return fengmian;
	}


	/**
	 * 设置：校园简介内容
	 */
	public void setXuexiaojianjie(String xuexiaojianjie) {
		this.xuexiaojianjie = xuexiaojianjie;
	}

	/**
	 * 获取：校园简介内容
	 */
	public String getXuexiaojianjie() {
		return xuexiaojianjie;
	}

}
