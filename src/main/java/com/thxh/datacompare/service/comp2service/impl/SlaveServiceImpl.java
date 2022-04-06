package com.thxh.datacompare.service.comp2service.impl;

import com.thxh.datacompare.dao.comp2dao.SlaveDao;
import com.thxh.datacompare.service.comp2service.SlaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author YYY
 * @date 2021年12月23日12:07
 */
@Service
public class SlaveServiceImpl implements SlaveService {

    @Autowired
    private SlaveDao slaveDao;

    @Override
    public int selectSlaveCount(String tableName) {

        int result = slaveDao.selectSlaveCount(tableName);

        return result;
    }

    @Override
    public List<HashMap<String, Object>> querySlaveDetail(String tableName) {
        List<HashMap<String,Object>> hashMapList = slaveDao.querySlaveDetail(tableName);
        return hashMapList;
    }

    @Override
    public HashMap<String, Object> querySlaveTableDetailByPrimary(String tableName,String slavePrimaryKey,String slavePrimaryValue) {
        HashMap<String,Object> hashMap = slaveDao.querySlaveTableDetailByPrimary(tableName,slavePrimaryKey,slavePrimaryValue);
        return hashMap;
    }

    @Override
    public List<HashMap<String, Object>> querySlaveStructure(String slaveTableName, String databaseName) {
        List<HashMap<String,Object>> hashMap = slaveDao.querySlaveStructure(slaveTableName,databaseName);
        return hashMap;
    }
}
