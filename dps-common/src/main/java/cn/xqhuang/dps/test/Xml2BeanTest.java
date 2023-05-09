package cn.xqhuang.dps.test;

import cn.xqhuang.dps.utils.XmlUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.*;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

public class Xml2BeanTest {

    @Data
    public static class GradeDomain {
        @JacksonXmlProperty(localName = "gradeId",isAttribute = true)
        private int gradeId;

        @JacksonXmlText
        private String gradeName;
    }

    @Data
    public static class ScoreDomain {
        @JacksonXmlProperty(localName = "scoreName")
        @JacksonXmlCData
        private String name;

        @JacksonXmlProperty(localName = "scoreNumber")
        private int score;
    }

    @Data
    @JacksonXmlRootElement(localName = "student")
    public static class StudentDomain {

        @JsonIgnore
        private String studentName;

        @JacksonXmlProperty(localName = "age")
        @JacksonXmlCData
        private int age;

        @JacksonXmlProperty(localName = "grade")
        private GradeDomain grade;

        @JacksonXmlElementWrapper(localName = "scoreList")
        @JacksonXmlProperty(localName = "score")
        private List<ScoreDomain> scores;
    }

    public static void main(String[] agr) {
        StudentDomain domain = new StudentDomain();

        domain.setStudentName("张三");
        domain.setAge(18);
        GradeDomain grade = new GradeDomain();
        grade.setGradeId(1);
        grade.setGradeName("高三");
        domain.setGrade(grade);
        ScoreDomain score1 = new ScoreDomain();
        score1.setName("语文");
        score1.setScore(90);
        ScoreDomain score2 = new ScoreDomain();
        score2.setName("数学");
        score2.setScore(98);
        ScoreDomain score3 = new ScoreDomain();
        score3.setName("英语");
        score3.setScore(91);
        List<ScoreDomain> scores = Arrays.asList(score1,score2,score3);
        domain.setScores(scores);
        String xml = XmlUtil.bean2Xml(domain);
        System.out.println(xml);
        StudentDomain studentDomain = XmlUtil.xml2Bean(xml, StudentDomain.class);
        System.out.println(studentDomain);
    }

}
