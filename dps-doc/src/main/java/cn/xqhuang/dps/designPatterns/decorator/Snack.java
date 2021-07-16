package cn.xqhuang.dps.designPatterns.decorator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Snack {

    public String des; // 描述

    private float price = 0.0f; //价格

    //计算费用的抽象方法
    //子类来实现
    public abstract float cost();
}