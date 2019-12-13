package com.xinba.active.dto;

import com.xinba.active.entity.CxActivityGoods;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 活动商品 业务类
 * @createBy XiaoWu
 * @date 2019/11/26 9:09
 */
@Data
public class CxActivityDTO {

    @ApiModelProperty(value="id",name="id")
    private Integer id;

    @ApiModelProperty(value="活动编号",name="activityId")
    private String activityId;

    @ApiModelProperty(value="活动名称",name="activityName")
    private String activityName;

    @ApiModelProperty(value="活动类型",name="activityType")
    private String activityType;

    @ApiModelProperty(value="活动开始日期",name="beginTime")
    private Date beginTime;

    @ApiModelProperty(value="活动结束日期",name="endTime")
    private Date endTime;

    @ApiModelProperty(value="状态，0正常，1停用",name="status")
    private Integer status;

    @ApiModelProperty(value="活动商品列表",name="activityGoodsList")
    private List<CxActivityGoods> activityGoodsList;
}
