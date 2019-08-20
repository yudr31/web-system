package com.spring.boot.web.proxy;

import com.spring.boot.feign.api.permission.PermissionInfoClient;
import com.spring.boot.feign.pojo.permission.vo.PermissionMenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yuderen
 * @version 2019/7/23 10:54
 */
@Component
public class PermissionInfoProxy extends BaseServiceProxy {

    @Autowired
    private PermissionInfoClient permissionInfoClient;

    public List<PermissionMenuVO> fetchPermissionMenuList(Long userId) {
        return successData(permissionInfoClient.fetchPermissionMenuList(userId));
    }

}
