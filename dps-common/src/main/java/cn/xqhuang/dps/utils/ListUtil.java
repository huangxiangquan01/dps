package cn.xqhuang.dps.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ListUtil {

    /**
     * 获取first集合和second集合的交集
     */
    public static <T> Set<T> getIntersection(final Set<T> first, final Set<T> second) {
        final Set<T> copyOfFirst = new HashSet<>(first);
        copyOfFirst.retainAll(second);
        return copyOfFirst;
    }

    /**
     * 获取first集合中存在但在second集合中不存在的元素集合
     */
    public static <T> Set<T> getDifference(Set<T> first, Set<T> second) {
        final Set<T> copyOfFirst = new HashSet<>(first);
        copyOfFirst.removeAll(second);
        return copyOfFirst;
    }

    /**
     * 获取first集合和second集合的交集
     */
    public static <T> List<T> getIntersection(List<T> first, List<T> second) {
        final List<T> copyOfFirst = new ArrayList<>(first);
        copyOfFirst.retainAll(second);
        return copyOfFirst;
    }

    /**
     * 获取first集合中存在但在second集合中不存在的元素集合
     */
    public static <T> List<T> getDifference(List<T> first, List<T> second) {
        final List<T> copyOfFirst = new ArrayList<>(first);
        copyOfFirst.removeAll(second);
        return copyOfFirst;
    }
}
