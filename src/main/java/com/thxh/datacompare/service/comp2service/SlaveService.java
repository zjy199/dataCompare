package com.thxh.datacompare.service.comp2service;

import java.util.HashMap;
import java.util.List;

public interface SlaveService {

    int selectSlaveCount(String tableName);

    List<HashMap<String,Object>> querySlaveDetail(String tableName);

    HashMap<String,Object> querySlaveTableDetailByPrimary(String tableName,String slavePrimaryKey,String slavePrimaryValue);

    List<HashMap<String,Object>> querySlaveStructure(String slaveTableName,String databaseName);
}
