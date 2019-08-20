package com.spring.boot.web.proxy;

import com.github.pagehelper.PageInfo;
import com.spring.boot.feign.api.permission.PermissionMenuClient;
import com.spring.boot.feign.pojo.permission.PermissionMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yuderen
 * @version 2019-7-17 16:44:52
 */
@Service
public class PermissionMenuServiceProxy extends BaseServiceProxy {

    @Autowired
    private PermissionMenuClient permissionMenuClient;

    public PageInfo<PermissionMenu> permissionMenuPage(PermissionMenu permissionMenu) {
        return successData(permissionMenuClient.permissionMenuPage(permissionMenu));
    }

    public List<PermissionMenu> permissionMenuList(PermissionMenu permissionMenu) {
        return successData(permissionMenuClient.permissionMenuList(permissionMenu));
    }

    public Integer addPermissionMenu(PermissionMenu permissionMenu) {
        return successData(permissionMenuClient.addPermissionMenu(permissionMenu));
    }

    public Integer updatePermissionMenu(PermissionMenu permissionMenu) {
        return successData(permissionMenuClient.updatePermissionMenu(permissionMenu));
    }

    public PermissionMenu permissionMenuDetail(Long gid) {
        return successData(permissionMenuClient.permissionMenuDetail(gid));
    }

    public Integer removePermissionMenu(Long gid) {
        return successData(permissionMenuClient.removePermissionMenu(gid));
    }
}