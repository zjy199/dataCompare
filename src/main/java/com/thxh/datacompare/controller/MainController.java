package com.thxh.datacompare.controller;

import com.alibaba.excel.EasyExcel;
import com.thxh.datacompare.entities.CommonResult;
import com.thxh.datacompare.service.comp1service.MasterService;
import com.thxh.datacompare.service.comp2service.SlaveService;
import com.thxh.datacompare.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author YYY
 * @date 2021年12月23日11:57
 */
@RestController
@Slf4j
public class MainController {

    @Autowired
    private MasterService masterService;
    @Autowired
    private SlaveService slaveService;

    @Autowired
    private RedisUtil redisUtil;

//    List<HashMap<String,Object>> resultHashMapList1 = new ArrayList<>();

    @GetMapping("/test")
    public String test(){
        return "Hello World";
    }

    /**
     * 比较两个表的数据条数是否一致
     * @param tableNameMaster
     * @param tableNameSlave
     * @return
     */
    @GetMapping("/compareNum/{tableNameMaster}/{tableNameSlave}")
    public String compareNum(@PathVariable("tableNameMaster") String tableNameMaster,
                             @PathVariable("tableNameSlave")String tableNameSlave){

        String result = "equal";

        int tableMasterNum = masterService.selectMasterCount(tableNameMaster);
        int tableSalveNum = slaveService.selectSlaveCount(tableNameSlave);

        if (tableMasterNum != tableSalveNum){
            result = "unequal";
        }

        return result;

    }

    /**
     * 查询主数据库表的内容，返回 json
     * @param tableName
     * @return List<HashMap<String,Object>>
     */
    @GetMapping("/queryMasterDetail/{tableMasterName}")
    public List<HashMap<String,Object>> queryMasterDetail(@PathVariable("tableMasterName") String tableName){

        List<HashMap<String,Object>> hashMapList =  masterService.queryMasterDetail(tableName);

        for (HashMap hashList:hashMapList){

            System.out.println(hashList);

        }
        System.out.println("========");
        for (int i = 0;i < hashMapList.size(); i++){
            Object id = hashMapList.get(i).get("id");

            System.out.println(id);
        }

        return hashMapList;
    }

    /**
     * 比较主、从数据库的数据，将不一致的数据以 json 的方式返回
     * @return
     * /{masterTableName}/{slaveTableName}/{primaryKey}/{pageSize}/{pageNo}
     */
    @GetMapping("/compareTableDetail")
    public CommonResult<List<HashMap<String, Object>>> compareTableDetail(//xxx
                                   @RequestParam(name = "masterTableName") String masterTableName,
                                   @RequestParam(name = "slaveTableName") String slaveTableName,
                                   //@RequestParam(name = "primaryKey") String primaryKey,
                                   @RequestParam(name = "pageSize") Integer pageSize,
                                   @RequestParam(name = "pageNo") Integer pageNo,
                                   @RequestParam(name = "masterDatabase") String masterDatabase,
                                   @RequestParam(name = "slaveDatabase") String slaveDatabase

    ){

        List<HashMap<String,Object>> masterHashMapList =  masterService.queryMasterDetail(masterTableName);
        List<HashMap<String, Object>> slaveHashMapList = slaveService.querySlaveDetail(slaveTableName);
        List<HashMap<String, Object>> slaveHashMapListRes ;
        List<HashMap<String,Object>> resultHashMapList = new ArrayList<>();
        List<HashMap<String,Object>> resultHashMapListRes ;

        List<HashMap<String,Object>> masterDatabaseList = masterService.queryMasterStructure(masterTableName,masterDatabase);
        List<HashMap<String,Object>> slaveDatabaseList = slaveService.querySlaveStructure(slaveTableName,slaveDatabase);

        int total = 0;  //记录查询结果的总数

        String root = System.getProperty("user.dir");

        //表结构不一致，返回
        if (!masterDatabaseList.equals(slaveDatabaseList)){
            return new CommonResult<>(200,"表结构不一致");
        }

        //主数据库表或者副数据库表为空，返回
        if (masterHashMapList.size() == 0 || slaveHashMapList.size() == 0){
            Map hashMap = new HashMap();
            hashMap.put("noValue","主数据库或者副数据库为空");
            resultHashMapList.add(0,(HashMap<String, Object>) hashMap);

            return new CommonResult<>(200,"主数据库或者副数据库为空",resultHashMapList,total);
        }
        //主表数据条数 大于 副表数据条数，从主表中比对副表数据，相同的移除，返回剩余数据
        if (masterHashMapList.size() >= slaveHashMapList.size()){
/*            for (int i = 0; i < masterHashMapList.size();i++){
//            for (int i = pageSize * (pageNo - 1) * 10;i < pageSize * 10 * (pageNo) && i < masterHashMapList.size();i++){
                String masterPrimaryValue = (String) masterHashMapList.get(i).get(primaryKey);
                HashMap<String,Object> slaveTable = slaveService.querySlaveTableDetailByPrimary(slaveTableName,primaryKey,masterPrimaryValue);

//                if (!masterHashMapList.get(i).equals(slaveTable)){
                if (masterHashMapList.get(i).equals(slaveTable)){
                   // resultHashMapList.add(masterHashMapList.get(i));
                   //切换逻辑
//                    masterHashMapList.remove(masterHashMapList.get(i));
//                    resultHashMapList = masterHashMapList;

                }

            }*/
            //  更换
            for (int i = 0; i < slaveHashMapList.size();i++){
//                String slavePrimaryValue = (String) slaveHashMapList.get(i).get(primaryKey);
                //------- 优化效率测试1，可大幅提升效率
                if (masterHashMapList.contains(slaveHashMapList.get(i))){
                    masterHashMapList.remove(slaveHashMapList.get(i));
                    resultHashMapList = masterHashMapList;
                }
                //-------

/*                HashMap<String,Object> masterTable = masterService.queryMasterTableDetailByPrimary(masterTableName,primaryKey,slavePrimaryValue);

//                if (!masterHashMapList.get(i).equals(slaveTable)){
                if (slaveHashMapList.get(i).equals(masterTable)){
                    // resultHashMapList.add(masterHashMapList.get(i));
                    //切换逻辑
                    masterHashMapList.remove(masterHashMapList.get(i));
                    resultHashMapList = masterHashMapList;

                }*/

            }
        }
        //主表数据条数 小于 副表数据条数，从副表中比对主表数据，相同的移除，返回剩余数据
        else if (masterHashMapList.size() < slaveHashMapList.size()){
            for (int i = 0;i < masterHashMapList.size();i++){
/*                String masterPrimaryValue = (String) masterHashMapList.get(i).get(primaryKey);
                HashMap<String,Object> slaveTable = slaveService.querySlaveTableDetailByPrimary(slaveTableName,primaryKey,masterPrimaryValue);

                if (!masterHashMapList.get(i).equals(slaveTable)){
                    slaveHashMapList.add(masterHashMapList.get(i));
                }else if (masterHashMapList.get(i).equals(slaveTable)){
                    slaveHashMapList.remove(slaveTable);
                }*/
                //------- 优化效率测试1
                if (slaveHashMapList.contains(masterHashMapList.get(i))){
                    slaveHashMapList.remove(masterHashMapList.get(i));
                   // resultHashMapList = masterHashMapList;
                }
                //-------

            }

            //将比对的数据存放入 redis 数据库，暂时未开启
            if (slaveHashMapList.size()>0 && slaveHashMapList != null){

//            DataToExcel.createExcel("d:/aaaa.xlsx",slaveHashMapList);
//                redisUtil.set("excelData",slaveHashMapList);  //将比对的数据存放入 redis 数据库
//                System.out.println("-----------------");
//                System.out.println("mas" + masterHashMapList);
//                System.out.println("-----------------");
//                System.out.println("sla" + slaveHashMapList);
//                EasyExcel.write(root + "\\比对信息表.xlsx").head(Head.head(slaveHashMapList)).sheet(0).doWrite(GetList.getList(slaveHashMapList));

            }
            if(slaveHashMapList.size() > pageSize){
                total = slaveHashMapList.size();
                slaveHashMapListRes = slaveHashMapList.subList(((pageNo - 1) * pageSize),(pageSize * pageNo));
                return new CommonResult<>(200,"查询成功",slaveHashMapListRes,total);
            }else {
                total = slaveHashMapList.size();
                slaveHashMapListRes = slaveHashMapList;
                return new CommonResult<>(200,"查询成功",slaveHashMapListRes,total);
            }

//            return slaveHashMapList;
        }

        //将比对的数据存放入 redis 数据库，暂时未开启
        if (resultHashMapList.size()>0 && resultHashMapList != null){

//                redisUtil.set("excelData",resultHashMapList);   //将比对的数据存放入 redis 数据库

//            EasyExcel.write(root + "\\比对信息表.xlsx").head(Head.head(masterHashMapList)).sheet(0).doWrite(GetList.getList(resultHashMapList));

        }

        // 分页相关处理
        if(resultHashMapList.size() > pageSize){
            total = resultHashMapList.size();
            resultHashMapListRes = resultHashMapList.subList(((pageNo - 1) * pageSize),(pageSize * pageNo));
        }else {
            total = resultHashMapList.size();
            resultHashMapListRes = resultHashMapList;

        }
//        return resultHashMapList;

        return new CommonResult<>(200,"查询成功",resultHashMapListRes,total);

    }

    /**
     * 查询副数据库的数据
     * @param tableName
     * @return
     */
    @GetMapping("/querySlaveDetail/{tableSlaveName}")
    public List<HashMap<String,Object>> querySlaveDetail(@PathVariable("tableSlaveName") String tableName){

        List<HashMap<String,Object>> hashMapList =  slaveService.querySlaveDetail(tableName);
        Map map = (Map) hashMapList;
//        System.out.println(hashMapList.get(0).keySet().size());
/*        hashMapList.get(0).keySet();
        Iterator iterator = hashMapList.get(0).keySet().iterator();*/
//        while (iterator.hasNext()){
//            System.out.println(iterator.next());
//        }
/*        System.out.println("===");
        Object[] arrayList = hashMapList.get(0).keySet().toArray();
        System.out.println(hashMapList.get(0).keySet());

        System.out.println("hashMapList" + hashMapList);
        for (Object o:arrayList){
            System.out.println(o);
        }*/

        return hashMapList;
    }

    /**
     * 测试 List 转为 Map
     * @param tableName
     * @return
     */
    @GetMapping("/querySlaveDetailtest/{tableSlaveName}")
    public HashMap<String,Object> querySlaveDetailTest(@PathVariable("tableSlaveName") String tableName){

        HashMap<String,Object> hashMapList = (HashMap<String, Object>) slaveService.querySlaveDetail(tableName);
//        Map<Long, Object> maps = hashMapList.stream().collect(Collectors.toMap(Object::getId, Function.identity(), (key1, key2) -> key2));


//        System.out.println(hashMapList.get(0).keySet().size());
/*        hashMapList.get(0).keySet();
        Iterator iterator = hashMapList.get(0).keySet().iterator();*/
//        while (iterator.hasNext()){
//            System.out.println(iterator.next());
//        }
/*        System.out.println("===");
        Object[] arrayList = hashMapList.get(0).keySet().toArray();
        System.out.println(hashMapList.get(0).keySet());

        System.out.println("hashMapList" + hashMapList);
        for (Object o:arrayList){
            System.out.println(o);
        }*/

        return hashMapList;
    }

    /**
     * 根据 副数据库 的 表名称、主键 来进行数据的查询
     * @param slaveTableName
     * @param primaryKey
     * @return
     */
    @GetMapping("/querySlaveByPrimary/{slaveTableName}/{primaryKey}/{primaryValue}")
    public HashMap<String,Object> querySlaveByPrimary(@PathVariable("slaveTableName") String slaveTableName,
                                                      @PathVariable("primaryKey") String primaryKey,
                                                      @PathVariable("primaryValue") String primaryValue){

        HashMap<String,Object> hashMap = slaveService.querySlaveTableDetailByPrimary(slaveTableName,primaryKey,primaryValue);
        System.out.println(hashMap);

        return hashMap;

    }

    @RequestMapping("/compareNum/{tableNameMaster}")
    public String test1(@PathVariable("tableNameMaster") String tableNameMaster){

        String result = "equal";

        int tableMasterNum = masterService.selectMasterCount(tableNameMaster);

        System.out.println(tableMasterNum);

        return result;

    }

    @RequestMapping("/createExcel")
    public String testExcel(){
        List<HashMap<String,Object>> resultHashMapList = new ArrayList<>();
        String root = System.getProperty("user.dir");
        String result ="查询结果为空，无法生成 Excel";
        if (redisUtil.hasKey("excelData")&& redisUtil.get("excelData") != null) {

            Object res1 = redisUtil.get("excelData");

            resultHashMapList = (List<HashMap<String, Object>>) res1;

            EasyExcel.write(root + "\\比对信息表.xlsx").head(Head.head(resultHashMapList)).sheet(0)
                    .doWrite(GetList.getList(resultHashMapList));
            result = "导出 Excel 成功！";

            return result;
        }
        return result;

    }

    //测试 redis 连接
    @RequestMapping("/testredis")
    public void testRedis(String key){
        boolean res = redisUtil.hasKey("k2");
        System.out.println(res);
        redisUtil.set("k2","ee");
        Object res1 =  redisUtil.get("k2");
        System.out.println(res1);
    }

    //测试查询主表表结构
    @RequestMapping("/queryMasterStruct")
    public List<HashMap<String,Object>> queryMasterStruct(@RequestParam(name = "masterTableName") String masterTableName,
                                                    @RequestParam(name = "databaseName") String databaseName){
        List<HashMap<String,Object>> result =  masterService.queryMasterStructure(masterTableName,databaseName);
        return result;

    }

    //测试查询副表表结构
    @RequestMapping("/querySlaveStruct")
    public List<HashMap<String,Object>> querySlaveStruct(@RequestParam(name = "slaveTableName") String slaveTableName,
                                                          @RequestParam(name = "databaseName") String databaseName){
        List<HashMap<String,Object>> result =  masterService.queryMasterStructure(slaveTableName,databaseName);
        return result;

    }

}
