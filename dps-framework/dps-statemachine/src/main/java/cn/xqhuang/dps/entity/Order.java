package cn.xqhuang.dps.entity;

import java.util.Date;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 订单表(TOrder)表实体类
 *
 * @author makejava
 * @since 2023-05-25 13:31:06
 */
@Getter
@Setter
@SuppressWarnings("serial")
public class Order extends Model<Order> {
    //主键ID
    private Long id;
    //订单编码
    private String orderCode;
    //订单状态
    private Integer status;
    //订单名称
    private String name;
    //价格
    private Double price;
    //删除标记，0未删除  1已删除
    private Integer deleteFlag;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //创建人
    private String createUserCode;
    //更新人
    private String updateUserCode;
    //版本号
    private Integer version;
    //备注
    private String remark;
}

