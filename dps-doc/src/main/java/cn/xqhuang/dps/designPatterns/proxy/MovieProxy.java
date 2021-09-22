package cn.xqhuang.dps.designPatterns.proxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieProxy implements IMovie {
    Movie movie;
 
    public MovieProxy(Movie movie) {
        this.movie = movie;
    }
 
    @Override
    public void play() {
        advertising(true);
        movie.play();
        advertising(false);
    }
 
    private void advertising(boolean isBeforMovie){
        if(isBeforMovie){
            System.out.println("影片马上开始,素小暖入驻CSDN啦,快来关注我啊");
        }else{
            System.out.println("影片正片已经结束,马上彩蛋环节,不要离开哦,素小暖入驻CSDN啦,快来关注我啊");
        }
    }
 
    public static void main(String[] args) {
        Movie movie = new Movie();
        IMovie movieProxy = new MovieProxy(movie);
        movieProxy.play();
    }
}