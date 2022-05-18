package com.cjs.sso.entity;

import lombok.Data;


import java.io.Serializable;
import java.util.Date;


@Data
public class SysPermission implements Serializable {
    private static final long serialVersionUID = 4285835478693487481L;

    private Integer id;

    private Integer pid;

    private Integer type;


    private String name;


    private String code;


    private String uri;


    private Integer seq = 1;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

}
