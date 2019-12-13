package com.xinba.active.vo.response;

import lombok.Data;

import java.util.List;

/**
 * 分页返回包装类
 * @createBy XiaoWu
 * @date 2019/11/26 9:16
 */
@Data
public class PageResponse<T> {

    // 总条数
    private Long total;

    // 单页条数
    private Integer pageSize;

    // 当前页数
    private Integer pageNum;

    // 内容列表
    private List<T> itemList;

    public PageResponse(Long total, Integer pageSize, Integer pageNum, List<T> itemList) {
        this.total = total;
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.itemList = itemList;
    }
}
