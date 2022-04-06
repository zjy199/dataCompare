package com.thxh.datacompare.dao.comp1dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface MasterDao {

    int selectMasterCount(String tableNameMaster);

    List<HashMap<String,Object>> queryMasterDetail(String tableName);

    /**
     * 通过主键查询主表信息，用来比对
     * @param tableName
     * @param masterPrimaryKey
     * @param masterPrimaryValue
     * @return
     */
    HashMap<String,Object> queryMasterTableDetailByPrimary(@Param("tableName") String tableName,
                                                          @Param("primaryKey") String masterPrimaryKey,
                                                          @Param("primaryValue") String masterPrimaryValue);

    /**
     * 查询主表的表结构
     * @param masterTableName
     * @return
     */
    List<HashMap<String,Object>> queryMasterStructure(@Param("tableName") String masterTableName,
                                                @Param("databaseName") String databaseName);
}
