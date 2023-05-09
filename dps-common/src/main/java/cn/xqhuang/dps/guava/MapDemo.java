package cn.xqhuang.dps.guava;

import com.google.common.collect.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * 描述
 *
 * @author xiangquan
 * @date 星期二, 5月 09, 2023, 11:08
 **/
public class MapDemo {

    public static void main(String[] args) {
        table();
    }

    /**
     * Table - 双键Map
     */
    public static void table() {
        Table<String,String,Integer> table= HashBasedTable.create();
        //存放元素
        table.put("Hydra", "Jan", 20);
        table.put("Hydra", "Feb", 28);

        table.put("Trunks", "Jan", 28);
        table.put("Trunks", "Feb", 16);

        //取出元素
        Integer dayCount = table.get("Hydra", "Feb");

        System.out.println(dayCount);
        //rowKey或columnKey的集合
        Set<String> rowKeys = table.rowKeySet();

        Set<String> columnKeys = table.columnKeySet();

        //value集合
        Collection<Integer> values = table.values();

        // 计算key对应的所有value的和
        for (String key : table.rowKeySet()) {
            Set<Map.Entry<String, Integer>> rows = table.row(key).entrySet();
            int total = 0;
            for (Map.Entry<String, Integer> row : rows) {
                total += row.getValue();
            }
            System.out.println(key + ": " + total);
        }

        // 转换rowKey和columnKey
        Table<String, String, Integer> table2 = Tables.transpose(table);
        Set<Table.Cell<String, String, Integer>> cells = table2.cellSet();
        cells.forEach(cell->
                System.out.println(cell.getRowKey()+","+cell.getColumnKey()+":"+cell.getValue())
        );

        //转为嵌套的Map
        Map<String, Map<String, Integer>> rowMap = table.rowMap();
        Map<String, Map<String, Integer>> columnMap = table.columnMap();
    }


    /**
     * BiMap - 双向Map
     */
    public static void biMap() {
        HashBiMap<String, String> biMap = HashBiMap.create();
        biMap.put("Hydra","Programmer");
        biMap.put("Tony","IronMan");
        biMap.put("Thanos","Titan");
        //使用key获取value
        System.out.println(biMap.get("Tony"));

        BiMap<String, String> inverse = biMap.inverse();
        //使用value获取key
        System.out.println(inverse.get("Titan"));

        inverse.put("IronMan","Stark");
        System.out.println(biMap);
    }

    public List<String> findKey(Map<String, String> map, String val){
        List<String> keys=new ArrayList<>();
        for (String key : map.keySet()) {
            if (map.get(key).equals(val))
                keys.add(key);
        }
        return keys;
    }

    /**
     * Multimap - 多值Map
     */
    public static void multimap() {
        ArrayListMultimap<String, Integer> multimap = ArrayListMultimap.create();
        multimap.put("day",1);
        multimap.put("day",2);
        multimap.put("day",8);
        multimap.put("month",3);
        // 获取值的集合
        List<Integer> day = multimap.get("day");
        List<Integer> month = multimap.get("month");
        List<Integer> year = multimap.get("year");
        System.out.println(day);
        System.out.println(year);

        // 操作get后的集合
        day.remove(0);//这个0是下标
        month.add(12);
        System.out.println(multimap);

        //转换为Map
        Map<String, Collection<Integer>> map = multimap.asMap();
        for (String key : map.keySet()) {
            System.out.println(key+" : "+map.get(key));
        }
        map.get("day").add(20);
        System.out.println(multimap);

        //数量问题
        System.out.println(multimap.size());
        System.out.println(multimap.entries().size());
        for (Map.Entry<String, Integer> entry : multimap.entries()) {
            System.out.println(entry.getKey()+","+entry.getValue());
        }
    }

    /**
     * RangeMap - 范围Map
     */
    @Deprecated
    public static  void rangeMap() {
        RangeMap<Integer, String> rangeMap = TreeRangeMap.create();
        rangeMap.put(Range.closedOpen(0,60),"fail");
        rangeMap.put(Range.closed(60,90),"satisfactory");
        rangeMap.put(Range.openClosed(90,100),"excellent");

        System.out.println(rangeMap.get(59));
        System.out.println(rangeMap.get(60));
        System.out.println(rangeMap.get(90));
        System.out.println(rangeMap.get(91));

        rangeMap.remove(Range.closed(70,80));
        System.out.println(rangeMap.get(75));
    }

    /**
     * ClassToInstanceMap - 实例Map
     */
    public static void classToInstanceMap() {
        ClassToInstanceMap<Object> instanceMap = MutableClassToInstanceMap.create();
        User user1 = new User("Hydra",18);
        User user2 =new User("develop",200);

        instanceMap.putInstance(User.class, user1);
        instanceMap.putInstance(User.class, user2);

        User user3 = instanceMap.getInstance(User.class);
        System.out.println(user1 == user3);


        ClassToInstanceMap<Map> instanceMapInMap = MutableClassToInstanceMap.create();
        HashMap<String, Object> hashMap = new HashMap<>();
        TreeMap<String, Object> treeMap = new TreeMap<>();
        ArrayList<Object> list = new ArrayList<>();

        instanceMapInMap.putInstance(HashMap.class,hashMap);
        instanceMapInMap.putInstance(TreeMap.class,treeMap);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class User {

        private String name;

        private Integer age;
    }
}
