package com.spring.boot.web.proxy;

import com.github.pagehelper.PageInfo;
import com.spring.boot.feign.api.permission.PermissionUserClient;
import com.spring.boot.feign.pojo.permission.PermissionUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yuderen
 * @version 2019-7-18 16:46:01
 */
@Service
public class PermissionUserServiceProxy extends BaseServiceProxy {

    @Autowired
    private PermissionUserClient permissionUserClient;

    public PageInfo<PermissionUser> permissionUserPage(PermissionUser permissionUser) {
        return successData(permissionUserClient.permissionUserPage(permissionUser));
    }

    public List<PermissionUser> permissionUserList(PermissionUser permissionUser) {
        return successData(permissionUserClient.permissionUserList(permissionUser));
    }

    public Integer addPermissionUser(PermissionUser permissionUser) {
        return successData(permissionUserClient.addPermissionUser(permissionUser));
    }

    public Integer updatePermissionUser(PermissionUser permissionUser) {
        return successData(permissionUserClient.updatePermissionUser(permissionUser));
    }

    public PermissionUser permissionUserDetail(Long gid) {
        return successData(permissionUserClient.permissionUserDetail(gid));
    }

    public Integer removePermissionUser(Long gid) {
        return successData(permissionUserClient.removePermissionUser(gid));
    }
}