package com.hwsafe.template.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hwsafe.template.controller.domain.TableEntity;

@RestController
public class OracleToMysqlController {

	/**
	 * @return 获取所有表名
	 */
	List<String> oraleTableList() {
		return null;
	}

	/**
	 * @return 表实体类
	 */
	List<TableEntity> tableMessage() {

		return null;
	}

	// 1获取oracle 所有表
	// 2 获取表结构 字段名称 类型 限制条件 是否必填 主键 注释
	// 3 获取JAVA类
	// 4 生成相应Mysql语句
	@RequestMapping("/oracleToMysql")
	public String oracleToMysql() {

		return null;
	}

}
