package com.spring.boot.web.proxy;

import com.spring.boot.common.bean.ResponseData;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yuderen
 * @version 2019/7/12 17:20
 */
@Slf4j
public class BaseServiceProxy {

    protected <T> T successData(ResponseData<T> result){
        if ("0".equals(result.getCode())){
            return result.getData();
        }
        log.info(result.getMsg());
        return null;
    }

}
