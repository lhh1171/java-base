package com.cjs.sso.entity;

import lombok.Data;


import java.io.Serializable;


@Data
public class SysRolePermission implements Serializable {
    private static final long serialVersionUID = 7402412601579098788L;


    private Integer id;


    private Integer roleId;

    private Integer permissionId;
}
