package com.cjs.sso.entity;

import lombok.Data;

import java.io.Serializable;


@Data
public class SysUserRole implements Serializable {
    private static final long serialVersionUID = -1810195806444298544L;

    private Integer id;

    private Integer userId;

    private Integer roleId;
}
