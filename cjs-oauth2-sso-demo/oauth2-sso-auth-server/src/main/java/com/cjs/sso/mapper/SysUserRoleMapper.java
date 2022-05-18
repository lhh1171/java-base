package com.cjs.sso.mapper;

import com.cjs.sso.entity.SysUserRole;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SysUserRoleMapper {
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "roleId", column = "role_id")
    })
    @Select("SELECT * FROM sys_user_role WHERE user_id=#{userId}")
    List<SysUserRole> findByUserId(@Param("userId") Integer userId);
}
