package com.xinba.active.entity;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

@Data
/**
 * (CxActivityGoods)实体类
 *
 * @author tsw
 * @since 2019-11-20 17:10:22
 */
@ApiModel(value="活动奖品实体类",description="活动奖品实体对象")
public class CxActivityGoods implements Serializable {
    private static final long serialVersionUID = -98512250760423714L;

        
    @ApiModelProperty(value="id",name="id")
    private Integer id;

    //活动编号    
    @ApiModelProperty(value="活动编号",name="activityId")
    private String activityId;

    //活动类型    
    @ApiModelProperty(value="活动类型",name="activityType")
    private String activityType;

    //奖品编号    
    @ApiModelProperty(value="奖品编号",name="goodsId")
    private String goodsId;

    //奖品名称    
    @ApiModelProperty(value="奖品名称",name="goodsName")
    private String goodsName;

    //奖品类型    
    @ApiModelProperty(value="奖品类型",name="goodsType")
    private Integer goodsType;

    //奖品总数,999999999表示不限    
    @ApiModelProperty(value="奖品总数,999999999表示不限",name="goodsTotal")
    private Integer goodsTotal;

    //奖品剩余数量    
    @ApiModelProperty(value="奖品剩余数量",name="goodsRemain")
    private Integer goodsRemain;

    //状态，0正常，1停用    
    @ApiModelProperty(value="状态，0正常，1停用",name="status")
    private Integer status;

    @ApiModelProperty(value="${column.comment}",name="createTime")
    private Date createTime;

    //奖品图片    
    @ApiModelProperty(value="奖品图片",name="goodsImg")
    private String goodsImg;

    //奖品权重    
    @ApiModelProperty(value="奖品权重",name="goodsWeight")
    private Integer goodsWeight;

    //是否在奖池，0在，1不在    
    @ApiModelProperty(value="是否在奖池，0在，1不在",name="isinpool")
    private Integer isInPool;

    //奖品详情，如打几折，多少流量    
    @ApiModelProperty(value="奖品详情，如打几折，多少流量",name="goodsDetail")
    private String goodsDetail;

    //奖品描述
    @ApiModelProperty(value="奖品描述",name="goodsInfo")
    private String goodsInfo;

    //奖品价格，允许有负数
    @ApiModelProperty(value="奖品价格，允许有负数",name="price")
    private Double price;

    //奖品使用截止时间
    @ApiModelProperty(value="奖品使用截止时间",name="endTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    private String exchangeCode;

}