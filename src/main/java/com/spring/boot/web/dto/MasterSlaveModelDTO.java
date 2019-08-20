package com.spring.boot.web.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuderen
 * @version 2018/9/12 15:23
 */
@Data
public class MasterSlaveModelDTO {

    private SingleTableInfoDTO master;
    private List<SlaveTableInfoDTO> slave = new ArrayList();

}
