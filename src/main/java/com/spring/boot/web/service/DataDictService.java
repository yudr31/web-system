package com.spring.boot.web.service;

import com.spring.boot.common.bean.BaseBeanService;
import com.spring.boot.feign.pojo.web.DataDict;
import com.spring.boot.web.mapper.DataDictMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yuderen
 * @version 2018/9/12 14:13
 */
@Service
public class DataDictService extends BaseBeanService<DataDictMapper, DataDict> {

    @Autowired
    private DataDictMapper dataDictMapper;

    public List<DataDict> fetchDataDictListByType(String dictType){
        DataDict dataDict = new DataDict();
        dataDict.setDictType(dictType);
        return super.fetchRecordList(dataDict);
    }

    public List<DataDict> fetchDataDictListGroupDictType(){
        return dataDictMapper.fetchDataDictListGroupDictType();
    }

}
