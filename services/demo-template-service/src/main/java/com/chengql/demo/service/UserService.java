package com.hwsafe.demo.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hwsafe.demo.domain.User;
import com.hwsafe.demo.mapper.UserMapper;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {
    public Integer myCount() {
        return baseMapper.myCount();
    }

    public List<User> getUserAndAddr() {
        return baseMapper.getUserAndAddr();
    }

    public List<User> getAddrAndUser() {
        return baseMapper.getAddrAndUser();
    }

    public Map<String, Object> getAddrAndUserMap() {
        return baseMapper.getAddrAndUserMap();
    }

    public Map<String, Object> userSchool() {
        return baseMapper.userSchool();
    }
}
