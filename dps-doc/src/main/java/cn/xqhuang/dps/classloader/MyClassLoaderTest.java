package cn.xqhuang.dps.classloader;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.Map;

public class MyClassLoaderTest {

    public static class MyClassLoader extends ClassLoader {
        private String calssPath;

        public MyClassLoader(String calssPath) {
            this.calssPath = calssPath;
        }


        protected Class<?> findClass(String name) {
            try {
                byte[] data = loadByte(name);

                return defineClass(name, data, 0, data.length);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        private byte[] loadByte(String name) throws Exception {
            name = name.replaceAll("\\.", "/");
            FileInputStream fis = new FileInputStream(calssPath + "/" + name + ".class");
            int len = fis.available();
            byte[] data = new byte[len];

            fis.read(data);
            fis.close();

            return data;
        }
    }

    public static void main(String[] args) throws Exception{
        MyClassLoader classLoader = new MyClassLoader("/Users/huangxq/Desktop");
        Class clazz = classLoader.loadClass("cn.xqhuang.dps.classloader.User");
        Object obj = clazz.newInstance();
        Method method = clazz.getDeclaredMethod("sout", null);
        method.invoke(obj, null);

        System.out.println(clazz.getClassLoader().getClass().getName());
    }
}
