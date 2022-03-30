package cn.xqhuang.dps.framework.protocol.http;

import cn.xqhuang.dps.framework.Invocation;
import cn.xqhuang.dps.framework.register.LocalRegister;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ObjectInputStream;
import java.lang.reflect.Method;

public class HttpServerHandel {

    public void handle(HttpServletRequest req, HttpServletResponse resp) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(req.getInputStream());
            Invocation invocation = (Invocation) objectInputStream.readObject();

            Class clazz = LocalRegister.getClass(invocation.getInterfaceName());
            Object obj = clazz.newInstance();
            Method method = clazz.getMethod(invocation.getMethodName(), invocation.getParamTypes());
            String invoke = (String) method.invoke(obj, invocation.getParams());

            resp.getWriter().write(invoke);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
