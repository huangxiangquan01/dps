package cn.xqhuang.dps.entity;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author huangxq
 * @date 2021-12-03 15:53
 */
public class User {
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private Integer gender;
    private String password;
    private Integer age;
    private LocalDate createTime;
    private LocalDate updateTime;

    public Map<String,Object> toMap(){
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("名称",name);
        data.put("邮箱",email);
        data.put("手机号",phone);
        data.put("性别",gender);
        data.put("密码",password);
        data.put("年龄",age);
        data.put("创建时间",createTime);
        data.put("最后修改时间",updateTime);
        return data;
    }

    public Object[] toArray() {
        return new Object[]{name,email,phone,gender,password,age,createTime,updateTime};
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDate getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDate createTime) {
        this.createTime = createTime;
    }

    public LocalDate getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDate updateTime) {
        this.updateTime = updateTime;
    }


}
