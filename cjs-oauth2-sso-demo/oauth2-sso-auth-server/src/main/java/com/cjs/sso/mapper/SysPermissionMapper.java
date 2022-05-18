package com.cjs.sso.mapper;

import com.cjs.sso.entity.SysPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface SysPermissionMapper {
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "pid", column = "pid"),
            @Result(property = "type", column = "type"),
            @Result(property = "name", column = "name"),
            @Result(property = "code", column = "code"),
            @Result(property = "uri", column = "uri"),
            @Result(property = "seq", column = "seq"),
            @Result(property = "createUser", column = "createUser"),
            @Result(property = "createTime", column = "createTime"),
            @Result(property = "updateUser", column = "updateUser"),
            @Result(property = "updateTime", column = "updateTime")
    })

    @Select("<script> SELECT * FROM sys_permission WHERE id IN"+
            "<foreach item='id' index='index' collection='list' open='(' separator=',' close=')'> #{id} </foreach></script>"
    )
    List<SysPermission> findByIds(List<Integer> ids);
}
