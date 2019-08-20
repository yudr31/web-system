package com.spring.boot.web.proxy;

import com.github.pagehelper.PageInfo;
import com.spring.boot.feign.api.permission.PermissionRoleClient;
import com.spring.boot.feign.pojo.permission.PermissionRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yuderen
 * @version 2019-7-18 16:43:22
 */
@Service
public class PermissionRoleServiceProxy extends BaseServiceProxy {

    @Autowired
    private PermissionRoleClient permissionRoleClient;

    public PageInfo<PermissionRole> permissionRolePage(PermissionRole permissionRole) {
        return successData(permissionRoleClient.permissionRolePage(permissionRole));
    }

    public List<PermissionRole> permissionRoleList(PermissionRole permissionRole) {
        return successData(permissionRoleClient.permissionRoleList(permissionRole));
    }

    public Integer addPermissionRole(PermissionRole permissionRole) {
        return successData(permissionRoleClient.addPermissionRole(permissionRole));
    }

    public Integer updatePermissionRole(PermissionRole permissionRole) {
        return successData(permissionRoleClient.updatePermissionRole(permissionRole));
    }

    public PermissionRole permissionRoleDetail(Long gid) {
        return successData(permissionRoleClient.permissionRoleDetail(gid));
    }

    public Integer removePermissionRole(Long gid) {
        return successData(permissionRoleClient.removePermissionRole(gid));
    }
}