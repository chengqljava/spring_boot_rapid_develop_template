package com.hwsafe.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwsafe.demo.domain.Person;

public interface PersonMapper extends BaseMapper<Person> {

    List<Person> selectList(Page<Person> page);

    List<Person> selectListByName(Page<Person> page,
            @Param("name") String name);

    List<Person> list();

}
