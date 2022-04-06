package com.thxh.datacompare.service.comp1service.impl;

import com.thxh.datacompare.dao.comp1dao.MasterDao;
import com.thxh.datacompare.exception.GlobalException;
import com.thxh.datacompare.service.comp1service.MasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author YYY
 * @date 2021年12月23日12:07
 */
@Service
public class MasterServiceImpl implements MasterService {

    @Autowired
    private MasterDao masterDao;

    @Override
    public int selectMasterCount(String tableName) {

        int result = masterDao.selectMasterCount(tableName);

        return result;
    }

    @Override
    public List<HashMap<String, Object>> queryMasterDetail(String tableName) {
        List<HashMap<String,Object>> hashMapList;

/*        try {
            hashMapList =  masterDao.queryMasterDetail(tableName);
        }catch (Exception e){
            try {
                throw new GlobalException("发生异常，请查询表名称是否正确");
            } catch (GlobalException e1) {
                e1.printStackTrace();
            }
        }*/

        hashMapList =  masterDao.queryMasterDetail(tableName);

        return hashMapList;
    }

    @Override
    public HashMap<String, Object> queryMasterTableDetailByPrimary(String tableName, String masterPrimaryKey, String masterPrimaryValue) {
        HashMap<String,Object> hashMap = masterDao.queryMasterTableDetailByPrimary(tableName,masterPrimaryKey,masterPrimaryValue);
        return hashMap;
    }

    @Override
    public List<HashMap<String,Object>> queryMasterStructure(String masterTableName,String databaseName) {

        List<HashMap<String,Object>> hashMap = masterDao.queryMasterStructure(masterTableName,databaseName);
        return hashMap;
    }
}
