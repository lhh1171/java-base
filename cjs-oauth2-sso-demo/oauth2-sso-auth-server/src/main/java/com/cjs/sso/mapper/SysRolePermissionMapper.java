package com.cjs.sso.mapper;

import com.cjs.sso.entity.SysRolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface SysRolePermissionMapper {
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "roleId", column = "role_id"),
            @Result(property = "permissionId", column = "permission_id")
    })
    @Select("<script> SELECT * FROM sys_role_permission WHERE role_id IN"+
            "<foreach item='role_id' index='index' collection='list' open='(' separator=',' close=')'> #{role_id} </foreach></script>"
    )
    List<SysRolePermission> findByRoleIds(List<Integer> roleIds);
}
