package cn.xqhuang.dps.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ObjectToMapUtils {
	/**
	 * Object转map
	 *
	 * @param obj 转换对象
	 * @return map
	 */
	public static Map<String, Object> objectToMap(Object obj) throws IllegalAccessException {
		if (obj == null) {
			throw new NullPointerException();
		}
		Map<String, Object> map = new HashMap<>();
		Class<?> clazz = obj.getClass();
		while (clazz != null) {
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				//访问私有属性需设为可见
				field.setAccessible(true);
				String key = field.getName();
				Object value = field.get(obj);
				if (value != null) {
					map.put(key, value);
				}
			}
			clazz = clazz.getSuperclass();
		}
		return map;
	}
}
