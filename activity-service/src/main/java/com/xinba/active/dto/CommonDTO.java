package com.xinba.active.dto;

import lombok.Data;

/**
 * 三围数据组，分组通过as tag (通常是时间),
 * count(id) as valueDTO
 * 键值赋给 keyDTO,where
 * 兼容json格式，key应该采用String类型，Map也是 Map<String, Integer>
 * @createBy XiaoWu
 * @date 2019/12/2 15:56
 */
@Data
public class CommonDTO {

    private String keyDTO;

    private Object valueDTO;

    private Object tag;
}
