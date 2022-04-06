package com.thxh.datacompare.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author YYY
 * @date 2021年12月30日19:49
 */
public class DataCompare {

    public static List<HashMap<String,Object>> dataComp(List<HashMap<String,Object>> masterHashMapList,
                                                        List<HashMap<String,Object>> slaveHashMapList,
                                                        HashMap<String,Object> slaveTable,
                                                        String primaryKey){
        List<HashMap<String,Object>> resultHashMapList = new ArrayList<>();

        if (masterHashMapList.size() == 0 || slaveHashMapList.size() == 0){
            Map hashMap = new HashMap();
            hashMap.put("noValue","主数据库或者副数据库为空");
            resultHashMapList.add(0,(HashMap<String, Object>) hashMap);
            System.out.println(resultHashMapList.size());
            return resultHashMapList;
        }
        if (masterHashMapList.size() >= slaveHashMapList.size()){
            for (int i = 0;i < masterHashMapList.size();i++){
                String masterPrimaryValue = (String) masterHashMapList.get(i).get(primaryKey);
//                HashMap<String,Object> slaveTable = slaveService.querySlaveTableDetailByPrimary(slaveTableName,primaryKey,masterPrimaryValue);

                if (!masterHashMapList.get(i).equals(slaveTable)){
                    resultHashMapList.add(masterHashMapList.get(i));
                }

            }
        }
        if (masterHashMapList.size() < slaveHashMapList.size()){
            for (int i = 0;i < masterHashMapList.size();i++){
                String masterPrimaryValue = (String) masterHashMapList.get(i).get(primaryKey);
//                HashMap<String,Object> slaveTable = slaveService.querySlaveTableDetailByPrimary(slaveTableName,primaryKey,masterPrimaryValue);

                if (!masterHashMapList.get(i).equals(slaveTable)){
                    slaveHashMapList.add(masterHashMapList.get(i));
                }else if (masterHashMapList.get(i).equals(slaveTable)){
                    slaveHashMapList.remove(slaveTable);
                }

            }

            return slaveHashMapList;
        }


        return resultHashMapList;

    }


}
