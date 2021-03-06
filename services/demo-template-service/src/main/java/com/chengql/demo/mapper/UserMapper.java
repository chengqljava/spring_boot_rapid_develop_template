package com.chengql.demo.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chengql.demo.domain.User;

public interface UserMapper extends BaseMapper<User> {

    /**
     * 自定义SQL：默认也会增加多租户条件 参考打印的SQL
     * 
     * @return
     */
    public Integer myCount();

    public List<User> getUserAndAddr();

    public List<User> getAddrAndUser();

    public Map<String, Object> getAddrAndUserMap();

    public Map<String, Object> userSchool();
}
