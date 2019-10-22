package com.xinba.flow.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * (GcFirm)实体类
 *
 * @author tsw
 * @since 2019-07-25 15:38:58
 */
@Data
public class GcFirm implements Serializable {
    private static final long serialVersionUID = 660689118207088245L;
    //客户id
    private Integer id;
    //客户名称
    private String name;
    //客户备注信息
    private String info;
    private String contact;
    private String email;
    private String phone;
    private String address;
    private List<GcFirmType>children;

    private String[] phoneArray;
    private String[] contactArray;



}