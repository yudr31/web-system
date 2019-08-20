package com.spring.boot.web.service;

import com.github.pagehelper.PageInfo;
import com.spring.boot.feign.pojo.permission.PermissionMenu;
import com.spring.boot.web.proxy.PermissionMenuServiceProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yuderen
 * @version 2019-7-18 15:52:13
 */
@Slf4j
@Service
public class PermissionMenuService {

    @Autowired
    private PermissionMenuServiceProxy permissionMenuServiceProxy;

    public PageInfo<PermissionMenu> permissionMenuPage(PermissionMenu permissionMenu) {
        return permissionMenuServiceProxy.permissionMenuPage(permissionMenu);
    }

    public List<PermissionMenu> permissionMenuList(PermissionMenu permissionMenu) {
        return permissionMenuServiceProxy.permissionMenuList(permissionMenu);
    }

    public Integer addPermissionMenu(PermissionMenu permissionMenu) {
        return permissionMenuServiceProxy.addPermissionMenu(permissionMenu);
    }

    public Integer updatePermissionMenu(PermissionMenu permissionMenu) {
        return permissionMenuServiceProxy.updatePermissionMenu(permissionMenu);
    }

    public PermissionMenu permissionMenuDetail(Long gid) {
        return permissionMenuServiceProxy.permissionMenuDetail(gid);
    }

    public Integer removePermissionMenu(Long gid) {
        return permissionMenuServiceProxy.removePermissionMenu(gid);
    }

}
