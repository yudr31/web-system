package com.spring.boot.web.manager;

import com.spring.boot.common.util.CookieUtils;
import com.spring.boot.feign.pojo.permission.vo.LoginUserVO;
import com.spring.boot.web.service.PermissionRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yuderen
 * @version 2018/9/11 16:31
 */
@Component
public class UserManager {

    public static final String AUTH_KEY = "auth_key";
    public static final String AUTH_LOGIN_TOKEN = "auth_login_token";  //cookie author key
    public static final Integer AUTH_LOGIN_TOKEN_EXPIRY = 3 * 10 * 60;
    @Autowired
    private PermissionRoleService permissionRoleService;

    private static Map<String, LoginUserVO> tokenMap = new ConcurrentHashMap();

    /**
     * 判断是否登录
     *
     * @return
     */
    public boolean isLoggedIn() {
        LoginUserVO loginUserVO = this.getLoginUserVO();
        return (null != loginUserVO && loginUserVO.getIsLogined());
    }

    /**
     * 获取登录用户信息
     *
     * @return
     */
    public LoginUserVO getLoginUserVO() {
        return this.getLoginUserVOByRedis();
    }

    public void setUnifiedUserToRedis(LoginUserVO loginUserVO, String authToken){
//        RedisUtil.setObject(authToken, unifiedUser,AUTH_LOGIN_TOKEN_EXPIRY);
        tokenMap.put(authToken, loginUserVO);
    }

    public LoginUserVO getLoginUserVOByRedis() {
        String authToken = this.getAuthLoginToken();
        if (StringUtils.isEmpty(authToken)){
            return null;
        }
        return tokenMap.get(authToken);
//        return RedisUtil.getObject(authToken,UnifiedUser.class);
    }

    public String getAuthLoginToken() {
        String authToken = CookieUtils.getCookieString(AUTH_LOGIN_TOKEN);
        try {
            if(StringUtils.isNotEmpty(authToken)) {
                authToken = URLDecoder.decode(authToken, "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return authToken;
    }

    /**
     * 登录token验证
     * @return
     */
    public Boolean authLoginResponse() {
        Boolean isAuthLogin = false;
        String token = this.getAuthLoginToken();
        if(StringUtils.isNotEmpty(token) && isLoggedIn()) {
            isAuthLogin = true;
            this.responseAuthLoginToken(token); //重置token失效时间
        }
        return isAuthLogin;
    }

    /**
     * 保存登录authLoginToken信息到Cookie
     * @param authToken
     */
    public void responseAuthLoginToken(String authToken) {
        CookieUtils.addCookie(AUTH_LOGIN_TOKEN,authToken,AUTH_LOGIN_TOKEN_EXPIRY,"");
    }

    public void logout() {
        /*if (this.isLoggedIn()) {
            userService.logout(User.LogoutRequest.newBuilder().setUserId(this.getUserId()).build());
        }
        HttpSessionUtils.remove();*/
        if(this.isLoggedIn()) {
            this.removeAuthLoginToken();
        }
    }

    /**
     * 清除Cookie中的Auth信息
     */
    public void removeAuthLoginToken() {
        CookieUtils.cancleCookie(AUTH_LOGIN_TOKEN, "");
    }

}
