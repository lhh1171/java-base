package com.cjs.sso.mapper;

import com.cjs.sso.entity.SysUser;
import org.apache.ibatis.annotations.*;

@Mapper
public interface SysUserMapper {
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "nickname", column = "nickname"),
            @Result(property = "email", column = "email"),
            @Result(property = "status", column = "status"),
            @Result(property = "createUser", column = "createUser"),
            @Result(property = "createTime", column = "createTime"),
            @Result(property = "updateUser", column = "updateUser"),
            @Result(property = "updateTime", column = "updateTime")
    })
    @Select("SELECT * FROM sys_user WHERE username=#{username}")
    SysUser findByUsername(@Param("username") String username);

}
