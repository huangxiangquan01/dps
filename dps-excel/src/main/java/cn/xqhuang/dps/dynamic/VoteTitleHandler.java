package cn.xqhuang.dps.dynamic;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.util.PropertyPlaceholderHelper;

/**
 * @Author: yangypeng
 * @date: 2022/11/14 21:17
 * @Version: 1.0
 * @Description:
 */
public class VoteTitleHandler implements CellWriteHandler {

    private String title;

    private String describe;

    private Date voteTime;

    PropertyPlaceholderHelper placeholderHelper = new PropertyPlaceholderHelper("${", "}");

    public VoteTitleHandler(String title,String describe, Date voteTime) {
        this.title = title;
        this.describe = describe;
        this.voteTime = voteTime;
    }

    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
            Row row, Head head, Integer integer, Integer integer1, Boolean aBoolean) {
        if (head != null) {
            List<String> headNameList = head.getHeadNameList();
            if (CollectionUtils.isNotEmpty(headNameList)) {
                Properties properties = new Properties();
                properties.setProperty("title", title);
                properties.setProperty("describe", describe);
                properties.setProperty("voteTime",
                        new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(voteTime));
                for (int i = 0; i < headNameList.size(); i++) {
                    headNameList.set(i, placeholderHelper.replacePlaceholders(headNameList.get(i), properties));
                }
            }
        }
    }

    @Override
    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell, Head head, Integer integer, Boolean aBoolean) {

    }

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<CellData> list, Cell cell, Head head, Integer integer, Boolean aBoolean) {

    }


}
