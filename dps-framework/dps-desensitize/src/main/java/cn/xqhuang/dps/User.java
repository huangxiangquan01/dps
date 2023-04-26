package cn.xqhuang.dps;

import cn.xqhuang.dps.annotation.SliderDesensitize;
import lombok.Getter;
import lombok.Setter;

/**
 * @author huangxq
 * @description:
 * @date 2023/4/26 09:16 星期三
 */
@Getter
@Setter
public class User {

    private Long id;

    @SliderDesensitize
    private String name;
}
