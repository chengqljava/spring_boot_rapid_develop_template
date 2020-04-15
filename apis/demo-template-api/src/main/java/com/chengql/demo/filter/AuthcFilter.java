package com.hwsafe.demo.filter;

public class AuthcFilter extends AuthcAbstractFilter {

    @Override
    public String isSysUserValid(String userid) {
        // SysUserService sysUserService = (SysUserService) SpringContextHolder
        // .getBean("sysUserService");
        // SysUser sysUser=sysUserService.getById(userid);
        return null;
    }

}
