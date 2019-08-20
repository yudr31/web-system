package com.spring.boot.web.service;

import com.spring.boot.feign.pojo.permission.vo.PermissionMenuVO;
import com.spring.boot.web.proxy.PermissionInfoProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yuderen
 * @version 2019/7/23 10:56
 */
@Service
public class PermissionInfoService {

    @Autowired
    private PermissionInfoProxy permissionInfoProxy;

    public List<PermissionMenuVO> fetchPermissionMenuList(Long userId) {
        return permissionInfoProxy.fetchPermissionMenuList(userId);
    }

}
