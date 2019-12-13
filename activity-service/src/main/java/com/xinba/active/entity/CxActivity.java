package com.xinba.active.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.io.Serializable;

/**
 * (CxActivity)活动实体类
 *
 * @author tsw
 * @since 2019-10-29 14:13:20
 */
@Data
@ApiModel(value="活动实体对象",description="活动实体对象")
public class CxActivity implements Serializable {
    private static final long serialVersionUID = 560362116084327618L;

    @ApiModelProperty(value="id",name="id")
    private Integer id;

    @ApiModelProperty(value="活动编号",name="activityId")
    private String activityId;

    @ApiModelProperty(value="活动名称",name="activityName")
    private String activityName;

    @ApiModelProperty(value="活动类型",name="activityType")
    private String activityType;

    @ApiModelProperty(value="活动开始日期",name="beginTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;

    @ApiModelProperty(value="活动结束日期",name="endTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    @ApiModelProperty(value="状态，0正常，1停用",name="status")
    private Integer status;

    @ApiModelProperty(value = "活动范围",name = "activityRange")
    private String activityRange;

    @ApiModelProperty(value = "活动详情",name = "activityDetail")
    private String activityDetail;

}