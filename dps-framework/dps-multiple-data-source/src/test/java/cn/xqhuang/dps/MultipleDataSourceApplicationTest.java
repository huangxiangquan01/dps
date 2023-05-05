package cn.xqhuang.dps;

import cn.xqhuang.dps.entity.SystemUsers;
import cn.xqhuang.dps.mapper.SystemUserMapper;
import cn.xqhuang.dps.service.SystemUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author huangxq
 * @description: TODO
 * @date 2023/3/716:44
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MultipleDataSourceApplicationTest {

    @Resource
    private SystemUserMapper systemUserMapper;
    @Resource
    private SystemUserService systemUserService;

    @Test
    public void test() {
        LambdaQueryWrapper<SystemUsers> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SystemUsers::getUsername, "admin");
        wrapper.gt(SystemUsers::getId, "120");
        List<SystemUsers> systemUsers = systemUserMapper.selectList(wrapper);
        System.out.println(systemUsers.toString());

        systemUserService.updateListById(systemUsers);
    }
}