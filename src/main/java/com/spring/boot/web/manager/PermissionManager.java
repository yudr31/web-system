package com.spring.boot.web.manager;

import com.spring.boot.common.annotation.permission.PermissionLimit;
import com.spring.boot.common.util.JsonUtil;
import com.spring.boot.feign.pojo.permission.vo.PermissionMenuVO;
import com.spring.boot.web.util.NumberUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author yuderen
 * @version 2018/9/18 17:30
 */
@Component
public class PermissionManager {

    private static final Logger logger = LoggerFactory.getLogger(PermissionManager.class);
    public static final String RESOURCE = "resourceList";
    @Autowired
    private UserManager userManager;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void setPermissionResourceByRedis(List<PermissionMenuVO> resourceList) {
        String authorToken = userManager.getAuthLoginToken();
        stringRedisTemplate.opsForValue().set(getTokenKey(authorToken), JsonUtil.toString(resourceList), UserManager.AUTH_LOGIN_TOKEN_EXPIRY, TimeUnit.SECONDS);
    }

    public List<PermissionMenuVO> getPermissionResourceByRedis() {
        String token = userManager.getAuthLoginToken();
        String result = stringRedisTemplate.opsForValue().get(getTokenKey(token));
        if (null != result){
            return null;
        }
        List<PermissionMenuVO> resourceList = JsonUtil.toListObject(result, PermissionMenuVO.class);
        return resourceList;
    }

    private String getTokenKey(String authorToken){
        return RESOURCE + authorToken;
    }

    /**
     * 验证用户权限
     *
     * @param servletPath
     * @return
     */
    public boolean verifyPermissionByHref(String servletPath) {
        //默认权限通过
        boolean verifyResult = true;
        for (PermissionMenuVO resource : this.getPermissionResourceByRedis()) {
            verifyResult = servletPath.equals(resource.getMenuLink());
        }
        //判断是否超级管理员
        if (!verifyResult) {
            verifyResult = userManager.getLoginUserVO().getIsLogined();
        }
        return verifyResult;
    }

    /**
     * 验证用户权限
     *
     * @param permissionLimit
     * @return
     */
    public boolean verifyPermissionByHref(PermissionLimit permissionLimit) {
        //默认权限通过
        boolean verifyResult = false;
        PermissionMenuVO itemMenu = fetchTargetItemMenu(this.getPermissionResourceByRedis(), permissionLimit.menuLink());
        if (null != itemMenu){
            if (null == itemMenu.getPermission()){
                return true;
            }
            List<Integer> authList = NumberUtil.splitNumber(itemMenu.getPermission());
            verifyResult = authList.contains(permissionLimit.permissionType().getValue());
        }

        //判断是否超级管理员
        if (!verifyResult) {
            verifyResult = userManager.getLoginUserVO().getIsLogined();
        }
        return verifyResult;
    }

    private PermissionMenuVO fetchTargetItemMenu(List<PermissionMenuVO> itemMenuList, String menuLink){
        if (null != itemMenuList){
            for (PermissionMenuVO resource : itemMenuList) {
                if (menuLink.equals(resource.getMenuLink())){
                    return resource;
                }
                if (!resource.getChildren().isEmpty()){
                    PermissionMenuVO result = fetchTargetItemMenu(resource.getChildren(), menuLink);
                    if (null != result){
                        return result;
                    }
                }
            }
        }
        return null;
    }

}
