package com.spring.boot.web.mapper;


import com.spring.boot.common.bean.BaseBeanMapper;
import com.spring.boot.feign.pojo.web.DataDict;

import java.util.List;


public interface DataDictMapper extends BaseBeanMapper<DataDict> {

    List<DataDict> fetchDataDictListGroupDictType();

}
