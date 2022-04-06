package com.thxh.datacompare.utils;

import com.alibaba.excel.util.ListUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @author YYY
 * @date 2022年01月07日09:39
 */
public class Head {

    public static List<List<String>> head(List<HashMap<String,Object>> hashMapList){
        List<List<String>> list = ListUtils.newArrayList();

        if (hashMapList.size() > 0 || hashMapList != null){
            List<String> data = ListUtils.newArrayList();
            HashMap hashMap = hashMapList.get(0);
            Set set = hashMap.keySet();
            for (Object setKey:set){
                List<String> head0 = ListUtils.newArrayList();
                head0.add((String) setKey);
                list.add(head0);
            }
        }

        return list;

    }
}
