package cn.xqhuang.dps.designPatterns.proxy;

public class Movie implements IMovie {
    @Override
    public void play() {
        System.out.println("您正在观看电影《速度与激情8》");
    }
}