package com.cjs.sso.service.impl;

import com.cjs.sso.entity.SysUser;
import com.cjs.sso.mapper.SysUserMapper;
import com.cjs.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class UserServiceImpl implements UserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser getByUsername(String username) {
        return sysUserMapper.findByUsername(username);
    }
}
