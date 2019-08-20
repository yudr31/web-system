package com.spring.boot.web.service;

import com.github.pagehelper.PageInfo;
import com.spring.boot.feign.pojo.permission.PermissionResource;
import com.spring.boot.web.proxy.PermissionResourceServiceProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yuderen
 * @version 2019-7-18 16:47:53
 */
@Slf4j
@Service
public class PermissionResourceService {

    @Autowired
    private PermissionResourceServiceProxy permissionResourceServiceProxy;

    public PageInfo<PermissionResource> permissionResourcePage(PermissionResource permissionResource) {
        return permissionResourceServiceProxy.permissionResourcePage(permissionResource);
    }

    public List<PermissionResource> permissionResourceList(PermissionResource permissionResource) {
        return permissionResourceServiceProxy.permissionResourceList(permissionResource);
    }

    public Integer addPermissionResource(PermissionResource permissionResource) {
        return permissionResourceServiceProxy.addPermissionResource(permissionResource);
    }

    public Integer updatePermissionResource(PermissionResource permissionResource) {
        return permissionResourceServiceProxy.updatePermissionResource(permissionResource);
    }

    public PermissionResource permissionResourceDetail(Long gid) {
        return permissionResourceServiceProxy.permissionResourceDetail(gid);
    }

    public Integer removePermissionResource(Long gid) {
        return permissionResourceServiceProxy.removePermissionResource(gid);
    }

}
