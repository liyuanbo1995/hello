package com.xinba.flow.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

/**
 * 菜单权限表(SysMenu)实体类
 *
 * @author makejava
 * @since 2019-07-15 09:32:00
 */
@Data
public class SysMenu implements Serializable {
    private static final long serialVersionUID = 196583024908190015L;
    //菜单ID
    private String menuId;
    //菜单名称
    private String name;
    //菜单权限标识
    private String permission;
    //前端URL
    private String path;
    //父菜单ID
    private String parentId;
    //图标
    private String icon;
    //VUE页面
    private String component;
    //排序值
    private Integer sort;
    //0-开启，1- 关闭
    private String keepAlive;
    //菜单类型 （0菜单 1按钮）
    private String type;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //逻辑删除标记(0--正常 1--删除)
    private String delFlag;

    private List<SysMenu> children;

}