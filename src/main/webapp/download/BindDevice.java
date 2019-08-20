package com.smartclothing.mix.bean;

import com.smartclothing.dbutils.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "绑定设备信息")
public class BindDevice extends BaseEntity{

    private String userId;
    private String productId;
    private String productName;
    private String macAddr;
    private String deviceNo;
    private String deviceName;
    private Integer linkStatus;
    private Date wakeTime;
    private Long onlineDuration;
    private Date lastOnlineTime;
    private String firmwareVersion;
    private String mcuVersion;
    private String city;

}