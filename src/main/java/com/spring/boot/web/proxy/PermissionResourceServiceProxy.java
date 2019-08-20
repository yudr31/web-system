package com.spring.boot.web.proxy;

import com.github.pagehelper.PageInfo;
import com.spring.boot.feign.api.permission.PermissionResourceClient;
import com.spring.boot.feign.pojo.permission.PermissionResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yuderen
 * @version 2019-7-18 16:47:34
 */
@Service
public class PermissionResourceServiceProxy extends BaseServiceProxy {

    @Autowired
    private PermissionResourceClient permissionResourceClient;

    public PageInfo<PermissionResource> permissionResourcePage(PermissionResource permissionResource) {
        return successData(permissionResourceClient.permissionResourcePage(permissionResource));
    }

    public List<PermissionResource> permissionResourceList(PermissionResource permissionResource) {
        return successData(permissionResourceClient.permissionResourceList(permissionResource));
    }

    public Integer addPermissionResource(PermissionResource permissionResource) {
        return successData(permissionResourceClient.addPermissionResource(permissionResource));
    }

    public Integer updatePermissionResource(PermissionResource permissionResource) {
        return successData(permissionResourceClient.updatePermissionResource(permissionResource));
    }

    public PermissionResource permissionResourceDetail(Long gid) {
        return successData(permissionResourceClient.permissionResourceDetail(gid));
    }

    public Integer removePermissionResource(Long gid) {
        return successData(permissionResourceClient.removePermissionResource(gid));
    }
}