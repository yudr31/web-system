package com.spring.boot.web.service;

import com.github.pagehelper.PageInfo;
import com.spring.boot.feign.pojo.permission.PermissionUser;
import com.spring.boot.web.proxy.PermissionUserServiceProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yuderen
 * @version 2019-7-18 16:46:21
 */
@Slf4j
@Service
public class PermissionUserService {

    @Autowired
    private PermissionUserServiceProxy permissionUserServiceProxy;

    public PageInfo<PermissionUser> permissionUserPage(PermissionUser permissionUser) {
        return permissionUserServiceProxy.permissionUserPage(permissionUser);
    }

    public List<PermissionUser> permissionUserList(PermissionUser permissionUser) {
        return permissionUserServiceProxy.permissionUserList(permissionUser);
    }

    public Integer addPermissionUser(PermissionUser permissionUser) {
        return permissionUserServiceProxy.addPermissionUser(permissionUser);
    }

    public Integer updatePermissionUser(PermissionUser permissionUser) {
        return permissionUserServiceProxy.updatePermissionUser(permissionUser);
    }

    public PermissionUser permissionUserDetail(Long gid) {
        return permissionUserServiceProxy.permissionUserDetail(gid);
    }

    public Integer removePermissionUser(Long gid) {
        return permissionUserServiceProxy.removePermissionUser(gid);
    }

}
