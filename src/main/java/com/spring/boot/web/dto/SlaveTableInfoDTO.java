package com.spring.boot.web.dto;

import lombok.Data;

/**
 * @author yuderen
 * @version 2017/10/31 15:31
 */
@Data
public class SlaveTableInfoDTO extends NnkTableInfoDTO {

    private String linkItem;        // 与主表关联属性

}
