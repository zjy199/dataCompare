package com.thxh.datacompare.dao.comp2dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface SlaveDao {

    int selectSlaveCount(String tableName);

    List<HashMap<String,Object>> querySlaveDetail(String tableName);

    /**
     * 通过主键查询副表信息，用来比对
     * @param tableName
     * @param slavePrimaryKey
     * @param slavePrimaryValue
     * @return
     */
    HashMap<String,Object> querySlaveTableDetailByPrimary(@Param("tableName") String tableName,
                                                          @Param("primaryKey") String slavePrimaryKey,
                                                          @Param("primaryValue") String slavePrimaryValue);

    /**
     * 查询副表的表结构
     * @param slaveTableName
     * @return
     */
    List<HashMap<String,Object>> querySlaveStructure(@Param("tableName") String slaveTableName,@Param("databaseName") String databaseName);
}
