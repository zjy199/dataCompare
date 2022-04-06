package com.thxh.datacompare.service.comp1service;

import java.util.HashMap;
import java.util.List;

public interface MasterService {

    int selectMasterCount(String tableName);

    List<HashMap<String,Object>> queryMasterDetail(String tableName);

    HashMap<String,Object> queryMasterTableDetailByPrimary(String tableName,String masterPrimaryKey,String masterPrimaryValue);

    List<HashMap<String,Object>> queryMasterStructure(String masterTableName,String databaseName);
}
