package cn.xqhuang.dps.dynamic;

import cn.xqhuang.dps.utils.EasyExcelUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author huangxq
 * @description:
 * @date 2023/4/11 10:08 星期二
 */
public class DynamicHeadTest {

    @Test
    public void test() {
        List<VoteExcel> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            VoteExcel topicExcel = new VoteExcel();
            topicExcel.setId(i);
            topicExcel.setVoteTime(new Date());
            topicExcel.setVoteName("name" + i);
            topicExcel.setVoteOption("b");
            data.add(topicExcel);
        }
        EasyExcelUtils.exportVoteExcel("测试导出excel表","sheet1", VoteExcel.class, data,
                "测试","测试这次是否成功", new Date(), "/Users/huangxq/Desktop/");
    }
}