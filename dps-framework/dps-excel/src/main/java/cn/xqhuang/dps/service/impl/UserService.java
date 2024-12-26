package cn.xqhuang.dps.service.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.xqhuang.dps.entity.User;
import cn.xqhuang.dps.handler.CustomResultHandler;
import cn.xqhuang.dps.mapper.UserMapper;
import cn.xqhuang.dps.utils.DownloadProcessor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author huangxq
 * @date 2021-12-03 15:53
 */
@Component
public class UserService {
    @Resource
    private UserMapper userMapper;

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;


    public List<User> getAll(){
        return userMapper.getAll();
    }


    /**
     * 普通导出
     */
    public void exportUser(HttpServletResponse response){

        ServletOutputStream out = null;
        ExcelWriter writer = ExcelUtil.getBigWriter();
        try {
            List<User> userList = userMapper.getAll();
            List<Map<String, Object>> maps = dataAnalysis(userList);
            writer.write(maps, true);
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("用户信息", "UTF-8") + ".xlsx");
            out = response.getOutputStream();
            writer.flush(out, true);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            writer.close();
            IoUtil.close(out);
        }
    }

    /**
     * 流式导出
     * @param response
     */
    public void streamExportUser(HttpServletResponse response) {
        ExcelWriter writer = ExcelUtil.getBigWriter();
        ServletOutputStream out = null;
        try {
            CustomResultHandler customResultHandler = new CustomResultHandler(new DownloadProcessor(response, writer,"用户信息"));
            sqlSessionTemplate.select("com.learn.spring_boot_excel.mapper.UserMapper.streamGetAll",customResultHandler);
            out = response.getOutputStream();
            writer.flush(out, true);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            writer.close();
            IoUtil.close(out);
        }
    }

    /**
     * 流式导出 多 sheet
     * @param response
     */
    public void streamExportUserMoreSheet(HttpServletResponse response) {
        ExcelWriter writer = ExcelUtil.getBigWriter();
        Workbook workbook = writer.getWorkbook();
        // 删除默认 工作表
        workbook.removeSheetAt(0);
        // 设置 表头
        String[] heads = {"名称","邮箱","手机号","性别","密码","创建时间","最后修改时间"};
        // 创建两个工作页
        Sheet sheet = workbook.createSheet("小于50岁");
        Sheet sheet1 = workbook.createSheet("大于50岁");
        // 创建 表头
        Row row = sheet.createRow(0);
        Row row2 = sheet1.createRow(0);
        for (int i = 0; i < heads.length; i++) {
            row.createCell(i).setCellValue(heads[i]);
            row2.createCell(i).setCellValue(heads[i]);
        }

        ServletOutputStream out = null;
        try {
            CustomResultHandler customResultHandler = new CustomResultHandler(new DownloadProcessor(response, writer,"用户信息"));
            sqlSessionTemplate.select("com.learn.spring_boot_excel.mapper.UserMapper.streamGetAll",customResultHandler);
            out = response.getOutputStream();
            writer.flush(out, true);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            writer.close();
            IoUtil.close(out);
        }
    }

    /**
     * 数据解析为 hutool 导出 excel 所用到我的工具
     * @return
     */
    public List<Map<String, Object>> dataAnalysis(List<User> list){
        List<Map<String, Object>> dataList = new LinkedList<>();
        for (User user : list) {
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("名称",user.getName());
            data.put("邮箱",user.getEmail());
            data.put("手机号",user.getPhone());
            data.put("性别",user.getGender());
            data.put("密码",user.getPassword());
            data.put("年龄",user.getAge());
            data.put("创建时间",user.getCreateTime());
            data.put("最后修改时间",user.getUpdateTime());
            dataList.add(data);
        }
        return dataList;
    }

}
