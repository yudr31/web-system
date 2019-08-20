package com.spring.boot.web.proxy;

import com.github.pagehelper.PageInfo;
import com.spring.boot.feign.api.permission.AdminInfoClient;
import com.spring.boot.feign.pojo.permission.AdminInfo;
import com.spring.boot.feign.pojo.permission.vo.LoginUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yuderen
 * @version 2019/7/12 17:20
 */
@Service
public class AdminInfoServiceProxy extends BaseServiceProxy {

    @Autowired
    private AdminInfoClient adminInfoClient;

    public LoginUserVO login(AdminInfo adminInfo) {
        LoginUserVO result = successData(adminInfoClient.login(adminInfo));
        return result;
    }

    public PageInfo<AdminInfo> adminInfoPage(AdminInfo adminInfo) {
        return successData(adminInfoClient.adminInfoPage(adminInfo));
    }

    public List<AdminInfo> adminInfoList(AdminInfo adminInfo) {
        return successData(adminInfoClient.adminInfoList(adminInfo));
    }

    public Integer addAdminInfo(AdminInfo adminInfo) {
        return successData(adminInfoClient.addAdminInfo(adminInfo));
    }

    public Integer updateAdminInfo(AdminInfo adminInfo) {
        return successData(adminInfoClient.updateAdminInfo(adminInfo));
    }

    public AdminInfo adminInfoDetail(Long gid) {
        return successData(adminInfoClient.adminInfoDetail(gid));
    }

    public Integer removeAdminInfo(Long gid) {
        return successData(adminInfoClient.removeAdminInfo(gid));
    }
}
