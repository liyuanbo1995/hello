package com.xinba.flow.entity.charts;

import lombok.Data;

/**
 * 订单统计实体类
 *
 * @author makejava
 * @since 2019-07-15 09:32:00
 */
@Data
public class OrderCount {
    //订单时期
    private String orderDate;

    private double amount;

    //订单数量
    private int count;
}
