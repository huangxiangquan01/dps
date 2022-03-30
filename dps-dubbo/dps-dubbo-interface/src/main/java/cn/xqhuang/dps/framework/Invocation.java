package cn.xqhuang.dps.framework;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
public class Invocation implements Serializable {

    private static final long serialVersionUID = 1L;

    private String interfaceName;
    private String methodName;
    private Class[] paramTypes;
    private Object[] params;
}
