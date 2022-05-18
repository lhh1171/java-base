package com.cjs.sso.vo;

import com.cjs.sso.entity.SysUser;
import lombok.Data;

import java.util.List;


@Data
public class SysUserVO extends SysUser {

    /**
     * 权限列表
     */
    private List<String> authorityList;

}
