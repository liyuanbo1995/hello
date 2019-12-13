package com.xinba.active.entity.draw;

import lombok.Data;

import java.util.Date;

@Data
public class ActivityGoods {
   private String activityName;
   private Date beginTime;
   private Date endTime;
   private String goodsName;
   private String goodsImg;
   private String goodsDetail;
   private String goodsInfo;
}
