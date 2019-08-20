package com.spring.boot.web.service;

import com.github.pagehelper.PageInfo;
import com.spring.boot.feign.pojo.permission.AdminInfo;
import com.spring.boot.feign.pojo.permission.vo.LoginUserVO;
import com.spring.boot.web.proxy.AdminInfoServiceProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yuderen
 * @version 2019/7/12 18:15
 */
@Slf4j
@Service
public class AdminInfoService {

    @Autowired
    private AdminInfoServiceProxy adminInfoServiceProxy;

    public LoginUserVO login(AdminInfo adminInfo) {
        LoginUserVO loginUserVO = adminInfoServiceProxy.login(adminInfo);
        return loginUserVO;
    }

    public PageInfo<AdminInfo> adminInfoPage(AdminInfo adminInfo) {
        return adminInfoServiceProxy.adminInfoPage(adminInfo);
    }

    public List<AdminInfo> adminInfoList(AdminInfo adminInfo) {
        return adminInfoServiceProxy.adminInfoList(adminInfo);
    }

    public Integer addAdminInfo(AdminInfo adminInfo) {
        return adminInfoServiceProxy.addAdminInfo(adminInfo);
    }

    public Integer updateAdminInfo(AdminInfo adminInfo) {
        return adminInfoServiceProxy.updateAdminInfo(adminInfo);
    }

    public AdminInfo adminInfoDetail(Long gid) {
        return adminInfoServiceProxy.adminInfoDetail(gid);
    }

    public Integer removeAdminInfo(Long gid) {
        return adminInfoServiceProxy.removeAdminInfo(gid);
    }

}
