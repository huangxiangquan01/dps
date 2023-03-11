package cn.xqhuang.dps.pdf;

import lombok.Data;

import java.util.List;

/**
 * 检验
 * @author changzhong
 */
@Data
public class TestView {

    /**
     * 报告号
     */
    private String reportId;

    //医嘱序号
    private String orderSeq;

    /**
     * 检验项目代码
     */
    private String testCode;

    /**
     * 检验项目名称
     */
    private String testName;

    /**
     * 标本类型代码
     */
    private String specimenCode;

    /**
     * 标本类型名称
     */
    private String specimenName;

    /**
     * 送检医生代码
     */
    private String appDocId;

    /**
     * 送检医生姓名(申请医生名称)
     */
    private String appDocName;

    /**
     * 报告日期和时间
     */
    private String reportDate;


    /**
     * 审核者代码
     */
    private String auditDocId;

    /**
     * 审核者姓名
     */
    private String auditDocName;

    /**
     * 检验详情
     */
    private List<TestDetailView> testDetailViews;

    private List<String> abnormalVal;

    @Data
    public static class TestDetailView {
        private String reportId;
        private String chkRptCode;
        private String chkRptName;
        private String chkRsVal;
        private String chkRsUnit;
        private String chkNormalTg;
        private String chkNormalVal;
        private String printSeq;
    }

}
