package com.cjs.sso.service;

import com.cjs.sso.entity.SysPermission;

import java.util.List;


public interface PermissionService {

    List<SysPermission> findByUserId(Integer userId);

}
