package com.xinba.flow.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (TcPackRelate)实体类
 *
 * @author makejava
 * @since 2019-07-18 16:19:20
 */
@Data
public class TcPackRelate implements Serializable {
    private static final long serialVersionUID = 802002185426790613L;
    
    private Integer id;
    //车厂套餐id
    private Integer basePackId;
    //运营商套餐id
    private Integer packId;
    //车厂套餐对应运营商套餐数量
    private Integer num;



}