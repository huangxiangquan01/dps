package cn.xqhuang.dps.dynamic;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @Author:
 * @date: 2023/4/11 22:25
 * @Version: 1.0
 * @Description:
 */
@Data
@EqualsAndHashCode
@HeadRowHeight(2)
public class VoteExcel {

    @ExcelProperty({"投票主题","描述","投票时间","序号"})
    @ColumnWidth(25)
    private Integer id;

    @ExcelProperty({"${title}","${describe}","${voteTime}","投票时间"})
    @DateTimeFormat("yyyy-MM-dd HH:mm::ss")
    private Date voteTime;

    @ExcelProperty({"${title}","${describe}","${voteTime}","投票人"})
    private String voteName;

    @ExcelProperty({"${title}","${describe}","${voteTime}","${title}"})
    private String voteOption;
}
