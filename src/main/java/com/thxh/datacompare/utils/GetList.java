package com.thxh.datacompare.utils;

import com.alibaba.excel.util.ListUtils;
import com.sun.org.apache.bcel.internal.generic.IFNULL;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @author YYY
 * @date 2022年01月07日09:39
 */
public class GetList {


    public static List<List<Object>> getList(List<HashMap<String,Object>> hashMapList) {
        List<List<Object>> list = ListUtils.newArrayList();
//        List data = ListUtils.newArrayList();
        if (hashMapList.size() > 0 || hashMapList != null) {
            Set set = hashMapList.get(0).keySet();
            for (int i = 0; i < hashMapList.size(); i++) {
                List data = ListUtils.newArrayList();
                for (Object key : set) {

                   data.add(hashMapList.get(i).get(key));
                }
//                System.out.println("data"+data);
                list.add(data);
//                data.clear();
            }
        }
//        System.out.println("list" + list);
        return list;
    }

}