package cn.xqhuang.dps.mapper;

import cn.xqhuang.dps.entity.SysUser;
import org.springframework.stereotype.Repository;

/**
 * @author huangxq
 * @description: TODO
 * @date 2023/3/716:37
 */
@Repository
public interface SysUserMapper {
    SysUser selectById(Long id);
}
