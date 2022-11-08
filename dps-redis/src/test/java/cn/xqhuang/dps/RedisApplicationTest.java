package cn.xqhuang.dps;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author huangxq
 * @description: TODO
 * @date 2022/11/310:16
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RedisApplication.class})
public class RedisApplicationTest {

    @Test
    public void pdfTest() {
        File file = new File("/Users/huangxq/Downloads/2.json");
        StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){ //使用readLine方法，一次读一行
                result.append(System.lineSeparator()+s);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        Map<String, Object> reportMap = new HashMap<>();
        String gender = "男";
        // reportMap.put("createDate", DateUtil.format(new Date(), "yyyy-MM-DD"));
        reportMap.put("name", "张三");
        reportMap.put("patientId", "1221412414");
        reportMap.put("imgUrl", "");
        reportMap.put("gender", gender);
        reportMap.put("age", "25");
        // List<TestView> testViews = JSON.parseArray(result.toString(), TestView.class);
        // reportMap.put("testViewList", testViews);

        //html文件路径
        try {
            // html转pdf
            // PdfUtils.html2pdf("visit_report_template", reportMap);
            System.out.println("转换成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}