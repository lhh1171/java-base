package com.cjs.sso.service;

import com.cjs.sso.entity.SysUser;


public interface UserService {

    SysUser getByUsername(String username);
}
