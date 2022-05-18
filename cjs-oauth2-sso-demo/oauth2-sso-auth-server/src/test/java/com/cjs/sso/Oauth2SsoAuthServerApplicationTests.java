package com.cjs.sso;

import com.cjs.sso.entity.SysPermission;
import com.cjs.sso.entity.SysUser;
import com.cjs.sso.service.impl.PermissionServiceImpl;
import com.cjs.sso.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Oauth2SsoAuthServerApplicationTests {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private PermissionServiceImpl permissionService;
    @Test
    public void contextLoads() {
        SysUser admin = userService.getByUsername("admin");
        System.out.println("fdsafsd");
        List<SysPermission> byUserId = permissionService.findByUserId(1);
        System.out.println("dfsafsd");
    }
}

