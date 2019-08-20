package com.spring.boot.web.service;

import com.github.pagehelper.PageInfo;
import com.spring.boot.feign.pojo.permission.PermissionRole;
import com.spring.boot.web.proxy.PermissionRoleServiceProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yuderen
 * @version 2019-7-18 16:44:35
 */
@Slf4j
@Service
public class PermissionRoleService {

    @Autowired
    private PermissionRoleServiceProxy permissionRoleServiceProxy;

    public PageInfo<PermissionRole> permissionRolePage(PermissionRole permissionRole) {
        return permissionRoleServiceProxy.permissionRolePage(permissionRole);
    }

    public List<PermissionRole> permissionRoleList(PermissionRole permissionRole) {
        return permissionRoleServiceProxy.permissionRoleList(permissionRole);
    }

    public Integer addPermissionRole(PermissionRole permissionRole) {
        return permissionRoleServiceProxy.addPermissionRole(permissionRole);
    }

    public Integer updatePermissionRole(PermissionRole permissionRole) {
        return permissionRoleServiceProxy.updatePermissionRole(permissionRole);
    }

    public PermissionRole permissionRoleDetail(Long gid) {
        return permissionRoleServiceProxy.permissionRoleDetail(gid);
    }

    public Integer removePermissionRole(Long gid) {
        return permissionRoleServiceProxy.removePermissionRole(gid);
    }

}
